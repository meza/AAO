package hu.meza.aao;

public abstract class Actor {

	private Action lastAction;

	public abstract <T> T authenticationData();

	public void execute(Action action) {
		action.setAuthenticationData(authenticationData());
		action.execute();
		lastAction = action;
	}

	public Action lastAction() {
		return lastAction;
	}

}
