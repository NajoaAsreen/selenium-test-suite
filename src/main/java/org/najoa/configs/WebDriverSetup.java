package org.najoa.configs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverSetup {
    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    public static WebDriver initializeDriver() {
        // Use WebDriverManager to set up the ChromeDriver automatically
        WebDriverManager.chromedriver().setup();

        // Create a new WebDriver instance for each thread
        WebDriver driver = new ChromeDriver();
        threadLocalDriver.set(driver);

        return driver;
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