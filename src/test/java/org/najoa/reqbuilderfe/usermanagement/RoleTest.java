package org.najoa.reqbuilderfe.usermanagement;

import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.najoa.configs.EnvManager;
import org.najoa.configs.ExtentManager;
import org.najoa.configs.WebDriverSetup;
import org.najoa.reqbuilderfe.RequestBuilderFe;
import org.najoa.reqbuilderfe.home.AdminHomePage;
import org.najoa.reqbuilderfe.home.HomePage;
import org.najoa.reqbuilderfe.home.TenantsPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.SkipException;

@Test(groups = {"role"})
public class RoleTest extends RequestBuilderFe {
    private static final Logger logger = LogManager.getLogger(RoleTest.class);
    private ExtentTest roleParent;
    private final String moduleName = "UserManagement";

    @BeforeTest
    public void setUp() {
        logger.info("{}:{} -> @BeforeTest: Setting Up RoleTest on ThreadId: {}", projectName, moduleName, Thread.currentThread().getId());
        WebDriverSetup.initializeDriver();
        roleParent = ExtentManager.getModuleParent(projectName, moduleName);
    }

    @Test(dependsOnGroups = {"login"}, priority = 1)
    public void testDeleteRole() {
        logger.info("{}:{} -> Executing testDeleteRole on ThreadId: {}", projectName, moduleName, Thread.currentThread().getId());

        WebDriver driver = WebDriverSetup.getDriver();
        ExtentTest roleTestNode = roleParent.createNode("Test Role Deletion");
        ExtentManager.setTestNode(roleTestNode);

        AdminHomePage adminHomePage = new AdminHomePage(driver);
        adminHomePage.clickTenants();
        roleTestNode.info("Clicked Tenants");

        TenantsPage tenantsPage = new TenantsPage(driver);
        tenantsPage.clickTenant();
        roleTestNode.info("Clicked Tenant");

        HomePage homePage = new HomePage(driver);
        homePage.clickRole();
        roleTestNode.info("Clicked Role");

        RolePage rolePage = new RolePage(driver);
        boolean isRoleExists = rolePage.isRoleExists();

        if (!isRoleExists) {
            logger.warn("Role does not exists. Skipping role deletion.");
            roleTestNode.warning("Role does not exists. Skipping role deletion.");
            throw new SkipException("Role does not exists for deletion. Skipping test.");
        }

        logger.info("Role found. Proceeding with deletion...");

        rolePage.clickRole();
        roleTestNode.info("Clicked Role to be deleted");
        logger.info("Clicked Role to be deleted");

        rolePage.clickRoleDeleteBtn();
        roleTestNode.info("Clicked role delete button");
        logger.info("Clicked role delete button");

        rolePage.clickRoleConfirmedDeleteBtn();
        roleTestNode.info("Clicked confirm delete button");
        logger.info("Clicked confirm delete button");

        rolePage.wait(500);

        Assert.assertFalse(rolePage.isRoleExists(), "Test Failed: Role not deleted");
        logger.info("Role is deleted");
    }

    @Test(dependsOnGroups = {"login"}, priority = 2)
    public void testCreateRole() {
        logger.info("{}:{} -> Executing testCreateRole on ThreadId: {}", projectName, moduleName, Thread.currentThread().getId());

        WebDriver driver = WebDriverSetup.getDriver();
        ExtentTest roleTestNode = roleParent.createNode("Test Valid Role creation");
        ExtentManager.setTestNode(roleTestNode);

        RolePage rolePage = new RolePage(driver);

        rolePage.clickAddRoleBtn();
        roleTestNode.info("Clicked add Role button");
        logger.info("Clicked add Role button");

        rolePage.enterRoleName(EnvManager.get("PROJECT_REQ_BUILDER_FE_ROLE_NAME"));
        roleTestNode.info("Entered Role name");
        logger.info("Entered Role name");

        rolePage.wait(500);

        rolePage.clickRolePermissionAllCheckbox();
        roleTestNode.info("Clicked Role permission all checkbox");
        logger.info("Clicked Role permission all checkbox");

        rolePage.clickRoleSaveBtn();
        roleTestNode.info("Clicked Role save button");
        logger.info("Clicked Role save button");

        rolePage.wait(500);

        Assert.assertTrue(rolePage.isRoleExists(), "Test Failed: Failed to create role");
        logger.info("New Role is created");
        driver.navigate().back();
    }
}
