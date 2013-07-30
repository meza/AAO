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

Reframing the scenario above from a first person view to a third person view however solves many problems.

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

Instead, we came to realize that _every action in the product must be available to every actor._
__The ability for an actor to carry out an action should be decided by the product, and not the testing framework.__
How could you test that a simple user cannot reach the admin functions, if your framework forbids him to even try?


### Usage

#### Maven

```
<dependency>
  <groupId>hu.meza</groupId>
  <artifactId>aao</artifactId>
  <version>RELEASE</version>
</dependency
```

or if you're interested in the bleding edge:
```
<dependency>
  <groupId>hu.meza</groupId>
  <artifactId>aao</artifactId>
  <version>LATEST_SNAPSHOT</version>
</dependency
```



The code below is a ___rough___ implementation of the scenario above.

You should:
- __Only share state through ActorManager->actor->lastAction__
  As soon as you start introducing step definition class members, you start tying steps together.
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
public class LoggedInUser extends RestfulActor {

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

public class ViewProfileOf implements RestfulAction {

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

## Context awareness

During a chat with the lovely Liz Keogh, we agreed that in normal conversations, we tend to use relative references
of things, people and happenings, and we all know, that the most important value of BDD is capturing conversations.
My biggest worry with this approach is that the simplest way of keeping context between steps is using class members
in the step definition code. This however would circle back to the original problem I was trying to solve.
So tempted by this discussion, context awarenes came to life in this "framework".

### Parts

The main part of the system is the ```ScenarioContext```. It can hold the subject the conversation is happening
about, the last ```Actor``` mentioned and the last ```Action``` executed.

To use it for subject tracking, you need to manually set the subject within a step definition where needed.

To use it for ```Actor``` and ```Action``` tracking, you need to pass your ```ScenarioContext``` object to the
```ActorManager``` or an individual ```Actor```. (I'd recommend the former)

### Usage

Until further documentation, please refer to the example in ```hu.meza.aao.example.ContextAwareStepDefs```
