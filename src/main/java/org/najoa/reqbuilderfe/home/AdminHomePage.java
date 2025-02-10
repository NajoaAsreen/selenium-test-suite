package org.najoa.reqbuilderfe.home;

import org.najoa.configs.LocatorConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminHomePage {
    private final WebDriver driver;
    // private final WebDriverWait wait;
    private final By tenantsCard = LocatorConfig.getLocator("reqbuilderfe.home.tenantsCard");

    public AdminHomePage(WebDriver driver) {
        this.driver = driver;
        // this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    public void clickTenants() {
        driver.findElement(tenantsCard).click();
    }
}
