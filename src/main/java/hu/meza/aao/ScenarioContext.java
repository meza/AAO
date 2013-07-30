package hu.meza.aao;

public interface ScenarioContext {
	<T> T getSubject();

	<T> void setSubject(T subject);

	Action getLastAction();

	void setLastAction(Action action);

	Actor getLastActor();

	void setLastActor(Actor actor);

	void clean();
}
