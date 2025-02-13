package com.rb.pages;

import com.rb.framework.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.rb.framework.PropertyManager.getProps;


public class BasePage {

    public static WebDriver driver;
    TestUtils utils = new TestUtils();
    GlobalParams params = new GlobalParams();

    public BasePage() {
        driver = new DriverManager().getDriver();
    }

    public void closeApp() {
        utils.log().info("Closing the driver");
        driver.quit();
    }

    public void waitForPageLoad(double timeoutInSeconds) {
        try {
            Thread.sleep(500);
            new WebDriverWait(driver, Duration.ofSeconds((long) timeoutInSeconds)).until(
                    webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
            );
        } catch (TimeoutException e) {
            System.out.println("Page did not load within " + timeoutInSeconds + " seconds.");
        } catch (InterruptedException e) {
            System.out.println("Exception while taking screenshot " + e.getMessage());
        }
    }


   

    public void navigateToUrlBasedOnEnvironment() {
        String executionEnv = params.getExecutionEnv().toLowerCase();
        String urlKey = "rb_" + executionEnv + "_url";
        String url = getProps().get(urlKey);
        System.out.println("url:"+url);
        driver.navigate().to(url);
        waitForPageLoad(5);
    }


}
