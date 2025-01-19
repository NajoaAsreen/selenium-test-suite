package org.najoa;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverSetup {

    public static WebDriver initializeDriver() {
        // Use WebDriverManager to set up the ChromeDriver automatically
        WebDriverManager.chromedriver().setup();

        // Create and return the WebDriver instance
        return new ChromeDriver();
    }
}