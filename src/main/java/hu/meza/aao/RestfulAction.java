package hu.meza.aao;

public interface RestfulAction extends Action {
	<T> void setAuthenticationData(T data);

	void execute();

	<T> T requestData();

	<T> T responseData();
}
