package hu.meza.aao;

public class ActorManagerIsEmptyException extends RuntimeException {
	public ActorManagerIsEmptyException() {
		super("No actors were added to the actor manager yet.");
	}
}
