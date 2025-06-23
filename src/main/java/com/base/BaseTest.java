package com.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.ultilities.extentreports.ExtentManager;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

public class BaseTest extends BaseSetup {

    protected static ExtentReports extent;
    protected static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @BeforeSuite
    public void initExtentReports() {
        extent = ExtentManager.getExtentReports(); // Đã chứa timestamp
    }

    @BeforeMethod
    public void startTest(Method method) {
        ExtentTest extentTest = extent.createTest(method.getName());
        test.set(extentTest);
    }

    @AfterMethod
    public void endTest(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.get().fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.get().pass("Test Passed");
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.get().skip("Test Skipped");
        }
    }

    @AfterSuite
    public void tearDownReport() {
        extent.flush();
    }
}

