package com.cellphoneS.pages;

import com.helpers.ValidateUIHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class Product_Detail_Page extends ValidateUIHelper {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;


    public By ProductCard = By.xpath("//div[@class='product-list-filter is-flex is-flex-wrap-wrap']//div[1]//div[1]//a[1]");






    public Product_Detail_Page(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

}
