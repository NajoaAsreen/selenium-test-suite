package org.najoa.reqbuilderfe.home;

import org.najoa.configs.LocatorConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class TenantsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By tenantToBeSelected = LocatorConfig.getLocator("reqbuilderfe.home.tenantToBeSelected");

    public TenantsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    public void clickTenant() {
        wait.until(ExpectedConditions.urlContains("admin/tenants"));
        driver.findElement(tenantToBeSelected).click();
    }
}
