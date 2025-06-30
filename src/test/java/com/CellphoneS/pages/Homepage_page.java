package com.CellphoneS.pages;

import com.helpers.ValidateUIHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Homepage_page extends ValidateUIHelper {
    private WebDriver driver;
    private WebDriverWait wait;

    private final By searchInput = By.xpath("//input[@id='inp$earch']");

    public Homepage_page(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public Search_Page openSearchPage() {
        // Thu nhỏ website sau khi mở trang
        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='75%'");
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement search = driver.findElement(searchInput);
        search.click();
        return new Search_Page(driver);
    }
}
