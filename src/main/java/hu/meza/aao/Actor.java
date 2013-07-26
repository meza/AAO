package hu.meza.aao;

public abstract class Actor {

	private Action lastAction;
	private ScenarioContext context;

	public void setContext(ScenarioContext context) {
		this.context = context;
	}

	public void execute(Action action) {
		action.execute();
		setLastAction(action);
	}

	public Action lastAction() {
		return lastAction;
	}

	protected void setLastAction(Action action) {
		lastAction = action;
		context.setLastAction(lastAction());
	}

}
