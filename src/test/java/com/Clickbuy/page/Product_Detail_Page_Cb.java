package com.Clickbuy.page;

import com.helpers.ValidateUIHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Product_Detail_Page_Cb extends ValidateUIHelper{
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static JavascriptExecutor js;

    public By TitleProduct = By.xpath("//h1[contains(text(),'iPhone 13 128GB chính hãng VN/A - Tặng BH rơi vỡ v')]");

    public Product_Detail_Page_Cb(WebDriver driver) {
        super(driver);
        Product_Detail_Page_Cb.driver = driver;
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public boolean isTitleProductDisplayed() {
        return isElementDisplayed(TitleProduct);
    }

    public String getTitleProduct() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(TitleProduct));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String titleText = (String) js.executeScript("return arguments[0].textContent.trim();", titleElement);
        return titleText;
    }
}
