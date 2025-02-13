package com.rb.pages;

import com.rb.framework.TestUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.rb.locators.WebLocators.Home.*;
import static com.rb.utility.PageActionLibrary.*;


public class SearchPage extends BasePage {

    TestUtils utils = new TestUtils();

    public void handleLanguagePopUp() {
        if(elementDisplayed(SELECT_LANGUAGE_POPUP))
            clickElement(SELECT_LANGUAGE);
        waitForMilliSeconds(1000);
    }

    public void searchForItem(String searchTerm) {
        sendText(SEARCH_BOX,searchTerm);
        waitForPageLoad(1);
        clickElement(SEARCH_BUTTON);
        waitForMilliSeconds(5000);
    }

    public int getTotalSearchResults() {
        String resultText = getText(SEARCH_TOTAL_RESULT);
        Pattern pattern = Pattern.compile("\\d+ of (\\d+)"); // Extracts the total count
        Matcher matcher = pattern.matcher(resultText);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1)); // Extract total results
        } else {
            throw new RuntimeException("Failed to extract total search result count");
        }
    }

    public String getFirstResultName() {
        System.out.println("getElementNameList(SEARCH_RESULT_LIST):"+getElementNameList(SEARCH_RESULT_LIST));
        return getElementNameList(SEARCH_RESULT_LIST).get(0);
    }

    public void applyYearFilter(String startYear) {
        int currentYear = java.time.Year.now().getValue();
        System.out.println("Current year Value:"+currentYear);
        String yearFilterAriaStatus=getAttribute(YEAR_FILTER_BUTTON,"aria-expanded");
        if (yearFilterAriaStatus != null && yearFilterAriaStatus.equalsIgnoreCase("false")) {
            scrollToAndClickElement(YEAR_FILTER_BUTTON);
            waitForMilliSeconds(1000);
        }
        clickElement(START_YEAR_FILTER_MIN_BUTTON);
        waitForMilliSeconds(1000);
        clearTextWithDelete(START_YEAR_FILTER_MIN_BUTTON);
        waitForMilliSeconds(1000);
        sendText(START_YEAR_FILTER_MIN_BUTTON,startYear);
        waitForMilliSeconds(1000);
        clickElement(START_YEAR_FILTER_MIN_BUTTON);
        waitForMilliSeconds(1000);
        clearTextWithDelete(START_YEAR_FILTER_MAX_BUTTON);
        waitForMilliSeconds(1000);
        sendText(START_YEAR_FILTER_MAX_BUTTON, String.valueOf(currentYear));
        waitForMilliSeconds(1000);
        pressEnter(START_YEAR_FILTER_MAX_BUTTON);
        waitForMilliSeconds(1000);
    }
}
