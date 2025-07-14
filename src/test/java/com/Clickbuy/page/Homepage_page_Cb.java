package com.Clickbuy.page;


import com.helpers.ValidateUIHelper;
import com.ultilities.logs.LogUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Homepage_page_Cb extends ValidateUIHelper {
    private WebDriver driver;
    private WebDriverWait wait;

    private final By searchInput = By.xpath("//input[@id='inp-search']");
    private final By toast = By.cssSelector(".jq-toast-single");

    public Homepage_page_Cb(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public Search_Page_Cb openSearchPage() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement toastElement = driver.findElement(toast);
            wait.until(ExpectedConditions.stalenessOf(toastElement));
        } catch (NoSuchElementException e) {
            LogUtils.info("Không có toast");
        }

        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='75%'");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("inp-search")));
        WebElement search = driver.findElement(searchInput);
        wait.until(ExpectedConditions.elementToBeClickable(search));
        clickElement(search);

        return new Search_Page_Cb(driver);
    }
}

