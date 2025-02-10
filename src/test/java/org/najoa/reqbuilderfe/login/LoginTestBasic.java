package org.najoa.reqbuilderfe.login;

import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.najoa.configs.EnvManager;
import org.najoa.configs.ExtentManager;
import org.najoa.configs.LocatorConfig;
import org.najoa.configs.WebDriverSetup;
import org.najoa.reqbuilderfe.RequestBuilderFe;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class LoginTestBasic extends RequestBuilderFe {
    private static final Logger logger = LogManager.getLogger(LoginTestBasic.class);
    private ExtentTest loginParent;

    @BeforeTest(enabled = false)
    public void setUp() {
        // Define project and module
        //  projectName = EnvManager.get("PROJECT_REQ_BUILDER_FE_NAME");
        String moduleName = "LoginBasic";
        logger.info("{}: Before Method: Setting Up LoginTestBasic ThreadId: {}", projectName, Thread.currentThread().getId());

        // Initialize WebDriver for each thread
        WebDriverSetup.initializeDriver();

        // Get parent (module) test
        loginParent = ExtentManager.getModuleParent(projectName, moduleName);
    }

    @Test(dependsOnGroups = {"login"}, enabled = false)
    public void testValidLogin() {
        logger.info(projectName + ": " + "Executing testValidLogin on ThreadId: " + Thread.currentThread().getId());

        WebDriver driver = WebDriverSetup.getDriver();
        ExtentTest childNode = loginParent.createNode("Test Valid Login");
        ExtentManager.setTestNode(childNode);

        // Navigate to the login page
        String loginUrl = EnvManager.get("PROJECT_REQ_BUILDER_FE_URI");
        driver.get(loginUrl);
        childNode.info("Navigating to: " + loginUrl);
        logger.info("Navigating to: " + loginUrl);

        // Retrieve credentials
        String userName = EnvManager.get("PROJECT_REQ_BUILDER_FE_USERNAME");
        String passWord = EnvManager.get("PROJECT_REQ_BUILDER_FE_PASSWORD");

        // Get locators
        By usernameField = LocatorConfig.getLocator("reqbuilderfe.login.usernameField");

        By passwordField = LocatorConfig.getLocator("reqbuilderfe.login.passwordField");
        By loginBtn = LocatorConfig.getLocator("reqbuilderfe.login.loginBtn");

        // Perform login
        driver.findElement(usernameField).sendKeys(userName);
        childNode.info("Entered Username: " + userName);
        logger.info("Entered Username: {}", userName);
        driver.findElement(passwordField).sendKeys(passWord);
        childNode.info("Entered Password");
        logger.info("Entered Password");
        driver.findElement(loginBtn).click();
        childNode.info("Clicked Login Button");
        logger.info("Clicked Login Button");

        // Wait for URL to contain "home" (Ensures login was successful)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        boolean urlContainsHome = wait.until(ExpectedConditions.urlContains("home"));

        String currentUrl = driver.getCurrentUrl();
        childNode.info("Current URL after login: " + currentUrl);
        logger.info("Current URL after login: {}", currentUrl);

        // Assert that "home" is in the URL
        Assert.assertTrue(urlContainsHome, "Login failed or incorrect redirection.");
        childNode.info("Login successful, redirected to home page");
    }
}
