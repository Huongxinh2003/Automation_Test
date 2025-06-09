package com.cellphoneS.pages;

import com.helpers.ValidateUIHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Search_Page extends ValidateUIHelper {
    private static WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    private final By searchInput = By.xpath("//input[@id='inp$earch']");
    private final By IconSearch = By.xpath("//button[@type='submit']//div//*[name()='svg']");

    public Search_Page(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void inputSearch(String searchText) {
        sendKeys(searchInput, searchText);
        clickElement(IconSearch);
    }

    public void inputSearch2(String searchText) {
        sendKeys(searchInput, searchText);
        sendKeys(searchInput, String.valueOf(Keys.ENTER));
    }

}
