package com.rb.locators;

public class WebLocators {
    public static class Home{
        public static final String SELECT_LANGUAGE_POPUP ="//h1[normalize-space()='Confirm your language and region']~xpath";
        public static final String SELECT_LANGUAGE ="//div[@aria-controls='Asia & Pacific']//span[normalize-space()='English']~xpath";
        public static final String SEARCH_BOX ="//input[@placeholder='Search over 113,000 items']~xpath";
        public static final String SEARCH_BUTTON="button[data-testid='search button']~css";
        public static final String SEARCH_TOTAL_RESULT="//h2[@data-testid='non-cat-header']~xpath";
        public static final String SEARCH_RESULT_LIST="[data-testid^='searchResultItemCard-'] h4 a~css";
        public static final String YEAR_FILTER_BUTTON="#manufactureYearRange-header~css";
        public static final String START_YEAR_FILTER_MIN_BUTTON="manufactureYearRange_min~id";
        public static final String START_YEAR_FILTER_MAX_BUTTON="manufactureYearRange_max~id";
    }

}
