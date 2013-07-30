package hu.meza.aao;

public abstract class RestfulActor extends Actor {

	public abstract <T> T authenticationData();

	public void execute(RestfulAction action) {
		action.setAuthenticationData(authenticationData());
		super.execute(action);
	}

}
