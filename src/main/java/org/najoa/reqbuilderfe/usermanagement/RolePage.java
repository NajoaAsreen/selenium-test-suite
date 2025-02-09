package org.najoa.reqbuilderfe.usermanagement;

import org.najoa.configs.LocatorConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.time.Duration;
import java.util.NoSuchElementException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RolePage {
    private final WebDriver driver;

    // Locators
    private final By roleTenantsBtn = LocatorConfig.getLocator("reqbuilderfe.role.tenants");
    private final By roletechDevBtn = LocatorConfig.getLocator("reqbuilderfe.role.techdev");
    private final By roleOptionBtn = LocatorConfig.getLocator("reqbuilderfe.role.roleBtn");
    private final By addRole = LocatorConfig.getLocator("reqbuilderfe.role.addroleBtn");
    private final By roleNameField = LocatorConfig.getLocator("reqbuilderfe.role.rolenameField");
    private final By rolepermAll = LocatorConfig.getLocator("reqbuilderfe.role.rolepermallField");
    private final By roleSaveBtn = LocatorConfig.getLocator("reqbuilderfe.role.rolesaveBtn");
    private final By roleDeleteBtn = LocatorConfig.getLocator("reqbuilderfe.role.roleDelete");
    private final By roleConfirmDeleteBtn = LocatorConfig.getLocator("reqbuilderfe.role.roleconfirmDelete");
    private final By roleExists = LocatorConfig.getLocator("reqbuilderfe.role.roleexists", "PROJECT_REQ_BUILDER_FE_ROLE_NAME");


    // Constructor
    public RolePage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions

    public void clickTenants() {
        driver.findElement(roleTenantsBtn).click();
    }

    public void clickTechDev() {
        driver.findElement(roletechDevBtn).click();
    }

    public void clickRoleOption() {
        driver.findElement(roleOptionBtn).click();
    }

    public void clickAddRole() {
        driver.findElement(addRole).click();
    }

    public void enterRoleName(String roleName) {
        driver.findElement(roleNameField).sendKeys(roleName);
    }

    public void clickRolePermission() {
        driver.findElement(rolepermAll).click();
    }

    public void clickRoleSave() {
        driver.findElement(roleSaveBtn).click();
    }

    public void clickExistingRole() {
        driver.findElement(roleExists).click();
    }

    public void clickRoleDeleteBtn() {
        driver.findElement(roleDeleteBtn).click();
    }

    public void clickRoleConfirmedDeleteBtn() {
        driver.findElement(roleConfirmDeleteBtn).click();
    }

    public static void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L); // Convert seconds to milliseconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted state
            e.printStackTrace();
        }
    }

    public boolean isRolePresent() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3)); // Adjust timeout if needed
            wait.until(ExpectedConditions.presenceOfElementLocated(roleExists));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        } catch (Exception e) {
            return false; // Catch timeout or other exceptions safely
        }
}
}

