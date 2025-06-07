package com.helpers;

import org.openqa.selenium.*;
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
    private static JavascriptExecutor js;
    private static WebDriverWait wait;
    public ValidateUIHelper(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }

    // Click vào một phần tử
    public static void clickElement(By element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        driver.findElement(element).click();
        js.executeScript("arguments[0].click();", element);
    }

    public void clickElementByJs(By element) {
        WebElement element1 = driver.findElement(element);
        js.executeScript("arguments[0].scrollIntoView(true);", element1);
        js.executeScript("arguments[0].click();", element1);
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

    public void waitForPageLoaded() {
        ExpectedCondition<Boolean> expectation = driver ->
                ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Timeout hợp lý
            wait.until(expectation);
        } catch (Throwable error) {
            Assert.fail("Quá thời gian chờ tải trang (readyState không = complete)");
        }
    }
}

