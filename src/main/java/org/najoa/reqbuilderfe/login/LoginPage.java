package org.najoa.reqbuilderfe.login;

import org.najoa.configs.LocatorConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private final WebDriver driver;

    // Locators
    private final By usernameField = LocatorConfig.getLocator("reqbuilderfe.login.usernameField");
    private final By passwordField = LocatorConfig.getLocator("reqbuilderfe.login.passwordField");
    private final By loginBtn = LocatorConfig.getLocator("reqbuilderfe.login.loginBtn");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions
    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginBtn).click();
    }
}
