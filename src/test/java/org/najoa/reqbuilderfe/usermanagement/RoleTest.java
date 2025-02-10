package org.najoa.reqbuilderfe.usermanagement;

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
import org.testng.SkipException;


@Test(groups = {"role"})
public class RoleTest extends RequestBuilderFe {
    private static final Logger logger = LogManager.getLogger(RoleTest.class);
    private ExtentTest roleParent;
    private final String moduleName = "UserManagement";

    @BeforeTest(enabled = false)
    public void setUp() {
        logger.info("{}:{} -> @BeforeTest: Setting Up RoleTest on ThreadId: {}", projectName, moduleName, Thread.currentThread().getId());
        WebDriverSetup.initializeDriver();
        roleParent = ExtentManager.getModuleParent(projectName, moduleName);
    }

    @Test(dependsOnGroups = {"login"}, priority = 1, enabled = false)
    public void testDeleteRole() {
        logger.info("{}:{} -> Executing testDeleteRole on ThreadId: {}", projectName, moduleName, Thread.currentThread().getId());

        WebDriver driver = WebDriverSetup.getDriver();
        RolePage rolePage = new RolePage(driver);

        ExtentTest roleTestNode = roleParent.createNode("Test Role Deletion");
        ExtentManager.setTestNode(roleTestNode); // ???

        // Navigate to Role Page
        rolePage.clickTenants();
        roleTestNode.info("Clicked Tenants");
        RolePage.waitForSeconds(5);

        rolePage.clickTechDev();
        roleTestNode.info("Clicked TechDev");
        RolePage.waitForSeconds(5);

        rolePage.clickRoleOption();
        roleTestNode.info("Clicked Role Option from User Management");
        RolePage.waitForSeconds(5);

        if (!rolePage.isRolePresent()) {
            logger.warn("Role does not exists. Skipping role deletion.");
            roleTestNode.warning("Role does not exists. Skipping role deletion.");
            throw new SkipException("Role does not exists for deletion. Skipping test.");
        }

        // If role  exist, proceed with deletion
        logger.info("Role found. Proceeding with deletion...");

        //clicked the role to be deleted
        rolePage.clickExistingRole();
        roleTestNode.info("Clicked Role to be deleted");
        RolePage.waitForSeconds(3);

        rolePage.clickRoleDeleteBtn();
        roleTestNode.info("Clicked Delete button");
        RolePage.waitForSeconds(3);

        rolePage.clickRoleConfirmedDeleteBtn();
        roleTestNode.info("Clicked Confirm Delete button");
        RolePage.waitForSeconds(3);
        Assert.assertFalse(rolePage.isRolePresent(), "Test Failed: 'Role' not deleted");
        logger.info("Role is deleted");

    }

    @Test(dependsOnGroups = {"login"}, priority = 2, enabled = false)
    public void testCreateRole() {
        logger.info("Executing testCreateRole on ThreadId: {}", Thread.currentThread().getId());

        WebDriver driver = WebDriverSetup.getDriver();
        RolePage rolePage = new RolePage(driver);

        ExtentTest roleTestNode = roleParent.createNode("Test Valid Role creation");
        ExtentManager.setTestNode(roleTestNode);

        //click add for role creation
        rolePage.clickAddRole();
        roleTestNode.info("Clicked Add Role");
        RolePage.waitForSeconds(5);

        rolePage.enterRoleName(EnvManager.get("PROJECT_REQ_BUILDER_FE_ROLE_NAME"));
        roleTestNode.info("Entered Role Name");

        rolePage.clickRolePermission();
        roleTestNode.info("Clicked Role All Permissions");
        RolePage.waitForSeconds(5);

        rolePage.clickRoleSave();
        roleTestNode.info("Clicked Save Button");
        RolePage.waitForSeconds(3);
        Assert.assertTrue(rolePage.isRolePresent(), "Test Failed: 'Failed to create role");
        logger.info("New Role is created");
        driver.navigate().back();
        RolePage.waitForSeconds(3);
    }
}
