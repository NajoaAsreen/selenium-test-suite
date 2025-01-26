package org.najoa.demoapp2;

import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.najoa.configs.ExtentManager;
import org.najoa.configs.WebDriverSetup;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestExtentReports {
    private static final Logger logger = LogManager.getLogger(TestExtentReports.class);
    private ExtentTest loginParent;

    @BeforeTest
    public void setUp() {
        logger.info("Setting Up TestExtentReports ThreadId: " + Thread.currentThread().getId());

        // Initialize WebDriver for each thread
        WebDriverSetup.initializeDriver();

        // Define project and module
        String projectName = "DemoApp2";
        String moduleName = "Login2";

        // Get parent (module) test
        loginParent = ExtentManager.getModuleParent(projectName, moduleName);
    }

    @Test
    public void testYoutubeHomePage() {
        // WebDriverSetup.initializeDriver();
        // Log the current thread ID to verify parallel execution
        logger.info("Test Youtube HomePage is running on ThreadId: " + Thread.currentThread().getId());

        // Get the WebDriver instance for the current thread
        WebDriver driver = WebDriverSetup.getDriver();

        // Create child node for this test
        ExtentTest childNode = loginParent.createNode("Test Youtube HomePage");
        ExtentManager.setTestNode(childNode);

        driver.get("https://www.youtube.com");
        childNode.info("Navigating to Youtube");

        String pageTitle = driver.getTitle();
        childNode.info("Page Title: " + pageTitle);

        Assert.assertEquals(pageTitle, "YouTube");
    }

    @Test
    public void testOpenAiHomePage() {
        // WebDriverSetup.initializeDriver();
        // Log the current thread ID to verify parallel execution
        logger.info("Test OpenAI HomePage is running on ThreadId: " + Thread.currentThread().getId());

        // Get the WebDriver instance for the current thread
        WebDriver driver = WebDriverSetup.getDriver();

        // Create child node for this test
        ExtentTest childNode = loginParent.createNode("Test OpenAI HomePage");
        ExtentManager.setTestNode(childNode);

        driver.get("https://openai.com/");
        childNode.info("Navigating to OpenAI");

        String pageTitle = driver.getTitle();
        childNode.info("Page Title: " + pageTitle);

        Assert.assertEquals(pageTitle, "OpenAI");
        // throw new SkipException("Skipping this test as it is not needed currently.");
    }

    @AfterMethod
    public void handleTestResult(ITestResult result) {
        // Log test status to the child node (logging only once)
        ExtentTest childNode = ExtentManager.getTestNode();
        if (childNode != null) {
            switch (result.getStatus()) {
                case ITestResult.FAILURE:
                    childNode.fail(result.getThrowable()); // Log exception details
                    break;
                case ITestResult.SUCCESS:
                    childNode.pass("Test Passed: " + result.getName());
                    break;
                case ITestResult.SKIP:
                    childNode.skip("Test Skipped: " + result.getName());
                    break;
            }
        }

        // Clean up ThreadLocal to avoid stale references
        ExtentManager.removeTestNode();
    }

    @AfterTest
    public void tearDown() {
        // Quit WebDriver after all tests are done
        WebDriverSetup.quitDriver();
        ExtentManager.flushAllReports();
    }
}