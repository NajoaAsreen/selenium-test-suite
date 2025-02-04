package org.najoa.demoapp2.module1;

import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.najoa.configs.ExtentManager;
import org.najoa.configs.WebDriverSetup;
import org.najoa.demoapp2.DemoApp2;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestExtentReports extends DemoApp2 {
    private static final Logger logger = LogManager.getLogger(TestExtentReports.class);
    private ExtentTest loginParent;
    //private String projectName;
    @BeforeTest
    public void setUp() {
       // projectName = EnvManager.get("PROJECT_DEMO_APP2_NAME");
        logger.info(projectName+": "+"Before Test: Setting Up TestExtentReports ThreadId: " + Thread.currentThread().getId());

        // Initialize WebDriver for each thread
        WebDriverSetup.initializeDriver();

        // Define project and module
        String projectName = "DemoApp2";
        String moduleName = "Module 1";

        // Get parent (module) test
        loginParent = ExtentManager.getModuleParent(projectName, moduleName);
    }

    @Test
    public void testYoutubeHomePage() {
        // WebDriverSetup.initializeDriver();
        // Log the current thread ID to verify parallel execution
        logger.info(projectName+": "+"Test Youtube HomePage is running on ThreadId: " + Thread.currentThread().getId());

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
        logger.info(projectName+": "+"Test OpenAI HomePage is running on ThreadId: " + Thread.currentThread().getId());

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
}