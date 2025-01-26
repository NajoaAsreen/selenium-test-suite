package org.najoa.demo;

import org.najoa.configs.ExtentManager;
import org.najoa.configs.WebDriverSetup;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
//import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;

public class TestExtentReports {
    private WebDriver driver;
    private ExtentTest loginParent;

    @BeforeTest
    public void setUp() {
        // Initialize WebDriver
        driver = WebDriverSetup.initializeDriver();

        // Define project and module
        String projectName = "Demo";
        String moduleName = "Login";

        // Get parent (module) test
        loginParent = ExtentManager.getModuleParent(projectName, moduleName);
    }

    @Test
    public void testGoogleHomePage() {
        // Create child node for this test
        ExtentTest childNode = loginParent.createNode("Test Google HomePage");
        ExtentManager.setTestNode(childNode);

        driver.get("https://www.google.com");
        childNode.info("Navigating to Google");

        String pageTitle = driver.getTitle();
        childNode.info("Page Title: " + pageTitle);

        Assert.assertEquals(pageTitle, "Google");
    }

    @Test
    public void testOpenAiHomePage() {
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
        if (driver != null) {
            driver.quit();
        }
        ExtentManager.flushAllReports();
    }
}