package com.rb.stepDef;

import com.rb.framework.DriverManager;
import com.rb.framework.GlobalParams;
import com.rb.pages.BasePage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.IOException;

public class Hooks{

        @Before
        public void initialize(Scenario scenario) throws Exception {
            String scenarioName = scenario.getName();
            System.out.println("scenario Name is: "+scenarioName);
        }

        @After
        public void quit(Scenario scenario) throws IOException {
            byte[] src ;
            try {
                // Create a reference for TakesScreenshot
                TakesScreenshot screenshot = (TakesScreenshot) new DriverManager().getDriver();

                // Capture screenshot as bytes
                src = screenshot.getScreenshotAs(OutputType.BYTES);

                // Attach screenshot to the scenario
                scenario.attach(src, "image/png", scenario.getName());
            } catch (Exception e) {
                // Log any exceptions encountered
                System.out.println("Exception while taking screenshot: " + e.getMessage());
            }
        }
}
