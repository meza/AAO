Feature: Showing off capabilities

  Scenario: stuff
    Given Bob is interested in bikes
    And Jill is also interested in bikes
    When Bob searches for them on Bing
    And Jill does the same on Google
    Then the first hit should be the same for each
