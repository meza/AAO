package hu.meza.aao;

public class ContextSubjectAssumptionError extends RuntimeException {
	public ContextSubjectAssumptionError(String originalMessage, String expectedType) {
		super(String.format("You were assuming that %s is the subject, but casting to it failed" +
							"qith the following message: \n" +
							"%s", expectedType, originalMessage));

	}
}
