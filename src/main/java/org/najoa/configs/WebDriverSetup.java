package org.najoa.configs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class WebDriverSetup {
    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    // Initialize WebDriver only if not already created for the current thread
    public static WebDriver initializeDriver() {
        if (threadLocalDriver.get() == null) {
            WebDriverManager.chromedriver().setup();
            WebDriver driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(EnvManager.get("IMPLICITLY_WAIT"))));
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
            driver.quit();
        }
        removeDriver();
    }
}
