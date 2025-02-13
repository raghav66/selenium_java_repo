package com.rb.framework;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ExtentManager {
    private static ExtentReports extent;
    private static Map<Long, ExtentTest> testMap = new HashMap<>();
    private static Properties props = new Properties();

    static {
        try {
            props.load(new FileInputStream("src/main/resources/extent.properties"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ExtentReports getInstance() {
        if (extent == null) {
            createInstance();
        }
        return extent;
    }

    private static void createInstance() {
        // Try and catch block code only for local use or in TestNG execution.
        // BDD execution will not use this.
        try {
            // Check if the script exists and is executable
            File script = new File("./cleanup.sh");
            if (script.exists() && script.canExecute()) {
                ProcessBuilder processBuilder = new ProcessBuilder("./cleanup.sh");
                processBuilder.directory(script.getParentFile()); // Use the directory containing cleanup.sh
                Process process = processBuilder.start();
                int exitCode = process.waitFor(); // Wait for the script to finish execution

                if (exitCode != 0) {
                    System.err.println("Error: cleanup.sh script failed with exit code " + exitCode);
                } else {
                    System.out.println("cleanup.sh script executed successfully.");
                }
            } else {
                System.err.println("cleanup.sh script does not exist or is not executable.");
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }

        String baseFolder = "report";
        File folder = new File(baseFolder);
        if (!folder.exists()) {
            folder.mkdirs(); // Create the folder if it does not exist
        }
        String reportPath = new File(props.getProperty("extent.reporter.spark.out")).getAbsolutePath();

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setDocumentTitle("Automation Report");
        sparkReporter.config().setReportName("Automation Report - Author: ");



        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
    }

    public static ExtentTest createTest(String name, String description) {
        ExtentTest test = extent.createTest(name, description);
        testMap.put(Thread.currentThread().getId(), test);
        return test;
    }

    public static ExtentTest getTest() {
        return testMap.get(Thread.currentThread().getId());
    }

    public static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}
