package org.najoa.reqbuilderfe.login;

import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.najoa.configs.EnvManager;
import org.najoa.configs.ExtentManager;
import org.najoa.configs.WebDriverSetup;
import org.najoa.reqbuilderfe.RequestBuilderFe;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@Test(groups = {"login"})
public class LoginTest extends RequestBuilderFe {
    private static final Logger logger = LogManager.getLogger(LoginTest.class);
    private ExtentTest loginParent;
    private final String moduleName = "Login";

    @BeforeTest
    public void setUp() {
        logger.info("{}:{} -> @BeforeTest: Setting Up LoginTest on ThreadId: {}", projectName, moduleName, Thread.currentThread().getId());
        WebDriverSetup.initializeDriver();
        loginParent = ExtentManager.getModuleParent(projectName, moduleName);
    }

    @Test
    public void testValidLogin() {
        logger.info("{}:{} -> Executing testValidLogin on ThreadId: {}", projectName, moduleName, Thread.currentThread().getId());

        WebDriver driver = WebDriverSetup.getDriver();
        LoginPage loginPage = new LoginPage(driver);

        ExtentTest loginTestNode = loginParent.createNode("Test Valid Login");
        ExtentManager.setTestNode(loginTestNode);

        // Navigate to Login Page
        String loginUrl = EnvManager.get("PROJECT_REQ_BUILDER_FE_URI");
        driver.get(loginUrl);
        loginTestNode.info("Navigating to: " + loginUrl);
        logger.info("Navigating to: {}", loginUrl);

        // Perform Login
        loginPage.enterUsername(EnvManager.get("PROJECT_REQ_BUILDER_FE_USERNAME"));
        loginTestNode.info("Entered username");

        loginPage.enterPassword(EnvManager.get("PROJECT_REQ_BUILDER_FE_PASSWORD"));
        loginTestNode.info("Entered password");

        loginPage.clickLoginBtn();
        loginTestNode.info("Clicked login button");
        boolean isLoginSuccessful = loginPage.isLoginSuccessful();

        String currentUrl = driver.getCurrentUrl();
        loginTestNode.info("Current URL after login: " + currentUrl);
        logger.info("Current URL after login: {}", currentUrl);

        Assert.assertTrue(isLoginSuccessful, "Login failed or incorrect redirection.");
        loginTestNode.pass("Login successful, redirected to home page");
    }
}

