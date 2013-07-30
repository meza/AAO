package hu.meza.aao;

public class DefaultScenarioContext implements ScenarioContext {

	private Object subject;
	private Action action;
	private Actor actor;

	@Override
	public <T> T getSubject() {

		try {
			return (T) subject; //Why, oh why can't I catch class cast exceptions, dear Java!?
		} catch (ClassCastException e) {
			//Although this is dead code, I left it here to show my intentions.
			throw new ContextSubjectAssumptionError(e.getMessage(), subject.getClass().getName());
		}
	}

	@Override
	public <T> void setSubject(T subject) {
		this.subject = subject;
	}

	@Override
	public Action getLastAction() {
		if (action == null) {
			return null;
		}
		return action.copyOf();
	}

	@Override
	public void setLastAction(Action action) {
		this.action = action;
	}

	@Override
	public Actor getLastActor() {
		return actor;
	}

	@Override
	public void setLastActor(Actor actor) {
		this.actor = actor;
	}

	@Override
	public void clean() {
		subject = null;
		action = null;
		actor = null;
	}

}
