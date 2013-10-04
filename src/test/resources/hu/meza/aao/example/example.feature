Feature: Showing off capabilities

  Scenario: Bikes
    Given Bob is interested in "bikes"
    And Jill is also interested in "bikes"
    When Bob searches for them
    And Jill does the same
    Then the first hit should be the same for each
