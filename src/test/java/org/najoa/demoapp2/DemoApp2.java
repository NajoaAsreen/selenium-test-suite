package org.najoa.demoapp2;

import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.najoa.configs.EnvManager;
import org.najoa.configs.ExtentManager;
import org.najoa.configs.WebDriverSetup;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;

public class DemoApp2 {
    private static final Logger logger = LogManager.getLogger(DemoApp2.class);
    protected String projectName;
    public DemoApp2(){
        projectName = EnvManager.get("PROJECT_DEMO_APP2_NAME");
    }
    @AfterMethod
    public void handleTestResult(ITestResult result) {
        // Log test status to the child node (logging only once)
        ExtentTest childNode = ExtentManager.getTestNode();
        if (childNode != null) {
            switch (result.getStatus()) {
                case ITestResult.FAILURE:
                    logger.error(projectName + ": " + result.getName() + ": is running on ThreadId: " + Thread.currentThread().getId() + " - " + result.getThrowable());

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
        logger.info(EnvManager.get("PROJECT_DEMO_APP2_NAME")+": "+"After Test ThreadId: " + Thread.currentThread().getId());

        WebDriverSetup.quitDriver();
        ExtentManager.flushAllReports();
    }
}
