package org.najoa.demoapp;

import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.najoa.configs.EnvManager;
import org.najoa.configs.ExtentManager;
import org.najoa.configs.WebDriverSetup;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;

public class DemoApp {
    private static final Logger logger = LogManager.getLogger(DemoApp.class);
    protected String projectName;
    public DemoApp() {
        projectName =EnvManager.get("PROJECT_DEMO_APP_NAME");
    }

    @AfterMethod
    public void handleTestResult(ITestResult result) {
        // Log test status to the child node (logging only once)
        ExtentTest childNode = ExtentManager.getTestNode();
        if (childNode != null) {
            switch (result.getStatus()) {
                case ITestResult.FAILURE:
                    logger.error("{}: {}: is running on ThreadId: {} - {}", projectName, result.getName(), Thread.currentThread().getId(), result.getThrowable());

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
        logger.info("{}: After Test ThreadId: {}", EnvManager.get("PROJECT_DEMO_APP_NAME"), Thread.currentThread().getId());
        WebDriverSetup.quitDriver();
        ExtentManager.flushAllReports();
    }
}
