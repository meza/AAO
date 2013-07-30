package hu.meza.aao;

public class ActorNotFoundException extends RuntimeException {
	public ActorNotFoundException(String label) {
		super(String.format("Actor named: '%s' cannot be found within the actors", label));
	}
}
