package org.najoa.accesscontrolfrontend.usermanagement;

import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.najoa.configs.EnvManager;
import org.najoa.configs.ExtentManager;
import org.najoa.configs.WebDriverSetup;
import org.najoa.accesscontrolfrontend.AccessControlFrontend;
import org.najoa.accesscontrolfrontend.home.AdminHomePage;
import org.najoa.accesscontrolfrontend.home.HomePage;
import org.najoa.accesscontrolfrontend.home.TenantsPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.SkipException;

@Test(groups = {"user"})
public class UserTest extends AccessControlFrontend {
    private static final Logger logger = LogManager.getLogger(UserTest.class);
    private ExtentTest userParent;
    private final String moduleName = "UserManagement";

    @BeforeTest
    public void setUp() {
        logger.info("{}:{} -> @BeforeTest: Setting Up UserTest on ThreadId: {}", projectName, moduleName, Thread.currentThread().getId());
        WebDriverSetup.initializeDriver();
        ExtentManager.setProjectName(projectName);

        userParent = ExtentManager.getModuleParent(projectName, moduleName);
    }

    @BeforeMethod
    public void setModuleName(){
        ExtentManager.setModuleName(moduleName);
    }

    @Test(dependsOnGroups = {"login"})
    public void testDeleteUser() {
        logger.info("{}:{} -> Executing testDeleteUser on ThreadId: {}", projectName, moduleName, Thread.currentThread().getId());

        WebDriver driver = WebDriverSetup.getDriver();
        ExtentTest userTestNode = userParent.createNode("Test User Deletion");
        ExtentManager.setTestNode(userTestNode);

        AdminHomePage adminHomePage = new AdminHomePage(driver);
        adminHomePage.clickTenants();
        userTestNode.info("Clicked Tenants");

        TenantsPage tenantsPage = new TenantsPage(driver);
        tenantsPage.clickTenant();
        userTestNode.info("Clicked Tenant");

        HomePage homePage = new HomePage(driver);
        homePage.clickUser();
        userTestNode.info("Clicked User");

        UserPage userPage = new UserPage(driver);
        boolean isUserExists = userPage.isUserExists();

        if (!isUserExists) {
            userTestNode.info("Navigating back");
            logger.info("Navigating back");
            driver.navigate().back();
            logger.warn("User does not exists. Skipping user deletion.");
            userTestNode.warning("User does not exists. Skipping user deletion.");
            throw new SkipException("User does not exists for deletion. Skipping test.");
        }

        logger.info("User found. Proceeding with deletion...");

        userPage.clickUser();
        userTestNode.info("Clicked User to be deleted");
        logger.info("Clicked User to be deleted");

        userPage.clickUserDeleteBtn();
        userTestNode.info("Clicked user delete button");
        logger.info("Clicked user delete button");

        userPage.clickUserConfirmedDeleteBtn();
        userTestNode.info("Clicked confirm delete button for user");
        logger.info("Clicked confirm delete button for user");

        wait(500);

        Assert.assertFalse(userPage.isUserExists(), "Test Failed: User not deleted");
        logger.info("User is deleted");
        userTestNode.info("Navigating back");
        logger.info("Navigating back");
        driver.navigate().back();
    }

    @Test(dependsOnMethods = "org.najoa.accesscontrolfrontend.usermanagement.RoleTest.testCreateRole")
    public void testCreateUser() {
        logger.info("{}:{} -> Executing testCreateUser on ThreadId: {}", projectName, moduleName, Thread.currentThread().getId());

        WebDriver driver = WebDriverSetup.getDriver();
        ExtentTest userTestNode = userParent.createNode("Test User Creation");
        ExtentManager.setTestNode(userTestNode);

        HomePage homePage = new HomePage(driver);

        wait(100);

        homePage.clickUser();
        userTestNode.info("Clicked User");

        UserPage userPage = new UserPage(driver);

        userPage.clickAddUserBtn();
        userTestNode.info("Clicked add User button");
        logger.info("Clicked add User button");

        userPage.enterUserFullName(EnvManager.get("PROJECT_ACCESS_CONTROL_FE_USER_FULLNAME"));
        userTestNode.info("Entered User fullname");
        logger.info("Entered User fullname");

        userPage.enterUserUsername(EnvManager.get("PROJECT_ACCESS_CONTROL_FE_USER_USERNAME"));
        userTestNode.info("Entered User username");
        logger.info("Entered User username");

        userPage.enterUserPassword(EnvManager.get("PROJECT_ACCESS_CONTROL_FE_USER_PASSWORD"));
        userTestNode.info("Entered User password");
        logger.info("Entered User password");

        userPage.enterUserEmail(EnvManager.get("PROJECT_ACCESS_CONTROL_FE_USER_EMAIL"));
        userTestNode.info("Entered User email");
        logger.info("Entered User email");

        userPage.clickUserRoleDropdown();
        userTestNode.info("Clicked Role dropdown button for User");
        logger.info("Clicked Role dropdown button for User");

        wait(500);

        userPage.selectUserRoleFromDropdown();
        userTestNode.info("Selected Role from dropdown button for User");
        logger.info("Selected Role from dropdown button for User");

        userPage.enterUserPhoneNumber(EnvManager.get("PROJECT_ACCESS_CONTROL_FE_USER_PHONE_NUMBER"));
        userTestNode.info("Entered User phone number");
        logger.info("Entered User phone number");

        userPage.clickUserSaveBtn();
        userTestNode.info("Clicked User save button");
        logger.info("Clicked User save button");

        wait(500);

        Assert.assertTrue(userPage.isUserExists(), "Test Failed: Failed to create user");
        logger.info("New User is created");
    }
}

