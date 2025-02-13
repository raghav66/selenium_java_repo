Feature: SearchAndFilter_Screen

  @rb
  Scenario: TC_0001_Search for Ford F-150
    Given I navigate to url
    When I search for "Ford F-150"
    Then I capture the total number of search results
    And I verify that the first result contains "Ford F-150"


  @rb
  Scenario: TC_0002_Search for Ford F-150 and check the count after year filter applied
    Given I navigate to url
    When I search for "Ford F-150"
    Then I capture the total number of search results
    When I apply the Year filter from "2010" to current year
    Then I verify the filtered results count is different from the initial count
