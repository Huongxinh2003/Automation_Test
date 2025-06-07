package com.driver;

import org.openqa.selenium.WebDriver;

public class BrowserHelper {
    private WebDriver driver;

    public BrowserHelper(WebDriver driver) {
        this.driver = driver;
    }

    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }

    public void maximizeWindow() {
        driver.manage().window().maximize();
    }

    public void navigateBack() {
        driver.navigate().back();
    }

    public void navigateForward() {
        driver.navigate().forward();
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }
}
