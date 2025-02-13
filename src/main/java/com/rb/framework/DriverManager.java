package com.rb.framework;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class DriverManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    TestUtils utils = new TestUtils();
    GlobalParams params = new GlobalParams();

    public WebDriver getDriver(){
        return driver.get();
    }

    public void setDriver(WebDriver driver2){
        driver.set(driver2);
    }

    public void initializeDriver() throws Exception {
        WebDriver driver;
        try {
            utils.log().info("initializing WebDriver:"+params.getBrowserName());
            switch (params.getBrowserName().toLowerCase()) {
                case "firefox" -> {
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions options = new FirefoxOptions();
                     options.addArguments("--headless");
                    driver = new FirefoxDriver(options);
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
                    driver.manage().deleteAllCookies();
                    driver.manage().window().maximize();
                    utils.log().info("Firefox driver is launched");
                }
                case "ie" -> {
                    WebDriverManager.iedriver().setup();
                    driver = new InternetExplorerDriver();
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
                    driver.manage().deleteAllCookies();
                    driver.manage().window().maximize();
                    utils.log().info("IE driver is launched");
                }
                case "safari" -> {
                    WebDriverManager.safaridriver().setup();
                    SafariOptions options = new SafariOptions();
                    driver = new SafariDriver(options);
                    driver.manage().window().setSize(new Dimension(390, 844));
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
                    driver.manage().deleteAllCookies();
                    utils.log().info("Safari driver is launched with iPhone 12 Pro view");
                }
                case "chrome" -> {
                  //  WebDriverManager.chromedriver().browserVersion("127.0.6533.88").setup();
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                   // options.addArguments("--headless");
                    driver = new ChromeDriver(options);
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
                    driver.manage().window().maximize();
                    utils.log().info("Chrome driver is launched");
                    // Clear cookies
                    driver.manage().deleteAllCookies();
                    utils.log().info("Cookies cleared for Chrome driver");
                }
                default -> {
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    Map<String, Object> mobileEmulation = new HashMap<>();
                    mobileEmulation.put("deviceName", "iPhone 12 Pro");
                    options.setExperimentalOption("mobileEmulation", mobileEmulation);
                    options.addArguments("--headless");
                    driver = new ChromeDriver(options);
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
                    utils.log().info("Default Chrome driver is launched");
                }
            }
            if (driver == null) {
                throw new Exception("driver is null. ABORT!!!");
            }
            utils.log().info("Driver is initialized");
            this.driver.set(driver);
            Thread.sleep(5000);
        } catch (IOException e) {
            e.printStackTrace();
            utils.log().fatal("Driver initialization failure. ABORT !!!!" + e.getMessage());
            throw e;
        }

    }
}
