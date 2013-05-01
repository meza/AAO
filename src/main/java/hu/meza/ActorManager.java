package hu.meza;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ActorManager {
	private Map<String, Actor> actors;

	protected ActorManager() {
		actors = new ConcurrentHashMap<String, Actor>();
	}

	public Actor getActor(String label) {
		if (!actors.containsKey(label)) {
			String msg = String.format("Actor named: '%s' cannot be found within the actors", label);
			throw new RuntimeException(msg);
		}

		return actors.get(label);
	}

	public Actor addActor(String label, Actor actor) {
		actors.put(label, actor);
		return actor;
	}
}
