package com.ultilities.listeners;

import com.base.BaseSetup;
import com.helpers.CaptureHelpers;
import com.ultilities.logs.LogUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


public class TestListener implements ITestListener {

    @Override
    public void onFinish(ITestContext result) {
        LogUtils.info("Kết thúc Automation Test: " + result.getName());
    }

    @Override
    public void onStart(ITestContext result) {
        LogUtils.info("Bắt đầu Automation Test: " + result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onTestFailure(ITestResult result) {
        LogUtils.info("Đây là test case bị fail: " + result.getName());

        try {
            CaptureHelpers.captureScreenshot(BaseSetup.getDriver(), result.getName());
        } catch (Exception e) {
            LogUtils.info("Exception while taking screenshot " + e.getMessage());
        }

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LogUtils.info("Đây là test case bị bỏ qua: " + result.getName());

    }

    @Override
    public void onTestStart(ITestResult result) {

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LogUtils.info("Đây là test case chạy thành công: " + result.getName());

    }
}

