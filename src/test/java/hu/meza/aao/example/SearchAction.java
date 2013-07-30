package hu.meza.aao.example;

import hu.meza.aao.Action;

public class SearchAction implements Action {

	private final String searchTerm;

	public SearchAction(String searchTerm) {

		this.searchTerm = searchTerm;
	}

	@Override
	public void execute() {
		// search on the internet for searchTerm
	}

	public SearchAction copyOf() {
		return new SearchAction(searchTerm);
	}

	public String searchResult() {
		return "result";
	}

}
