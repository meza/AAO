package hu.meza.aao;

public class Actor {

	private Action lastAction;
	private ScenarioContext context;

	public Actor() {
		context = new NoScenarioContext();
	}

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
