package org.najoa.configs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverSetup {
    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    // Initialize WebDriver only if not already created for the current thread
    public static WebDriver initializeDriver() {
        if (threadLocalDriver.get() == null) {
            WebDriverManager.chromedriver().setup();
            WebDriver driver = new ChromeDriver();
            threadLocalDriver.set(driver);
        }
        return threadLocalDriver.get();
}

    public static WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    public static void removeDriver() {
        threadLocalDriver.remove();
    }

    // Quit WebDriver for the current thread (can be called from @AfterTest)
    public static void quitDriver() {
        WebDriver driver = threadLocalDriver.get();
        if (driver != null) {
            driver.quit();  // Quit the WebDriver instance
        }
        removeDriver();  // Remove the WebDriver from ThreadLocal
    }
}