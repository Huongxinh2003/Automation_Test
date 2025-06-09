package com.cellphoneS.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Product_Detail_Page {
    private WebDriver driver;
    private WebDriverWait wait;

    public Product_Detail_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
}
