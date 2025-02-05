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
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Test(groups = {"login"})
public class LoginTest extends RequestBuilderFe {
    private static final Logger logger = LogManager.getLogger(LoginTest.class);
    private ExtentTest loginParent;

    @BeforeTest
    public void setUp() {

        String moduleName = "Login";
        logger.info("{}: Before Method: Setting Up LoginTest ThreadId: {}", projectName, Thread.currentThread().getId());

        // Initialize WebDriver for each thread
        WebDriverSetup.initializeDriver();

        // Get parent (module) test
        loginParent = ExtentManager.getModuleParent(projectName, moduleName);
    }

    @Test(priority = 1)
    public void testValidLogin() {
        logger.info("Executing testValidLogin on ThreadId: {}", Thread.currentThread().getId());

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
        loginTestNode.info("Entered Username");

        loginPage.enterPassword(EnvManager.get("PROJECT_REQ_BUILDER_FE_PASSWORD"));
        loginTestNode.info("Entered Password");

        loginPage.clickLogin();
        loginTestNode.info("Clicked Login Button");

        // Wait for Home Page
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        boolean urlContainsHome = wait.until(ExpectedConditions.urlContains("home"));

        String currentUrl = driver.getCurrentUrl();
        loginTestNode.info("Current URL after login: " + currentUrl);
        logger.info("Current URL after login: {}", currentUrl);

        Assert.assertTrue(urlContainsHome, "Login failed or incorrect redirection.");
        loginTestNode.pass("Login successful, redirected to home page");
    }
}

