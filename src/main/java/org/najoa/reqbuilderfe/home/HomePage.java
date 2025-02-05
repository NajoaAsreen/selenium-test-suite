package org.najoa.reqbuilderfe.home;

import org.najoa.configs.LocatorConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    // Locators
    private final By avatarCircle = LocatorConfig.getLocator("reqbuilderfe.login.accCircle");
    private final By logoutBtn = LocatorConfig.getLocator("reqbuilderfe.login.logoutBtn");

    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Actions
    public void clickAvatar() {
        driver.findElement(avatarCircle).click();
    }

    public void clickLogout() {
        // ✅ Wait until the logout button is clickable before clicking
        wait.until(ExpectedConditions.elementToBeClickable(logoutBtn)).click();
        //driver.findElement(logoutBtn).click();
    }
}
