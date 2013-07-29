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
			throw new ContextSubjectAssumptionError(e.getMessage(), subject.getClass().getName());
		}
	}

	@Override
	public <T> void setSubject(T subject) {
		this.subject = subject;
	}

	@Override
	public void setLastAction(Action action) {
		this.action = action;
	}

	@Override
	public Action getLastAction() {
		return action;
	}

	@Override
	public void setLastActor(Actor actor) {
		this.actor = actor;
	}

	@Override
	public Actor getLastActor() {
		return actor;
	}

}
