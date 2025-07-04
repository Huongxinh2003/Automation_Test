package com.Clickbuy.page;

import com.helpers.ValidateUIHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Checkout_Page_Cb extends ValidateUIHelper{
    private static WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private ValidateUIHelper validateUIHelper;

    public By PopupModal = By.xpath("//div[@id='popup-modal']");
    public By TitlePopupModal = By.xpath("//h2[contains(text(),'Đặt hàng sản phẩm')]");
    public By ProductName = By.xpath("//div[@class='jquery-modal blocker current']//h1[1]");
    public By ColorName= By.xpath("//p[contains(@class, 'flex') and contains(@class, 'active')]");
    public By ColorPrice = By.xpath("(//span[contains(text(),'₫')])[8]");
    public By SpanColor = By.xpath("//span[@class='product-name-variant']");
    public By SpanWarranty = By.cssSelector("span.product-name-variant");
    public By ProductPrice = By.cssSelector("div[class='col col-sm-5'] p[class='price']");
    public By WarrantyActive = By.xpath("//div[@class='list-variant__item list-item active']//p[@class='active']");

    public Checkout_Page_Cb(WebDriver driver) {
        super(driver);
        Checkout_Page_Cb.driver = driver;
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        validateUIHelper = new ValidateUIHelper(driver);
    }

    public boolean isPopupModalDisplayed() {
        return validateUIHelper.isElementDisplayed(PopupModal);
    }

    public boolean isTitlePopupModalDisplayed() {

        return validateUIHelper.isElementDisplayed(TitlePopupModal);
    }

    public String getTitlePopupModal() {
        return validateUIHelper.getText(TitlePopupModal);
    }

    public String getColorNamePopup() {
        WebElement colorElement = driver.findElement(ColorName);
        String colorName = colorElement.getAttribute("data-name").trim();
        System.out.println("Tên màu trong Popup Checkout: " + colorName);
        return colorName;
    }

    public String getColorPricePopup() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement activeColor = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(@class, 'flex') and contains(@class, 'active')]")
        ));
        WebElement priceElement = activeColor.findElement(ColorPrice);
        String colorPrice = priceElement.getText().trim();
        System.out.println("Giá màu trong popup Checkout: " + colorPrice);
        return colorPrice;
    }


    public String getSpanColor() {
        String SpanText = driver.findElement(SpanColor)
                .getText()
                .trim();
        System.out.println("Text từ Span color: " + SpanText);
        return SpanText;
    }

    public String getSpanWarranty() {
        String SpanText = driver.findElement(SpanWarranty)
                .getText()
                .trim();
        System.out.println("Text từ Span warranty: " + SpanText);
        return SpanText;
    }

    public String getProductNamePopup() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(ProductName));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        String titleText = (String) js.executeScript(
                "return arguments[0].childNodes[0].textContent.trim();", titleElement);
        return titleText;
    }

    public String getProductPricePopup() {
        String price = driver.findElement(ProductPrice).getText().trim();
        System.out.println("Giá sản phẩm: " + price);
        return price;
    }

    public String getActiveWarrantyPopup() {
        WebElement activeInPopup = driver.findElement(WarrantyActive);
        String titlePopup = activeInPopup.getAttribute("data-name").trim();
        System.out.println("Bảo hành active: " + titlePopup);
        return titlePopup;
    }


}
