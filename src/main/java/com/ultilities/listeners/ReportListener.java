package com.ultilities.listeners;

import com.aventstack.extentreports.Status;
import com.base.BaseSetup;
import com.helpers.CaptureHelpers;
import com.helpers.RecordVideo;
import com.ultilities.extentreports.ExtentTestManager;
import com.ultilities.logs.LogUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static com.ultilities.extentreports.ExtentManager.getExtentReports;


public class ReportListener implements ITestListener {

    public String getTestName(ITestResult result) {
        return result.getTestName() != null ? result.getTestName()
                : result.getMethod().getConstructorOrMethod().getName();
    }

    public String getTestDescription(ITestResult result) {
        return result.getMethod().getDescription() != null ? result.getMethod().getDescription()
                : getTestName(result);
    }

    @Override
    public void onStart(ITestContext context) {
        LogUtils.info("Start testing " + context.getName());

        try {
            RecordVideo.startRecord(context.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        LogUtils.info("End testing " + iTestContext.getName());
        //Kết thúc và thực thi Extents Report
        getExtentReports().flush();
        //Gọi hàm stopRecord video trong CaptureHelpers class
        try {
            RecordVideo.stopRecord();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        String className = result.getTestClass().getRealClass().getSimpleName();
        // Tạo test report đúng chỗ có ITestResult
        ExtentTestManager.saveToReport(className, result.getName(), result.getTestName());
        LogUtils.info(result.getName() + " test is starting...");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        LogUtils.info(getTestName(iTestResult) + " test is passed.");
        //ExtentReports log operation for passed tests.
        ExtentTestManager.logMessage(Status.PASS, getTestDescription(iTestResult));
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        LogUtils.error(getTestName(iTestResult) + " test is failed.");

        ExtentTestManager.addScreenShot(Status.FAIL, getTestName(iTestResult));

        ExtentTestManager.logMessage(Status.FAIL, iTestResult.getThrowable().toString());
        ExtentTestManager.logMessage(Status.FAIL, iTestResult.getName() + " is failed.");
        try {
            CaptureHelpers.captureScreenshot(BaseSetup.getDriver(), iTestResult.getName());
        } catch (Exception e) {
            LogUtils.info("Exception while taking screenshot " + e.getMessage());
        }

    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        LogUtils.warn(getTestName(iTestResult) + " test is skipped.");
        ExtentTestManager.logMessage(Status.SKIP, getTestName(iTestResult) + " test is skipped.");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        LogUtils.error("Test failed but it is in defined success ratio " + getTestName(iTestResult));
        ExtentTestManager.logMessage("Test failed but it is in defined success ratio " + getTestName(iTestResult));
    }
}