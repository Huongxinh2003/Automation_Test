package com.helpers;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;

public class ValidateUIHelper {
    private WebDriver driver;
    private JavascriptExecutor js;
    private WebDriverWait wait;

    public ValidateUIHelper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }

    // Click element bằng JavaScript
    public void clickElement(By by) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(by));
        js.executeScript("arguments[0].click();", element);
    }

//    // Click element và scroll vào view
//    public void clickElementByJs(By by) {
//        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
//        js.executeScript("arguments[0].scrollIntoView(true);", element);
//        js.executeScript("arguments[0].click();", element);
//    }

    public void clickElement(WebElement element) {
        js.executeScript("arguments[0].click();", element);

    }

    // Gửi dữ liệu vào ô input
    public void sendKeys(By by, String text) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(by));
        element.clear();
        element.sendKeys(text);
    }

    // Chọn dropdown dạng <select> theo visible text
    public void selectDropdownByVisibleText(By by, String visibleText) {
        WebElement dropdownElement = wait.until(ExpectedConditions.elementToBeClickable(by));
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText(visibleText);
    }

    // Chọn dropdown dạng <select> hoặc custom dropdown
    public void selectDropdown(By by, String visibleText) {
        WebElement dropdownElement = wait.until(ExpectedConditions.elementToBeClickable(by));
        dropdownElement.click();

        try {
            Select select = new Select(dropdownElement);
            select.selectByVisibleText(visibleText);
        } catch (UnexpectedTagNameException e) {
            // Nếu không phải <select>, xử lý custom dropdown
            By optionLocator = By.xpath("//div[contains(text(),'" + visibleText + "')]");
            wait.until(ExpectedConditions.visibilityOfElementLocated(optionLocator)).click();
        }
    }

    // Kiểm tra phần tử có hiển thị không
    public boolean isElementDisplayed(By by) {
        try {
            return driver.findElement(by).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // Kiểm tra phần tử có enabled không
    public boolean isElementEnabled(By by) {
        try {
            return driver.findElement(by).isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // Lấy text từ element
    public String getText(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by)).getText();
    }

    // Lấy giá trị thuộc tính của element
    public String getElementAttribute(By by, String attribute) {
        return driver.findElement(by).getAttribute(attribute);
    }

    // Copy text vào clipboard
    public static void copyToClipboard(String text) {
        StringSelection selection = new StringSelection(text);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
    }

    // Dán clipboard bằng Robot
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
                ((JavascriptExecutor) driver).executeScript("return document.readyState")
                        .toString().equals("complete");

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(expectation);
        } catch (Throwable error) {
            Assert.fail("Quá thời gian chờ trang tải (readyState != complete)");
        }
    }
}
