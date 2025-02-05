package org.najoa.reqbuilderfe.home;
import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.najoa.configs.ExtentManager;
import org.najoa.configs.WebDriverSetup;
import org.najoa.reqbuilderfe.RequestBuilderFe;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.time.Duration;

@Test(groups = {"home"})
public class HomeTest extends RequestBuilderFe {
    private static final Logger logger = LogManager.getLogger(HomeTest.class); // Fixed incorrect reference
    private ExtentTest homeParent;
    private WebDriver driver; // Added WebDriver instance at class level

    @BeforeTest
    public void setUp() {
        // Define project and module
        String moduleName = "Home";
        logger.info("{}: Before Test: Setting Up HomeTest ThreadId: {}", projectName, Thread.currentThread().getId());

        // Initialize WebDriver for each thread
        driver = WebDriverSetup.initializeDriver(); // Assign the initialized WebDriver to class-level variable

        // Get parent (module) test
        homeParent = ExtentManager.getModuleParent(projectName, moduleName);
    }

    @Test(dependsOnGroups = {"login"}) // Added missing @Test annotation
    public void testValidLogout() {
        logger.info("Executing testValidLogout on ThreadId: {}", Thread.currentThread().getId());

        // Ensure driver is initialized
        driver = WebDriverSetup.getDriver();

        HomePage homePage = new HomePage(driver);
        ExtentTest logoutTestNode = homeParent.createNode("Test Valid Logout");
        ExtentManager.setTestNode(logoutTestNode);

        // Performing logout
        homePage.clickAvatar();
        logoutTestNode.info("Clicked Avatar Button");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        homePage.clickLogout();
        logoutTestNode.info("Clicked Logout Button");
        logger.info("Clicked Logout Button");

        boolean urlContainsLogout = wait.until(ExpectedConditions.urlContains("auth/login"));
        String currentUrl = driver.getCurrentUrl();
        logoutTestNode.info("Current URL after logout: " + currentUrl);
        logger.info("Current URL after logout: {}", currentUrl);

        // Assert that "auth/login" is in the URL
        Assert.assertTrue(urlContainsLogout, "Logout failed or incorrect redirection.");
        logoutTestNode.info("Logout successful, redirected to login page");
    }
}
