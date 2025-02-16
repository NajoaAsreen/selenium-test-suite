package org.najoa.accesscontrolfrontend.home;

import org.najoa.configs.LocatorConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By avatarCircle = LocatorConfig.getLocator("accesscontrolfrontend.home.profileAvatar");
    private final By logoutBtn = LocatorConfig.getLocator("accesscontrolfrontend.home.logoutBtn");
    private final By roleCard = LocatorConfig.getLocator("accesscontrolfrontend.home.roleCard");
    private final By userCard = LocatorConfig.getLocator("accesscontrolfrontend.home.userCard");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
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

    public void clickRole() {
        wait.until(ExpectedConditions.urlContains("/home"));
        driver.findElement(roleCard).click();
    }

    public void clickUser() {
        wait.until(ExpectedConditions.urlContains("/home"));
        wait.until(ExpectedConditions.elementToBeClickable(userCard));
        driver.findElement(userCard).click();
    }
}