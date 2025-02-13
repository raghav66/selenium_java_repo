package com.rb.runner;

import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;


@CucumberOptions(
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        features = {"src/test/resources/"},
        glue = {"com.rb.stepDef"}
)

public class SanityRunnerTest extends RunnerBase {

    @Parameters({"browserName", "clientName", "executionEnv","tagName"})
    @BeforeClass
    @Override
    public void setUpClass(String browserName, String clientName, String executionEnv, String tags) throws Exception {
        System.out.println("SanityRunnerTest setup with parameters:");
        System.out.println("browserName: " + browserName);
        System.out.println("clientName: " + clientName);
        System.out.println("executionEnv: " + executionEnv);
        System.out.println("tags: " + tags);
        super.setUpClass(browserName, clientName, executionEnv, tags);
    }


}
