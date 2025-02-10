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
    private final By avatarCircle = LocatorConfig.getLocator("reqbuilderfe.login.profileAvatar");
    private final By logoutBtn = LocatorConfig.getLocator("reqbuilderfe.login.logoutBtn");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    public void clickAvatar() {
        driver.findElement(avatarCircle).click();
    }

    public void clickLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutBtn)).click();
    }

    public Boolean isLogoutSuccessful() {
        return wait.until(ExpectedConditions.urlContains("auth/login"));
    }
}