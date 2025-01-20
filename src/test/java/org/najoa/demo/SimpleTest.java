package org.najoa.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.najoa.WebDriverSetup;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SimpleTest {
    private static final Logger logger = LogManager.getLogger(SimpleTest.class);
    private WebDriver driver;

    @BeforeTest
    public void setUp() {
        this.logTest();
        logger.info("Initialize WebDriver");
        // Initialize WebDriver
        driver = WebDriverSetup.initializeDriver();
    }

    @Test
    public void testGoogleHomePage() {
        logger.debug("Navigating to Google...");
        // Open Google homepage
        driver.get("https://www.google.com");

        // Verify the title of the page
        String pageTitle = driver.getTitle();
        logger.debug("Page title is: " + pageTitle);
        Assert.assertEquals(pageTitle, "Google");
    }

    @AfterTest
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }

    private void logTest(){
        logger.trace("Testing logging...");
        logger.debug("Testing logging...");
        logger.info("Testing logging...");
        logger.warn("Testing logging...");
        logger.error("Testing logging...");
    }
}