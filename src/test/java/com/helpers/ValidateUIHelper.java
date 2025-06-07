package com.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;

public class ValidateUIHelper {
    private static WebDriver driver;
    private JavascriptExecutor js;
    private static WebDriverWait wait;
    public ValidateUIHelper(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
    }

    // Click vào một phần tử
    public static void clickElement(By element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        driver.findElement(element).click();
        //click với js
//        js.executeScript("arguments[0].click();", driver.findElement(element));
    }

    // Gửi dữ liệu vào ô input
    public void sendKeys(By element, String text) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        driver.findElement(element).clear();
        driver.findElement(element).sendKeys(text);
    }


    // Chọn giá trị từ dropdown theo visible text
    public void selectDropdownByVisibleText(By element, String visibleText) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        WebElement dropdownElement = driver.findElement(element);
        dropdownElement.click();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText(visibleText);
    }

    // Kiểm tra phần tử có hiển thị không
    public boolean isElementDisplayed(By element) {
        try {
            return driver.findElement(element).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Kiểm tra phần tử có sẵn sàng thao tác không
    public boolean isElementEnabled(By element) {
        try {
            return driver.findElement(element).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    // Lấy text hiển thị trên phần tử
    public String getText(By element) {
        return driver.findElement(element).getText();
    }

    // Lấy giá trị thuộc tính bất kỳ
    public String getElementAttribute(By element, String attribute) {
        return driver.findElement(element).getAttribute(attribute);
    }

    public static void copyToClipboard(String text) {
        StringSelection selection = new StringSelection(text);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
    }

    public static void pasteWithRobot() throws AWTException {
        Robot robot = new Robot();
        robot.delay(300);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(300);
    }

    // Đợi trang tải xong
    public void waitForPageLoaded() {
        ExpectedCondition<Boolean> expectation = driver ->
                ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
        try {
            Thread.sleep(1000);
            Duration duration = Duration.ofSeconds(5);
            WebDriverWait wait = new WebDriverWait(driver, duration.ofSeconds(5));
            wait.until(expectation);
        } catch (Throwable error) {
            Assert.fail("Quá thời gian chờ tải trang");
        }
    }
}

