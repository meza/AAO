package hu.meza.aao;

public class NoScenarioContext implements ScenarioContext {
	@Override
	public <T> T getSubject() {
		throw new NullObjecException();
	}

	@Override
	public <T> void setSubject(T subject) {

	}

	@Override
	public Action getLastAction() {
		throw new NullObjecException();
	}

	@Override
	public void setLastAction(Action action) {

	}

	@Override
	public Actor getLastActor() {
		throw new NullObjecException();
	}

	@Override
	public void setLastActor(Actor actor) {

	}
}
