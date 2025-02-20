package org.najoa.accesscontrolfrontend.usermanagement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.najoa.configs.EnvManager;
import org.najoa.configs.LocatorConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import java.time.Duration;
import java.util.NoSuchElementException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RolePage {
    private static final Logger logger = LogManager.getLogger(RolePage.class);
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By addRoleBtn = LocatorConfig.getLocator("accesscontrolfrontend.role.addRoleBtn");
    private final By roleNameField = LocatorConfig.getLocator("accesscontrolfrontend.role.roleNameField");
    private final By rolePermissionAllCheckbox = LocatorConfig.getLocator("accesscontrolfrontend.role.rolePermissionAllCheckbox");
    private final By roleSaveBtn = LocatorConfig.getLocator("accesscontrolfrontend.role.roleSaveBtn");
    private final By roleDeleteBtn = LocatorConfig.getLocator("accesscontrolfrontend.role.roleDeleteBtn");
    private final By roleConfirmDeleteBtn = LocatorConfig.getLocator("accesscontrolfrontend.role.roleConfirmDeleteBtn");
    private final By roleName = LocatorConfig.getLocator("accesscontrolfrontend.role.roleName", "PROJECT_ACCESS_CONTROL_FE_ROLE_NAME");

    public RolePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void clickAddRoleBtn() {
        driver.findElement(addRoleBtn).click();
    }

    public void enterRoleName(String roleName) {
        wait.until(ExpectedConditions.presenceOfElementLocated(roleNameField));
        driver.findElement(roleNameField).sendKeys(roleName);
    }

    public void clickRolePermissionAllCheckbox() {
        driver.findElement(rolePermissionAllCheckbox).click();
    }

    public void clickRoleSaveBtn() {
        driver.findElement(roleSaveBtn).click();
    }

    public void clickRole() {
        driver.findElement(roleName).click();
    }

    public void clickRoleDeleteBtn() {
        wait.until(ExpectedConditions.presenceOfElementLocated(roleDeleteBtn));
        driver.findElement(roleDeleteBtn).click();
    }

    public void clickRoleConfirmedDeleteBtn() {
        wait.until(ExpectedConditions.presenceOfElementLocated(roleConfirmDeleteBtn));
        driver.findElement(roleConfirmDeleteBtn).click();
    }

    public boolean isRoleExists() {
        return !driver.findElements(roleName).isEmpty();
        /*
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2)); // Disable implicit wait temporarily
        boolean exists = !driver.findElements(roleName).isEmpty();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(EnvManager.get("IMPLICITLY_WAIT")))); // Restore implicit wait
        return exists;
        */
    }

    public boolean isRoleExistsOld() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(roleName));
            return true;
        } catch (NoSuchElementException e) {
            logger.warn("Role '{}' not found in the DOM. NoSuchElementException occurred.", roleName);
            return false;
        } catch (TimeoutException e) {
            logger.warn("Timeout: Role '{}' not found within the specified wait time.", roleName);
            return false;
        }
    }
}

