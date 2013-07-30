package hu.meza.aao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ActorManager implements Iterable<Actor> {
	private Map<String, Actor> actors;
	private String lastLabel = "";
	private ScenarioContext context;

	public ActorManager() {
		actors = new ConcurrentHashMap<>();
		this.addContext(new NoScenarioContext());
	}

	public void addContext(ScenarioContext context) {
		this.context = context;
	}

	public Actor getActor(String label) {

		if (isRelativeActor(label)) {
			return lastActor();
		}

		if (!actors.containsKey(label)) {
			throw new ActorNotFoundException(label);
		}

		setLastActor(label);
		return actors.get(label);
	}

	public Actor addActor(String label, Actor actor) {

		if (isRelativeActor(label)) {
			throw new IllegalArgumentException(
					String.format("%s is a relative actor, do not use as a label.", label));
		}
		actor.setContext(context);
		actor.setLabel(label);
		actors.put(label, actor);
		setLastActor(label);
		return actor;
	}

	public Actor lastActor() {
		if (actors.isEmpty()) {
			throw new ActorManagerIsEmptyException();
		}
		return getActor(lastLabel);
	}

	@Override
	public Iterator<Actor> iterator() {
		List<Actor> actorList = new ArrayList<>();

		for (Map.Entry<String, Actor> entry : actors.entrySet()) {
			actorList.add(entry.getValue());
		}

		return actorList.iterator();

	}

	private synchronized void setLastActor(String label) {
		lastLabel = label;
		context.setLastActor(actors.get(label));
	}

	private boolean isRelativeActor(String label) {
		return "he".equals(label.toLowerCase()) || "she".equals(label.toLowerCase());
	}

}
