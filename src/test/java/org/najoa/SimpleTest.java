package org.najoa;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SimpleTest {

    private WebDriver driver;

    @BeforeTest
    public void setUp() {
        // Initialize WebDriver
        driver = WebDriverSetup.initializeDriver();
    }

    @Test
    public void testGoogleHomePage() {
        // Open Google homepage
        driver.get("https://www.google.com");

        // Verify the title of the page
        String pageTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, "Google");
    }


    @Test
    public void testGeminiHomePage() {
        // Open the Gemini homepage
        driver.get("https://gemini.google.com/");
        String title = driver.getTitle();
        if(title == null) title = "";
        // Get the title of the page
        String pageTitle = title.trim().replace("\u200E", "").replace("\u200F", "");

        // Verify the title of the page
        Assert.assertEquals(pageTitle, "Gemini - chat to supercharge your ideas",
                "Page title does not match the expected value.");
    }

    @AfterTest
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}