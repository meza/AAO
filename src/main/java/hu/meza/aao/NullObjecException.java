package hu.meza.aao;

public class NullObjecException extends RuntimeException {
	public NullObjecException() {
		super("Accessing a null object for data, which is probably not a good idea");
	}
}
