package hu.meza.aao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.UUID;

public class ActorManagerTest {

	private ActorManager actorManager;

	@Before
	public void setUp() throws Exception {
		actorManager = new ActorManager();
	}

	@Test
	public void testActorManaging() {

		Actor actor = Mockito.mock(Actor.class);
		String correctLabel = randomLabel();
		actorManager.addActor(correctLabel, actor);
		Actor actual = actorManager.getActor(correctLabel);

		Assert.assertEquals("The wrong actor was returned from the actor manager", actor, actual);

	}

	@Test
	public void testRelativeActorManaging() {

		Actor firstActor = Mockito.mock(Actor.class);
		actorManager.addActor(randomLabel(), firstActor);
		Actor actual = actorManager.getActor("he");
		Assert.assertEquals("The wrong actor was returned from the actor manager", firstActor, actual);

		Actor secondActor = Mockito.mock(Actor.class);
		actorManager.addActor(randomLabel(), secondActor);
		actual = actorManager.getActor("she");
		Assert.assertEquals("The wrong actor was returned from the actor manager", secondActor, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testRelativeAdd() {
		actorManager.addActor("she", Mockito.mock(Actor.class));
	}

	@Test(expected = ActorManagerIsEmptyException.class)
	public void testRelativeWithoutActors() {
		actorManager.getActor("he");
	}

	@Test(expected = ActorNotFoundException.class)
	public void testActorManagingWithLabelMismatch() {
		Actor actor = Mockito.mock(Actor.class);
		actorManager.addActor(randomLabel(), actor);
		actorManager.getActor("incorrectLabel");
	}

	@Test
	public void testLastActor() {
		Actor actor = Mockito.mock(Actor.class);
		Actor secondActor = Mockito.mock(Actor.class);
		actorManager.addActor("second", secondActor);
		actorManager.addActor(randomLabel(), actor);
		Assert.assertEquals("The wrong last actor was returned from the actor manager", actor,
							actorManager.lastActor());
		actorManager.getActor("second");
		Assert.assertEquals("The wrong last actor was returned from the actor manager", secondActor,
							actorManager.lastActor());
	}

	@Test
	public void addLastActorToContext() {
		ScenarioContext ctx = Mockito.mock(ScenarioContext.class);
		ActorManager contextAwareManager = new ActorManager(ctx);

		final Actor actor = Mockito.mock(Actor.class);
		contextAwareManager.addActor(randomLabel(), actor);

		Mockito.verify(ctx, Mockito.atLeastOnce()).setLastActor(actor);

	}

	private String randomLabel() {
		return UUID.randomUUID().toString();
	}

}
