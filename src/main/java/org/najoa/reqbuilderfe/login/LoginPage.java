package org.najoa.reqbuilderfe.login;

import org.najoa.configs.LocatorConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;
    private final By usernameField = LocatorConfig.getLocator("reqbuilderfe.login.usernameField");
    private final By passwordField = LocatorConfig.getLocator("reqbuilderfe.login.passwordField");
    private final By loginBtn = LocatorConfig.getLocator("reqbuilderfe.login.loginBtn");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginBtn).click();
    }

    public boolean isLoginSuccessful(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.urlContains("home"));
    }
}
