package hu.meza.aao;

public class EmptyScenarioContext implements ScenarioContext {
	@Override
	public <T> T getSubject() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public <T> void setSubject(T subject) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void setLastAction(Action action) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public Action getLastAction() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
