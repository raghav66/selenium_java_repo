package com.rb.stepDef;

import com.rb.pages.SearchPage;
import io.cucumber.java.en.*;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;


public class SearchStepDef {

    SearchPage searchPage = new SearchPage();
    private int initialResultsCount = 0;

    @Given("I navigate to url")
    public void i_navigate_to_url() {
        searchPage.navigateToUrlBasedOnEnvironment();
        searchPage.handleLanguagePopUp();
    }


    @When("I search for {string}")
    public void i_search_for(String searchTerm) {
        searchPage.searchForItem(searchTerm);

    }

   @Then("I capture the total number of search results")
    public void i_capture_the_total_number_of_search_results() {
       initialResultsCount = searchPage.getTotalSearchResults();
       System.out.println("Total search results: " + initialResultsCount);
       int expectedMinimumCount=1;

       // Assert the count is numeric and greater than expected value
       Assert.assertTrue(initialResultsCount > expectedMinimumCount,
               "Expected results to be greater than " + expectedMinimumCount + ", but found: " + initialResultsCount);

   }

     @Then("I verify that the first result contains {string}")
    public void i_verify_that_the_first_result_contains(String expectedText) {
        String firstResultName=searchPage.getFirstResultName();
         Assert.assertTrue(
                 firstResultName.contains(expectedText),
                 "First search result does not contain the expected text: '" + expectedText + "', but found: '" + firstResultName + "'");
    }

    @When("I apply the Year filter from {string} to current year")
    public void iApplyTheYearFilterFromToCurrentYear(String startYear) {
        searchPage.applyYearFilter(startYear);
    }

  @Then("I verify the filtered results count is different from the initial count")
    public void verifyFilteredResultsCount() {
     int filteredResultsCount = searchPage.getTotalSearchResults();
      System.out.println("Total search results: " + filteredResultsCount);
      Assert.assertNotEquals(filteredResultsCount, initialResultsCount,
                    "Filtered search count should be different from initial count.");

    }
}