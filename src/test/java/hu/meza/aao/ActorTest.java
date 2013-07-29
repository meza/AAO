package hu.meza.aao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ActorTest {


	private Actor actor;

	@Before
	public void setUp() throws Exception {
		actor = new Actor();
	}

	@Test
	public void testExecute() {
		Action action = Mockito.mock(Action.class);
		actor.execute(action);
		Mockito.verify(action, Mockito.times(1)).execute();
	}

	@Test
	public void testLastAction() {
		Action action = Mockito.mock(Action.class);
		actor.execute(action);
		Assert.assertEquals("Last action was not recorded properly with the actor", action,
							actor.lastAction());
	}

	@Test
	public void testSetLastActionToScenarioContext() {
		Action action = Mockito.mock(Action.class);
		ScenarioContext ctx = Mockito.mock(ScenarioContext.class);
		actor.setContext(ctx);
		actor.execute(action);
		Mockito.verify(ctx, Mockito.times(1)).setLastAction(Mockito.eq(action));
	}

}
