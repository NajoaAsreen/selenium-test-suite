package org.najoa.accesscontrolfrontend.home;

import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.najoa.configs.ExtentManager;
import org.najoa.configs.WebDriverSetup;
import org.najoa.accesscontrolfrontend.AccessControlFrontend;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@Test(groups = {"home"})
public class HomeTest extends AccessControlFrontend {
    private static final Logger logger = LogManager.getLogger(HomeTest.class);
    private ExtentTest homeParent;
    private final String moduleName = "Home";

    @BeforeTest
    public void setUp() {
        logger.info("{}:{} -> @BeforeTest: Setting Up HomeTest on ThreadId: {}", projectName, moduleName, Thread.currentThread().getId());
        WebDriverSetup.initializeDriver();
        homeParent = ExtentManager.getModuleParent(projectName, moduleName);
    }

    @Test(dependsOnMethods = "org.najoa.accesscontrolfrontend.usermanagement.UserTest.testCreateUser")
    public void testValidLogout() {
        logger.info("{}:{} -> Executing testValidLogout on ThreadId: {}", projectName, moduleName, Thread.currentThread().getId());
        WebDriver driver = WebDriverSetup.getDriver();
        HomePage homePage = new HomePage(driver);
        ExtentTest logoutTestNode = homeParent.createNode("Test Logout");
        ExtentManager.setTestNode(logoutTestNode);

        // Performing logout
        homePage.clickAvatar();
        logoutTestNode.info("Clicked profile avatar");
        homePage.clickLogout();
        logoutTestNode.info("Clicked logout button");
        logger.info("Clicked logout button");
        boolean isLogoutSuccessful = homePage.isLogoutSuccessful();

        String currentUrl = driver.getCurrentUrl();
        logoutTestNode.info("Current URL after logout: " + currentUrl);
        logger.info("Current URL after logout: {}", currentUrl);

        Assert.assertTrue(isLogoutSuccessful, "Logout failed or incorrect redirection.");
        logoutTestNode.info("Logout successful, redirected to login page");
    }
}
