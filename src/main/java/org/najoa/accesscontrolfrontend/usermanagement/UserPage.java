package org.najoa.accesscontrolfrontend.usermanagement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.najoa.configs.LocatorConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import java.time.Duration;
import java.util.NoSuchElementException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class UserPage {
    private static final Logger logger = LogManager.getLogger(UserPage.class);
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By addUserBtn = LocatorConfig.getLocator("accesscontrolfrontend.user.addUserBtn");
    private final By userFullNameField = LocatorConfig.getLocator("accesscontrolfrontend.user.userFullNameField");
    private final By userUsernameField = LocatorConfig.getLocator("accesscontrolfrontend.user.usernameField");
    private final By userPasswordField = LocatorConfig.getLocator("accesscontrolfrontend.user.passwordField");
    private final By userEmailField = LocatorConfig.getLocator("accesscontrolfrontend.user.emailField");
    private final By userRoleOptionDropdown = LocatorConfig.getLocator("accesscontrolfrontend.user.roleOptionDropdown");
    private final By userSelectRoleFromDropdown = LocatorConfig.getLocator("accesscontrolfrontend.user.pickRoleFromDropdown", "PROJECT_ACCESS_CONTROL_FE_ROLE_NAME");
    private final By userPhoneNumberField = LocatorConfig.getLocator("accesscontrolfrontend.user.phoneNumberField");
    private final By userSaveBtn = LocatorConfig.getLocator("accesscontrolfrontend.user.saveBtn");
    private final By userDeleteBtn = LocatorConfig.getLocator("accesscontrolfrontend.user.userDeleteBtn");
    private final By userConfirmDeleteBtn = LocatorConfig.getLocator("accesscontrolfrontend.user.userConfirmDeleteBtn");
    private final By username = LocatorConfig.getLocator("accesscontrolfrontend.user.username", "PROJECT_ACCESS_CONTROL_FE_USER_USERNAME");

    public UserPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void clickAddUserBtn() {
        driver.findElement(addUserBtn).click();
    }

    public void enterUserFullName(String userFullName) {
        wait.until(ExpectedConditions.presenceOfElementLocated(userFullNameField));
        driver.findElement(userFullNameField).sendKeys(userFullName);
    }

    public void enterUserUsername(String userUsername) {
        driver.findElement(userUsernameField).sendKeys(userUsername);
    }

    public void enterUserPassword(String userPassword) {
        driver.findElement(userPasswordField).sendKeys(userPassword);
    }

    public void enterUserEmail(String userEmail) {
        driver.findElement(userEmailField).sendKeys(userEmail);
    }

    public void clickUserRoleDropdown() {
        driver.findElement(userRoleOptionDropdown).click();
    }

    public void selectUserRoleFromDropdown() {
        wait.until(ExpectedConditions.presenceOfElementLocated(userSelectRoleFromDropdown));
        driver.findElement(userSelectRoleFromDropdown).click();
    }

    public void enterUserPhoneNumber(String userPhoneNumber) {
        driver.findElement(userPhoneNumberField).sendKeys(userPhoneNumber);
    }

    public void clickUserSaveBtn() {
        driver.findElement(userSaveBtn).click();
    }

    public void clickUser() {
        driver.findElement(username).click();
    }

    public void clickUserDeleteBtn() {
        wait.until(ExpectedConditions.presenceOfElementLocated(userDeleteBtn));
        driver.findElement(userDeleteBtn).click();
    }

    public void clickUserConfirmedDeleteBtn() {
        wait.until(ExpectedConditions.presenceOfElementLocated(userConfirmDeleteBtn));
        driver.findElement(userConfirmDeleteBtn).click();
    }

    public boolean isUserExists() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(username));
            return true;
        } catch (NoSuchElementException e) {
            logger.warn("User '{}' not found in the DOM. NoSuchElementException occurred.", username);
            return false;
        } catch (TimeoutException e) {
            logger.warn("Timeout: User '{}' not found within the specified wait time.", username);
            return false;
        }
    }
}
