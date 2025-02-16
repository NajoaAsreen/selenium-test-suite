package org.najoa.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.najoa.configs.ExtentManager;
import org.testng.ISuite;
import org.testng.ISuiteListener;

public class ExtentReportListener implements ISuiteListener {
    private static final Logger logger = LogManager.getLogger(ExtentReportListener.class);

    @Override
    public void onStart(ISuite suite) {
        logger.info("Suite started: {} ThreadId: {}", suite.getName(), Thread.currentThread().getId());
    }

    @Override
    public void onFinish(ISuite suite) {
        logger.info("Suite finished: {} ThreadId: {}", suite.getName(), Thread.currentThread().getId());
        ExtentManager.flushAllReports();
        logger.info("Flushed reports ThreadId: {}", Thread.currentThread().getId());
    }
}