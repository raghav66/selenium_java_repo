package com.rb.runner;

import com.rb.framework.DriverManager;
import com.rb.framework.GlobalParams;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import org.apache.logging.log4j.ThreadContext;
import org.testng.annotations.*;

public class RunnerBase {
    GlobalParams params = new GlobalParams();
    private static final ThreadLocal<TestNGCucumberRunner> testNGCucumberRunner = new ThreadLocal<>();

    public static TestNGCucumberRunner getRunner() {
        return testNGCucumberRunner.get();
    }

    private static void setRunner(TestNGCucumberRunner runner) {
        testNGCucumberRunner.set(runner);
    }

    @Parameters({"browserName", "clientName", "executionEnv", "tagName"})
    @BeforeClass(alwaysRun = true)
    public void setUpClass(String browserName, String clientName, String executionEnv,String tagName) throws Exception {
        ThreadContext.put("ROUTINGKEY", browserName + "_" + browserName);
        GlobalParams params = new GlobalParams();
        params.setBrowserName(browserName);
        params.setClientName(clientName);
        params.setExecutionEnv(executionEnv);
        params.setTagName(tagName);
        new DriverManager().initializeDriver();
        setRunner(new TestNGCucumberRunner(this.getClass()));
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    public void scenario(PickleWrapper pickle, FeatureWrapper cucumberFeature) throws Throwable {
        getRunner().runScenario(pickle.getPickle());
    }

    @DataProvider
    public Object[][] scenarios() {
        System.out.println("Providing scenarios...");
        return getRunner().provideScenarios();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        System.out.println("Tearing down class...");
        try {
            DriverManager driverManager= new DriverManager();
            if (driverManager.getDriver() != null) {
                System.out.println("Quitting driver...");
               // driverManager.getDriver().quit();
                driverManager.setDriver(null);
            }
        } finally {
            getRunner().finish();
            System.out.println("Cucumber runner finished.");
        }
    }
}
