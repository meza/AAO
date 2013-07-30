package hu.meza.aao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class DefaultScenarioContextTest {

	private DefaultScenarioContext ctx;

	@Before
	public void setUp() throws Exception {
		ctx = new DefaultScenarioContext();
	}

	@Test(expected = ClassCastException.class)
	public void testTheWrongGetSubject() {

		ctx.setSubject("something");
		int i = ctx.getSubject();

	}

	@Test
	public void testGetSubject() {
		String subject = "subject";
		ctx.setSubject(subject);
		Assert.assertEquals("The wrong subject was returned", subject, ctx.getSubject());
	}

	@Test
	public void testGetLastAction() {
		final Action action = Mockito.mock(Action.class);
		Mockito.when(action.copyOf()).thenReturn(Mockito.mock(Action.class));

		ctx.setLastAction(action);
		Action actual = ctx.getLastAction();

		Mockito.verify(action, Mockito.atLeastOnce()).copyOf();

		Assert.assertNotEquals("The last action was the same instance, not the same action", action, actual);

		Assert.assertTrue("No action was given", actual instanceof Action);

	}

	@Test
	public void testGetLastActor() {
		final Actor actor = Mockito.mock(Actor.class);
		ctx.setLastActor(actor);
		Actor actual = ctx.getLastActor();

		Assert.assertEquals("The last actor was returned incorrectly", actor, actual);

	}

	@Test
	public void testClean() {
		final Actor actor = Mockito.mock(Actor.class);
		final Action action = Mockito.mock(Action.class);
		String subject = "subject";
		ctx.setSubject(subject);
		ctx.setLastActor(actor);
		ctx.setLastAction(action);
		ctx.clean();

		Assert.assertNull(ctx.getLastAction());
		Assert.assertNull(ctx.getLastActor());
		Assert.assertNull(ctx.getSubject());

	}
}
