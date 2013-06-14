package hu.meza;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ActorManager {
	private Map<String, Actor> actors;
	private String lastLabel = "";


	protected ActorManager() {
		actors = new ConcurrentHashMap<String, Actor>();
	}

	public Actor getActor(String label) {

		if (isRelativeActor(label)) {
			return lastActor();
		}

		if (!actors.containsKey(label)) {
			String msg = String.format("Actor named: '%s' cannot be found within the actors", label);
			throw new RuntimeException(msg);
		}

		setLastActorLabel(label);
		return actors.get(label);
	}

	public Actor addActor(String label, Actor actor) {

		if (isRelativeActor(label)) {
			throw new IllegalArgumentException(
					String.format("%s is a relative actor, do not use as a label.", label));
		}
		actors.put(label, actor);
		setLastActorLabel(label);
		return actor;
	}

	public Actor lastActor() {
		return getActor(lastLabel);
	}

	private synchronized void setLastActorLabel(String label) {
		lastLabel = label;
	}

	private boolean isRelativeActor(String label) {
		return "he".equals(label.toLowerCase()) || "she".equals(label.toLowerCase());
	}

}
