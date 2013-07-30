package hu.meza.aao;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class NoScenarioContextTest {

	private NoScenarioContext ctx;

	@Before
	public void setUp() throws Exception {
		ctx = new NoScenarioContext();

	}

	@Test(expected = NullObjecException.class)
	public void testGetSubject() {
		ctx.setSubject("x");
		ctx.getSubject();
	}

	@Test(expected = NullObjecException.class)
	public void testGetLastAction() {
		ctx.setLastAction(Mockito.mock(Action.class));
		ctx.getLastAction();
	}

	@Test(expected = NullObjecException.class)
	public void testGetLastActor() {
		ctx.setLastActor(Mockito.mock(Actor.class));
		ctx.getLastActor();
	}

}
