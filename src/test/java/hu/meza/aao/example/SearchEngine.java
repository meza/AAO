package hu.meza.aao.example;

public enum SearchEngine {

	BING("Bing"),
	GOOGLE("Google");
	private final String engine;


	SearchEngine(String engine) {
		this.engine = engine;
	}

	public static SearchEngine fromString(String type) {
		if (type == null) {
			throw new IllegalArgumentException();
		}
		for (SearchEngine engineType : SearchEngine.values()) {
			if (type.equalsIgnoreCase(engineType.engine)) {
				return engineType;
			}
		}
		throw new IllegalArgumentException(String.format("Unrecognized engine: %s", type));
	}

}
