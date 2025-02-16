package org.najoa.accesscontrolfrontend;
import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.najoa.configs.EnvManager;
import org.najoa.configs.ExtentManager;
import org.najoa.configs.WebDriverSetup;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;

public class AccessControlFrontend {
    private static final Logger logger = LogManager.getLogger(AccessControlFrontend.class);
    protected String projectName;

    public AccessControlFrontend(){
        projectName = EnvManager.get("PROJECT_ACCESS_CONTROL_FE_NAME");
    }

    public void wait(int millisecond) {
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {
            logger.error("Thread was interrupted while waiting for {} seconds", millisecond, e);
            Thread.currentThread().interrupt(); // Restore interrupted state
        }
    }

    @AfterMethod
    public void handleTestResult(ITestResult result) {
        // Log test status to the child node (logging only once)
        ExtentTest childNode = ExtentManager.getTestNode();
        if (childNode != null) {
            switch (result.getStatus()) {
                case ITestResult.FAILURE:
                    logger.error(projectName + ": " + result.getName() + ": is running on ThreadId: " + Thread.currentThread().getId() + " - " + result.getThrowable());
                    childNode.fail(result.getThrowable());
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
        logger.info("{}: After Test ThreadId: {}", EnvManager.get("PROJECT_ACCESS_CONTROL_FE_NAME"), Thread.currentThread().getId());
        WebDriverSetup.quitDriver();
    }

}
