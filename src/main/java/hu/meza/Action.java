package hu.meza;

public interface Action {
	<T> void setAuthenticationData(T data);

	void execute();

	<T> T requestData();

	<T> T responseData();
}
