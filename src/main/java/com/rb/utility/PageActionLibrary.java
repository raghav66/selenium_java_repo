package com.rb.utility;

import com.rb.pages.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PageActionLibrary extends BasePage {
    private static final Logger logger = LogManager.getLogger(PageActionLibrary.class);
    /**
     * To get first visible element
     *
     * @param locator     Identifier of element
     * @return WebElement     *
     */
    public static WebElement getElementByVisibility(String locator) {
        By byElement = ElementExtractor.getElement(locator);
        try {
            WebDriverWait wait = new WebDriverWait(BasePage.driver, Duration.ofSeconds(30));
            logger.info("Checking visibility of element: " + locator);
            return wait.until(ExpectedConditions.visibilityOfElementLocated(byElement));
        } catch (Exception e) {
            logger.error("Exception occurred while waiting for element visibility: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * To get first visible element
     *
     * @param locator Identifier of element
     * @return List<WebElement>     *
     */
    public static List<WebElement> getElementsByVisibility(String locator) {
        By byElement = ElementExtractor.getElement(locator);
        try {
            WebDriverWait wait = new WebDriverWait(BasePage.driver, Duration.ofSeconds(30));
            logger.info("Checking visibility of elements: {}", locator);
            return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(byElement));
        } catch (TimeoutException e) {
            logger.error("Timeout waiting for elements visibility: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Exception occurred while waiting for elements visibility: {}", e.getMessage());
        }
        return Collections.emptyList(); // Return an empty list if there's an exception
    }

    /**
     * To click on element
     *
     * @param locator *
     */
    public static void clickElement(String locator) {
        WebElement element = getElementByVisibility(locator);
        if (element != null) {
            try {
                logger.info("Clicking on element:"+locator);
                element.click();
            } catch (Exception e) {
                logger.info(e);
                System.out.println(e);
            }
        }
    }

    /**
     * To click on element
     *
     * @param locator *
     * @return boolean
     */
    public static boolean elementDisplayed(String locator) {
        WebElement element = getElementByVisibility(locator);
        if (element != null) {
            try {
                logger.info("element is displayed:"+locator);
                return element.isDisplayed();
            } catch (Exception e) {
                logger.info(e);
                System.out.println(e);
            }
        }
        return false;
    }

    public static boolean isButtonEnabled(String locator) {
        WebElement button = getElementByVisibility(locator);
        if (button != null) {
            try {
                logger.info("Checking if button is enabled: " + locator);
                return button.isEnabled();
            } catch (Exception e) {
                logger.error("Exception occurred while checking button status: " + e.getMessage());
            }
        }
        return false;
    }

    public static boolean isButtonDisable(String locator) {
        WebElement button = getElementByVisibility(locator);
        if (button != null) {
            try {
                logger.info("Checking if button is disable: " + locator);
                return button.getAttribute("disabled").equals("true");
            } catch (Exception e) {
                logger.error("Exception occurred while checking button status: " + e.getMessage());
            }
        }
        return false;
    }


    /**
     * To wait for provided milliseconds
     *
     * @param milliSeconds *
     */
    public static void waitForMilliSeconds(int milliSeconds) {
        try {
            logger.info("Waiting for " + milliSeconds + " Miliseconds..");
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            logger.info(e);
            e.printStackTrace();
        }
    }


    /**
     * To send text to element
     *
     * @param locator*
     */
    public static void sendText(String locator, String text) {
        WebElement element = getElementByVisibility( locator);
        if (element != null) {
            try {
                logger.info("Send keys on element:"+element+text);
                element.clear();
                webDriverWait(Duration.ofSeconds(1));
                element.sendKeys(text);
            } catch (Exception e) {
                logger.info(e);
                System.out.println(e);
            }
        }
    }


    public static List<String> getElementNameList(String locator) {
        List<String> elementText = new ArrayList<>();
        By byElement = ElementExtractor.getElement(locator);
        List<WebElement> myElements = BasePage.driver.findElements(byElement);
        for(WebElement e : myElements) {
            elementText.add(e.getText());
        }
        return elementText;
    }

    public static void openLinkInNewTab(String locator) {
        String selectLinkToOpenInNewTab = Keys.chord(Keys.CONTROL,"t");
        WebElement element = getElementByVisibility( locator);
        if (element != null) {
            try {
                element.sendKeys(selectLinkToOpenInNewTab);
            } catch (Exception e) {
                logger.info(e);
                System.out.println(e);
            }
        }
       // driver.findElement(By.linkText("urlLink")).sendKeys(selectLinkOpeninNewTab);
    }


    /**
     * To get text from an element
     *
     * @param locator The locator used to identify the element
     * @return The text content of the element, or null if the element is not found
     */
    public static String getText(String locator) {
        WebElement element = getElementByVisibility(locator);
        // Check if the element is not null
        if (element != null) {
            try {
                // Log information about the operation
                logger.info("Getting text from element: " + element);
                // Return the text content of the element
                String text = element.getText();
                System.out.println("Element Text: "+text);
                return text;
            } catch (Exception e) {
                // Log and print any exceptions that occur
                logger.info(e);
                System.out.println(e);
            }
        }
        // Return null if the element is not found
        return null;
    }

    /**
     * Create a WebDriverWait instance with the given WebDriver and Duration
     *
     * @param duration The duration for WebDriverWait
     */
    public static void webDriverWait(Duration duration) {
        new WebDriverWait(BasePage.driver, duration);
    }


    public static void enterOtp(String locator, String otp) {
        waitForMilliSeconds(3000);
        List<WebElement> otpBox = getElementsByVisibility(locator);
        System.out.println("otpBox:"+otpBox.size());
        if (otpBox != null) {
            for (int i = 0; i < Math.min(otp.length(), otpBox.size()); i++) {
                String digit = String.valueOf(otp.charAt(i));
                System.out.println("digit:"+digit);
                otpBox.get(i).sendKeys(digit);
            }
        } else {
            logger.info("Element not found or not visible.");
        }
    }

    public static void pageActionOption(String option) {
        switch (option.toLowerCase()) {
            case "back" -> {
                BasePage.driver.navigate().back();
            }
            case "forward" -> {
                BasePage.driver.navigate().forward();
            }
            default -> {
                System.out.println("Invalid option");
                Assert.fail("Invalid option selected");
            }
        }
    }

    public static String getAttribute(String locator, String attributeName) {
        WebElement element = getElementByVisibility(locator);
        // Check if the element is not null
        if (element != null) {
            try {
                // Log information about the operation
                logger.info("Getting text from element: " + element);
                // Return the text content of the element
                return element.getAttribute(attributeName);
            } catch (Exception e) {
                // Log and print any exceptions that occur
                logger.info(e);
                System.out.println(e.getMessage());
            }
        }
        // Return null if the element is not found
        return null;
    }

    public static void actionClick(String locator) {
        WebElement element = getElementByVisibility(locator);
        ((JavascriptExecutor) BasePage.driver).executeScript("arguments[0].click()", element);
    }
    public static void scrollToAndClickElement(String locator) {
        scrollToElement(locator);
        actionClick(locator);
    }

    public static void scrollToElement(String locator) {
        WebElement element = getElementByVisibility(locator);
        ((JavascriptExecutor) BasePage.driver).executeScript("arguments[0].scrollIntoView(false)", element);
    }

    public static void scrollToTop() {
        new Actions(BasePage.driver).keyDown(Keys.COMMAND).sendKeys(Keys.ARROW_UP).perform();
    }


    public static void scrollToBottom() {
        ((JavascriptExecutor) BasePage.driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }


    public static void clearTextBox(String locator) {
        WebElement element = getElementByVisibility( locator);
        if (element != null) {
            try {
                logger.info("clear text on element:"+element);
                element.clear();
                webDriverWait(Duration.ofSeconds(1));
                element.clear();
            } catch (Exception e) {
                logger.info(e);
                System.out.println(e);
            }
        }
    }

    public static void selectDropdownOptionByText(String locator, String optionText) {
        // Locate the dropdown element using getElementByVisibility (assuming it is a custom method for visibility)
        WebElement dropdown = getElementByVisibility(locator);

        if (dropdown != null) {
            try {
                // Find all list items (li elements) within the dropdown
                List<WebElement> options = dropdown.findElements(By.tagName("li"));

                // Loop through the options and select the one that matches the given text
                for (WebElement option : options) {
                    if (option.getText().contains(optionText)) {
                        logger.info("Clicking on option: {}", optionText);
                        option.click();  // Click the matching option
                        webDriverWait(Duration.ofSeconds(1));  // Add a small wait
                        return;  // Exit the method once the option is selected
                    }
                }

                // If the option is not found, log and throw an exception
                String errorMsg = "Option with text '" + optionText + "' not found in dropdown.";
                logger.info(errorMsg);
                throw new NoSuchElementException(errorMsg);
            } catch (Exception e) {
                logger.info("Exception occurred: {}", e.getMessage());
                System.out.println(e.getMessage());
            }
        } else {
            logger.info("Dropdown element not found using locator: {}", locator);
        }
    }

    public static void scrollIntoViewAndClick(WebElement element) {
        try {
            // Scroll element into view
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", element);

            // Wait for the element to be clickable (Optional: You can tweak the timeout)
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(element));

            // Click the element
            element.click();
            System.out.println("Successfully scrolled and clicked the element.");
        } catch (ElementClickInterceptedException e) {
            System.out.println("Element click intercepted, retrying using JavaScript click.");

            // Fallback: If normal click fails, perform a JS click
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            System.out.println("Failed to scroll and click the element: " + e.getMessage());
        }
    }

    public static void pressEnter(String locator) {
        WebElement element = getElementByVisibility(locator);
        if (element != null) {
            try {
                logger.info("Pressing Enter on element: " + locator);
                element.sendKeys(Keys.RETURN);  // Or use Keys.ENTER
            } catch (Exception e) {
                logger.info("Exception while pressing Enter: " + e);
                System.out.println(e);
            }
        }
    }

    public static void clearTextWithDelete(String locator) {
        WebElement element = getElementByVisibility(locator);
        if (element != null) {
            try {
                logger.info("Clearing text on element: " + locator);

                // Click to focus on the text field
                element.click();
                waitForMilliSeconds(500);
                // Select all text (for complete clearing)
                element.sendKeys(Keys.CONTROL + "a"); // Windows/Linux
                element.sendKeys(Keys.COMMAND + "a"); // Mac (Optional)

                // Press DELETE key 5 times
                for (int i = 0; i < 4; i++) {
                    element.sendKeys(Keys.DELETE);
                    waitForMilliSeconds(500); // Small wait between deletes
                }

            } catch (Exception e) {
                logger.info("Exception while clearing text: " + e);
                System.out.println(e);
            }
        }
    }

}
