package hu.meza.aao;

public interface Action extends Cloneable {
	void execute();

	<T> T copyOf();
}
