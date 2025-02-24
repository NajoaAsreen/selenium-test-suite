package org.najoa.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.najoa.configs.ExtentManager;
import org.najoa.configs.WebDriverSetup;
import org.najoa.utils.ScreenshotUtil;
import org.testng.ITestListener;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import java.io.IOException;

import com.aventstack.extentreports.ExtentTest;

public class ScreenshotListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger(ScreenshotListener.class);

    @Override
    public void onTestFailure(ITestResult result) {
        String projectName = ExtentManager.getProjectName();
        String moduleName = ExtentManager.getModuleName();

        WebDriver driver = null;
        try {
            // Retrieve WebDriver instance from ThreadLocal
            driver = WebDriverSetup.getDriver();
        } catch (Exception e) {
            logger.error("{}:{} -> Failed to retrieve WebDriver instance for test: {} on ThreadId: {}", projectName, moduleName, result.getMethod().getMethodName(), Thread.currentThread().getId(), e);
        }

        if (driver != null) {
            try {
                // Take Screenshot
                String filePath = ScreenshotUtil.getScreenshot(projectName, moduleName, result.getMethod().getMethodName(), driver);
                logger.info("{}:{} -> Screenshot taken for test: {}. Saved at: {}", projectName, moduleName, result.getMethod().getMethodName(), filePath);

                // Add screenshot to report (but without re-logging the failure here)
                ExtentTest testNode = ExtentManager.getTestNode();
                if (testNode != null) {
                    // testNode.addScreenCaptureFromPath(filePath.replace(System.getProperty("user.dir") + "/reports/", ""), result.getMethod().getMethodName());
                    testNode.addScreenCaptureFromPath(projectName + "/" + moduleName + "/" + result.getMethod().getMethodName() + ".png", result.getMethod().getMethodName());
                }
            } catch (IOException e) {
                logger.error("{}:{} -> Error capturing screenshot for test: {} on ThreadId: {}", projectName, moduleName, result.getMethod().getMethodName(), Thread.currentThread().getId(), e);
            }
        } else {
            logger.warn("{}:{} -> WebDriver instance is null. Screenshot cannot be captured for test: {} on ThreadId: {}", projectName, moduleName, result.getMethod().getMethodName(), Thread.currentThread().getId());
        }
    }
}
