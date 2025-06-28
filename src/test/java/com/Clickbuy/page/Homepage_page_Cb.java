package com.Clickbuy.page;


import com.helpers.ValidateUIHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Homepage_page_Cb extends ValidateUIHelper {
    private WebDriver driver;
    private WebDriverWait wait;

    private final By searchInput = By.xpath("//input[@id='inp-search']");

    public Homepage_page_Cb(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }
}
