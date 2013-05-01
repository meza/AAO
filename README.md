Actors-Actions-Outcomes framework
---------------------------------

Following the Cucucmber book, and many examples on the internet, the most common format of wording scenarios
is the following:

```gherkin
  Scenario: some scenario
   Given I am a logged in user
    When I view my profile
    Then I should see my details
```

While this works for simple projects, it can get quite messy with complicated ones.
Imagine a scenario where you have to describe interaction between multiple users.
This would result in a pretty awkward and twisted either code, or language usage.

For example the following might even read right, but the implementation of such steps would be a shared state nightmare.
Who am I? Who is "the other?"

```gherkin
  Scenario: view someone's profile
    Given I am a logged in user
      And there is another logged in user
    When I view the other user's profile
    Then I should see the profile data
      And the other user should receive a notification that I have viewed their profile
```

Reframing the scenario above frmo a first person view to a third person view solves many problems.

```gherkin
  Scenario: view someone's profile
    Given Fred is a logged in user
      And Jill is a logged in user
    When Fred views the profile of Jill
    Then Fred should see the profile details of Jill
      And Jill should receive a notification that Fred viewed her profile
```

This format is much easier to understand and automate too, and it could be used with personas really nicely.

When creating this 'framework', we wanted something lightweight and easy to use. Sharing state between steps
seemed to cause most of our problems. It resulted in steps strongly tied together, and we did not want that
to happen.

Instead, we came to realize that every action in the product must be available to every actor.
The ability for an actor to carry out an action should be decided by the product, and not the testing framework.
How could you test that a simple user cannot reach the admin functions, if your framework forbids him to even try?


### Usage

The code below is a ___rough___ implementation of the scenario above.

You should:
- Extend custom ActorManagers for different types of users. Use Cucumber's DI to pass them on.
- Implement Actions. It is tempting to create a god action that will for example do all the HTTP GET commands,
  but that would couple all child actions together strongly. Each action will only be modified because that
  action needs to be modified, therefore they should not be coupled. It is not code duplication if the intent
  of the code is not the same.
- Customize the Actor and Action abstract and interface to your own needs. Add user authentication, custom
  request, response, etc. Remember the goal is not to share state between steps.
- Be ruthless. Your automation should do what you want it to do. Do not compromise with methods that might seem
  supported. Everything is supported with a little effort. Make it readable, understandable and easy to use.

```java
public class LoggedInUser extends Actor {

	private String name;

	LoggedInUser(String name) {
		this.name = name;
	}

	@Override
	public Object authenticationData() {
		return "username:password";
	}

	public String name() {
		return name;
	}
}

public class ViewProfileOf implements Action {

	private Object authData;
	private LoggedInUser targetUser;
	private Object response;
	private String request;

	ViewProfileOf(LoggedInUser targetUser) {
		this.targetUser = targetUser;
	}

	@Override
	public void setAuthenticationData(Object data) {
		authData = data;
	}

	@Override
	public void execute() {
		String url = String.format("http://%s@example.com/user/%s", authData, targetUser.name());
		request = url;
		response = httpGet(url);
	}

	@Override
	public Object requestData() {
		return request;
	}

	@Override
	public Object responseData() {
		return response;
	}
}


public class MyStepdefs {

	private ActorManager actorManager;

	public MyStepdefs(ActorManager actorManager) {
		this.actorManager = actorManager;
	}

	@Given("^([^/s]+) is a logged in user$")
	public void isALoggedInUser(String actorLabel) {
		LoggedInUser actor = new LoggedInUser(actorLabel);
		actorManager.addActor(actorLabel, actor);
	}

	@When("^([^/s]+) views the profile of ([^/s]+)$")
	public void viewsTheProfile(String actor1Label, String actor2Label) {
		Actor performingActor = actorManager.getActor(actor1Label);
		LoggedInUser targetActor = (LoggedInUser) actorManager.getActor(actor2Label);

		Action viewProfileAction = new ViewProfileOf(targetActor);
		performingActor.execute(viewProfileAction);
	}

	@Then("^([^/s]+) should see the profile details of ([^/s]+)")
	public void shouldSeeTheProfileDetails(String actor1Label, String actor2Label) {
		Actor performingActor = actorManager.getActor(actor1Label);
		Actor targetActor = actorManager.getActor(actor2Label);

		Object lastResponse = performingActor.lastAction().responseData();
		Assert.assertTrue(hasProfileDataOf(lastResponse, targetActor));
	}

	private boolean hasProfileDataOf(Object lastResponse, Actor targetActor) {
		// do something
		return true;
	}

}
```