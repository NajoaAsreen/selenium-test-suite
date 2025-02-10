package org.najoa.configs;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.util.HashMap;
import java.util.Map;

public class ExtentManager {
    private static final Map<String, ExtentReports> projectReports = new HashMap<>();
    private static final Map<String, ExtentTest> moduleParents = new HashMap<>();

    // ThreadLocal to manage child nodes for parallel execution
    private static final ThreadLocal<ExtentTest> threadLocalNode = new ThreadLocal<>();

    // Initialize and get ExtentReports instance for a specific project
    public static ExtentReports getReportInstance(String projectName) {
        if (!projectReports.containsKey(projectName)) {
            ExtentReports extent = new ExtentReports();
            ExtentSparkReporter spark = new ExtentSparkReporter("reports/" + projectName +"Report.html");
            // Configure the report appearance
            spark.config().setDocumentTitle(projectName + " Test Report");
            spark.config().setReportName(projectName + " Test Report");
            extent.attachReporter(spark);
            projectReports.put(projectName, extent);
        }
        return projectReports.get(projectName);
    }

    // Get or create a parent test (module) for a specific project
    public static ExtentTest getModuleParent(String projectName, String moduleName) {
        String key = projectName + ":" + moduleName;
        if (!moduleParents.containsKey(key)) {
            ExtentReports report = getReportInstance(projectName);
            ExtentTest parent = report.createTest(moduleName);
            moduleParents.put(key, parent);
        }
        return moduleParents.get(key);
    }

    public static void setTestNode(ExtentTest childNode) {
        threadLocalNode.set(childNode);
    }

    public static ExtentTest getTestNode() {
        return threadLocalNode.get();
    }

    public static void removeTestNode() {
        threadLocalNode.remove();
    }

    // Flush reports for all projects
    public static void flushAllReports() {
        projectReports.values().forEach(ExtentReports::flush);
    }
}