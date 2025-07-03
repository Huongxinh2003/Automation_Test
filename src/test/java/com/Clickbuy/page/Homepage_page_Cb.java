package com.Clickbuy.page;


import com.helpers.ValidateUIHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Homepage_page_Cb extends ValidateUIHelper {
    private WebDriver driver;
    private WebDriverWait wait;

    private final By searchInput = By.xpath("//input[@id='inp-search']");
    private final By Toast = By.cssSelector(".jq-toast-single");

    public Homepage_page_Cb(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }
    public Search_Page_Cb openSearchPage() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement toast = driver.findElement(Toast);
        wait.until(ExpectedConditions.stalenessOf(toast));

        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='75%'");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement search = driver.findElement(searchInput);
        wait.until(ExpectedConditions.elementToBeClickable(search));
        clickElement(search);
        return new Search_Page_Cb(driver);
    }
}
