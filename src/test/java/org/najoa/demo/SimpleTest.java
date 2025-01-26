package org.najoa.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.najoa.configs.EnvManager;
import org.najoa.configs.LocatorConfig;
import org.najoa.configs.WebDriverSetup;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class SimpleTest {
    private static final Logger logger = LogManager.getLogger(SimpleTest.class);
    private WebDriver driver;
    private ExtentReports extentReports;
    private ExtentTest extentTest;
    private ExtentSparkReporter extentSparkReporter;

    @BeforeTest
    public void setUp() {
        logTest();
        envTest();
        locatorTest();
        // Initialize WebDriver (ChromeDriver in this case)
        driver = WebDriverSetup.initializeDriver();

        // Initialize ExtentReports and ExtentSparkReporter
        extentSparkReporter = new ExtentSparkReporter("reports/extentReport.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);

        // Configure the report appearance
        extentSparkReporter.config().setDocumentTitle("Selenium Test Report");
        extentSparkReporter.config().setReportName("Test Execution Report");
        extentSparkReporter.config().setTheme(Theme.STANDARD);
    }

    @Test
    public void testGoogleHomePage() {
        extentTest = extentReports.createTest("TestGoogleHomePage"); // Start logging test steps

        // Step 1: Navigate to Google
        driver.get("https://www.google.com");
        extentTest.info("Navigating to Google");

        // Step 2: Validate page title
        String pageTitle = driver.getTitle();
        extentTest.info("Page Title: " + pageTitle);

        if (pageTitle != null && pageTitle.equals("Google")) {
            extentTest.pass("Test Passed: Page title matches expected.");
        } else {
            extentTest.fail("Test Failed: Page title does not match.");
        }
    }

    @AfterMethod
    public void getResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            extentTest.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.pass("Test Passed: " + result.getName());
        } else {
            extentTest.skip("Test Skipped: " + result.getName());
        }
    }

    @AfterTest
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
        extentReports.flush(); // Finalize and write the report
    }

    private void logTest(){
        logger.trace("Testing logging...");
        logger.debug("Testing logging...");
        logger.info("Testing logging...");
        logger.warn("Testing logging...");
        logger.error("Testing logging...");
    }

    private void envTest(){
        logger.info("EnvManager.get(\"BASE_URL\"): " + EnvManager.get("BASE_URL"));
        // logger.error("EnvManager.get(\"BASE_URL_\"): " + EnvManager.get("BASE_URL_"));
        logger.warn("EnvManager.get(\"BASE_URL_\", \"default_url\"): " + EnvManager.get("BASE_URL_", "default url"));
    }

    private void locatorTest(){
        logger.info(LocatorConfig.getLocator("login.usernameField"));
        logger.info(LocatorConfig.getLocator("demo_app.login.usernameField"));
        logger.info(LocatorConfig.getLocator("login.passwordField"));
        logger.info(LocatorConfig.getLocator("login.loginButton"));
    }
}