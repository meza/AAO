package hu.meza.aao;

public interface ScenarioContext {
	<T> T getSubject();

	<T> void setSubject(T subject);

	void setLastAction(Action action);

	Action getLastAction();
}
