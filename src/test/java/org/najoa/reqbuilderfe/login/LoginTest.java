package org.najoa.reqbuilderfe.login;
import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.najoa.configs.EnvManager;
import org.najoa.configs.ExtentManager;
import org.najoa.configs.LocatorConfig;
import org.najoa.configs.WebDriverSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
@Test(groups = {"login"})
public class LoginTest extends RequestBuilderFe {
    private static final Logger logger = LogManager.getLogger(LoginTest.class);
    private ExtentTest loginParent;

    // @BeforeTest
    @BeforeMethod
    public void setUp() {
        // Define project and module
        //projectName = EnvManager.get("PROJECT_REQ_BUILDER_FE_NAME");
        String moduleName = "Login";
        logger.info("{}: Before Method: Setting Up LoginTest ThreadId: {}", projectName, Thread.currentThread().getId());

        // Initialize WebDriver for each thread
        WebDriverSetup.initializeDriver();

        // Get parent (module) test
        loginParent = ExtentManager.getModuleParent(projectName, moduleName);
    }

   @Test(priority = 1)
   // @Test(groups = {"login"}, priority = 1)
    public void testValidLogin() {
        logger.info(projectName+": "+ "Executing testValidLogin on ThreadId: " + Thread.currentThread().getId());

        WebDriver driver = WebDriverSetup.getDriver();
        ExtentTest childNode = loginParent.createNode("Test Valid Login");
        ExtentManager.setTestNode(childNode);

        // Navigate to the login page
        String loginUrl = EnvManager.get("PROJECT_REQ_BUILDER_FE_URI");
        driver.get(loginUrl);
        childNode.info("Navigating to: " + loginUrl);
        logger.info(projectName+": "+ "Navigating to: " + loginUrl);

        // Get locators
        By usernameField = LocatorConfig.getLocator("reqbuilderfe.login.usernameField");
        By passwordField = LocatorConfig.getLocator("reqbuilderfe.login.passwordField");
        By loginBtn = LocatorConfig.getLocator("reqbuilderfe.login.loginBtn");

        // Retrieve credentials
        String userName = EnvManager.get("PROJECT_REQ_BUILDER_FE_USERNAME");
        String passWord = EnvManager.get("PROJECT_REQ_BUILDER_FE_PASSWORD");

        // Perform login
        driver.findElement(usernameField).sendKeys(userName);
        childNode.info("Entered Username: " + userName);
        logger.info("Entered Username: " + userName);
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
        logger.info("Current URL after login: " + currentUrl);

        // Assert that "home" is in the URL
        Assert.assertTrue(urlContainsHome, "Login failed or incorrect redirection.");
        childNode.info("Login successful, redirected to home page");
    }
    //@Test(groups = {"login"}, priority = 2)
    @Test(priority = 2)
    public void testValidLogout() {
        logger.info(projectName+": "+ "Executing testValidLogout on ThreadId: " + Thread.currentThread().getId());

        WebDriver driver = WebDriverSetup.getDriver();
        ExtentTest childNode = loginParent.createNode("Test Valid Logout");
        ExtentManager.setTestNode(childNode);

       //getting locator
        By accountCircle = LocatorConfig.getLocator("reqbuilderfe.login.accCircle");
        By logoutBtn = LocatorConfig.getLocator("reqbuilderfe.login.logoutBtn");

        driver.findElement(accountCircle).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(logoutBtn));
        // Click on the logout button
        logoutButton.click();

        childNode.info("Clicked Logout Button");
        logger.info("Clicked Logout Button");

        // Wait for URL to contain "home" (Ensures login was successful)
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean urlContainsLogout = wait.until(ExpectedConditions.urlContains("auth/login"));

        String currentUrl = driver.getCurrentUrl();
        childNode.info("Current URL after logout: " + currentUrl);
        logger.info("Current URL after logout: " + currentUrl);

        // Assert that "home" is in the URL
        Assert.assertTrue(urlContainsLogout, "Logout failed or incorrect redirection.");
        childNode.info("Logout successful, redirected to login page");
    }
}
