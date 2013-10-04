package hu.meza.aao.example;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import hu.meza.aao.Action;
import hu.meza.aao.Actor;
import hu.meza.aao.ActorManager;
import hu.meza.aao.DefaultScenarioContext;
import org.junit.Assert;

public class ContextAwareStepDefs {

	private final ActorManager actorManager;
	private final DefaultScenarioContext ctx;

	public ContextAwareStepDefs(ActorManager actorManager, DefaultScenarioContext ctx) {
		this.actorManager = actorManager;
		this.ctx = ctx;

		this.actorManager.addContext(this.ctx);
	}

	@Before
	public void cleanup() {
		ctx.clean();
	}

	@Given("^(.*) is interested in \"([^\"]*)\"$")
	public void isInterestedIn(String actorLabel, String searchTerm) {
		actorManager.addActor(actorLabel, new Actor());
		ctx.setSubject(searchTerm);
	}

	@Given("^(.*) is also interested in \"([^\"]*)\"$")
	public void isAlsoInterestedIn(String actorLabel, String searchTerm) {
		isInterestedIn(actorLabel, searchTerm);
	}

	@When("^(.*) searches for them$")
	public void searchesForThemOn(String actorLabel) {
		String subject = ctx.getSubject();
		SearchAction action = new SearchAction(subject);
		Actor actor = actorManager.getActor(actorLabel);
		actor.execute(action);
	}

	@When("^(.*) does the same$")
	public void doesTheSameOn(String actorLabel) {
		Actor actor = actorManager.getActor(actorLabel);
		Action action = ctx.getLastAction();
		actor.execute(action);
	}

	@Then("^the first hit should be the same for each$")
	public void theFirstHitShouldBeTheSameForEach() {

		String previousResult = null;
		for (Actor actor : actorManager) {
			SearchAction action = (SearchAction) actor.lastAction();

			String result = action.searchResult();

			if (previousResult == null) {
				previousResult = result;
			}

			Assert.assertEquals(
				String.format("The search term did not yield the same results for %s", actor.getLabel()),
				previousResult, result);

		}

	}

}
