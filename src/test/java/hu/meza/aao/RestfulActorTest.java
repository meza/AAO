package hu.meza.aao;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.UUID;

public class RestfulActorTest {

	@Test
	public void testExecute() {

		String data = UUID.randomUUID().toString();

		RestfulAction action = Mockito.mock(RestfulAction.class);
		RestfulActor actor = new TestRestfulActor(data);

		actor.execute(action);

		Mockito.verify(action, Mockito.atLeastOnce()).setAuthenticationData(data);

	}

	class TestRestfulActor extends RestfulActor {

		private final Object testData;

		TestRestfulActor(Object testData) {
			this.testData = testData;
		}

		@Override
		public <T> T authenticationData() {
			return (T) testData;
		}
	}
}
