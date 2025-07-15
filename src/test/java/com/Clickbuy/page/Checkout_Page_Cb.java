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
    private ValidateUIHelper validateUIHelper;

    public By PopupModal = By.xpath("//div[@id='popup-modal']");
    public By TitlePopupModal = By.xpath("//h2[contains(text(),'Đặt hàng sản phẩm')]");
    public By ProductName = By.xpath("//div[@class='jquery-modal blocker current']//h1[1]");
    public By ColorName= By.xpath("//p[contains(@class, 'flex') and contains(@class, 'active')]");
    public By ColorPrice = By.xpath("(//span[@class='font-normal'][contains(text(),'₫')])[6]");
    public By SpanColor = By.xpath("//span[@class='product-name-variant']");
    public By SpanWarranty = By.cssSelector("span.product-name-variant");
    public By ProductPrice = By.cssSelector("div[class='col col-sm-5'] p[class='price']");
    public By WarrantyActive = By.xpath("//div[@class='list-variant__item list-item active']//p[@class='active']");
    public By InputName = By.xpath("//input[@placeholder='Họ và tên*']");
    public By InputPhone = By.xpath("//input[@placeholder='Điện thoại*']");
    public By InputEmail = By.xpath("//input[@placeholder='Email']");
    public By ButtonBuy = By.xpath("//button[@id='btnSubInstallmentForm']");

    public Checkout_Page_Cb(WebDriver driver) {
        super(driver);
        this.driver = driver;
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
        WebElement activeColor = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class, 'list-item') and contains(@class, 'active')]")
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

    public void inputName(String name) {
        WebElement inputName = wait.until(ExpectedConditions.visibilityOfElementLocated(InputName));
        inputName.clear();
        sendKeys(InputName, name);
    }

    public void inputPhone(String phone) {
        WebElement inputPhone = wait.until(ExpectedConditions.visibilityOfElementLocated(InputPhone));
        inputPhone.clear();
        sendKeys(InputPhone, phone);
    }

    public void inputEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(InputEmail));
        sendKeys(InputEmail, email);
    }

    public void clickButtonBuy() {
        WebElement ButtonBuyNow = wait.until(ExpectedConditions.elementToBeClickable(ButtonBuy));
        (js).executeScript("arguments[0].scrollIntoView(true);", ButtonBuyNow);
        clickElement(ButtonBuy);
    }

    public WebElement getInputNameElement() {
        return driver.findElement(InputName);
    }

    public WebElement getInputPhoneElement() {
        return driver.findElement(InputPhone);
    }

    public WebElement getInputEmailElement() {
        return driver.findElement(InputEmail);
    }

    public String getFailToast() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement toastElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("jq-toast-single")));
        String fullText = toastElement.getText();

        String cleanedText = fullText
                .replace("×", "")
                .replace("Lỗi", "")
                .trim();

        return cleanedText;
    }

}
