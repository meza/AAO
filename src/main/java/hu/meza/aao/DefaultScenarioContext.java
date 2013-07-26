package hu.meza.aao;

public class DefaultScenarioContext implements ScenarioContext {

	private Object subject;
	private Action action;

	@Override
	public <T> T getSubject() {

		try {
			return (T) subject;
		} catch (Exception e) {
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

}
