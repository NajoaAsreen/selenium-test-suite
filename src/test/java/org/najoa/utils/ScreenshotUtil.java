package org.najoa.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {
    private static final Logger logger = LogManager.getLogger(ScreenshotUtil.class);

    public static String getScreenshot(String projectName, String moduleName, String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        // Define directory structure: reports/{projectName}/{moduleName}
        String directoryPath = System.getProperty("user.dir") + File.separator + "reports"
                + File.separator + projectName + File.separator + moduleName;

        logger.info("DirectoryPath: " + directoryPath);

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs(); // Create directories if they don't exist
            if (created) {
                // Log success if directories were created
                logger.info("Directories created successfully: " + directory.getAbsolutePath());
            } else {
                // Log failure if directories couldn't be created
                logger.error("Failed to create directories: " + directory.getAbsolutePath());
            }
        } else {
            // Log that the directory already exists
            logger.info("Directory already exists: " + directory.getAbsolutePath());
        }

        // Define full file path
        String filePath = directoryPath + File.separator + testCaseName + ".png";
        File destination = new File(filePath);

        FileUtils.copyFile(source, destination);
        return filePath;
    }
}
