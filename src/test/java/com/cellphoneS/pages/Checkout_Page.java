package com.cellphoneS.pages;

import com.helpers.ValidateUIHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class Checkout_Page extends ValidateUIHelper {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static JavascriptExecutor js;

    //Trang thông tin
    public By TitleCheckout = By.xpath("//h1[contains(text(),'Thông tin giao hàng')]");
    public By TabInfo = By.xpath("//div[@class='nav__item nav__item--active']");
    public By CheckoutCart = By.xpath("//div[@class='view-list']");
    public By InputEmail = By.xpath("//div[@class='customer-input__2']//div[@class='box-input__line']");
    public By NameCustomer = By.xpath("//p[contains(text(),'Phùng Hương')]");
    public By PhoneCustomer = By.xpath("//p[@class='customer-phone']");
    public By CheckboxPromo = By.xpath("//input[@id='emailPromo']");
    public By CheckboxPickUp = By.xpath("//input[@id='pickup']");
    public By DropdownCity = By.xpath("//input[@placeholder='Hà Nội']");
    public By ButtonAgree = By.xpath("//button[contains(text(),'Đồng ý')]");
    public By DropdownDistrict = By.xpath("//input[@placeholder='Chọn quận/huyện']");
    public By DropdownAddress = By.xpath("//input[@placeholder='Chọn địa chỉ cửa hàng']");
    public By InputNote = By.xpath("//textarea[@placeholder='Ghi chú']");
    public By CheckboxShipping = By.xpath("//input[@id='shipping']");
    public static By InputName = By.xpath("//input[@placeholder='Họ tên người nhận']");
    public By InputPhone = By.xpath("//input[@placeholder='Số điện thoại người nhận']");
    public By DropdownCityShip = By.xpath("//input[@placeholder='Đà Nẵng']");
    public By DropdownDistrictShip = By.xpath("//input[@placeholder='Chọn quận/huyện']");
    public By DropdownAddressShip = By.xpath("//input[@placeholder='Chọn phường/xã']");
    public By InputHomeNumberShip = By.xpath("//input[@placeholder='Số nhà, tên đường']");
    public By InputNoteShip = By.xpath("//input[@placeholder='Ghi chú khác (nếu có)']");
    public By PriceTemp = By.xpath("//span[@class='total']");
    public By ButtonContinue = By.xpath("//button[contains(text(),'Tiếp tục')]");

    //TRang thanh toán
    public By RadioVATYes = By.xpath("//input[@id='VAT-Yes']");
    public By RadioVATNo = By.xpath("//input[@id='VAT-No']");
    public By DiscountCode = By.xpath("//input[@placeholder='Nhập mã giảm giá (chỉ áp dụng 1 lần)']");
    public By ButtonApply = By.xpath("//button[contains(text(),'Áp dụng')]");
    public By ProductQuantity = By.cssSelector("div[class='info-payment'] div:nth-child(1) div:nth-child(1) p:nth-child(1)");
    public By TotalProduct = By.xpath("//p[contains(text(),'đ')]");
    public By TotalShipping = By.xpath("//p[contains(text(),'Miễn phí')]");
    public By DiscountPrice = By.xpath("//p[@class='quote-block__price']");
    public By TotalPrice = By.xpath("//p[@class='quote-bottom__value']");
    public By PaymentMethod = By.xpath("//div[@class='payment-quote__modal']");
    public By CustomerName = By.xpath("//p[contains(text(),'Phùng Hương')]");
    public By PhoneNumber = By.xpath("//p[normalize-space()='0332019523']");
    public By Email = By.xpath("//p[normalize-space()='quynhhuong6319@gmail.com']");//p[normalize-space()='EMAIL']");
    public By CheckboxTerms = By.xpath("//input[@type='checkbox']");
    public By TotalPricePay = By.xpath("//span[@class='total']");
    public By ButtonBuyNow = By.xpath("//button[normalize-space()='Thanh toán']");
    public By ListProduct = By.xpath("//button[@id='viewListItemInQuote-btn']");
    public By TitleListProduct = By.xpath("//header[@id='modalViewListItemInQuote___BV_modal_header_']");

    public Checkout_Page(WebDriver driver) {
        super(driver);
        Checkout_Page.driver = driver;
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public static String getTitleCheckout() {
        return driver.getTitle();
    }

    public static boolean verifyTitleCheckout() {
        String expectedTitle = "CellphoneS Checkout";
        Assert.assertEquals(getTitleCheckout(), expectedTitle);
        return getTitleCheckout().equals(expectedTitle);
    }

    public String getTabInfoActive() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(TabInfo)).getText();
    }

    public boolean isCheckoutCartDisplayed() {
        return isElementDisplayed(CheckoutCart);
    }

    public String getInputEmail() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement emailElement = wait.until(ExpectedConditions.visibilityOfElementLocated(InputEmail));
        return emailElement.getText();
    }

    public String getNameCustomer() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement nameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(NameCustomer));
        return nameElement.getText();
    }

    public String getPhoneCustomer() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement phoneElement = wait.until(ExpectedConditions.visibilityOfElementLocated(PhoneCustomer));
        return phoneElement.getText();
    }

    public void isSelectedCheckboxPromotion() {
        wait.until(ExpectedConditions.elementToBeClickable(CheckboxPromo));
        WebElement checkbox = driver.findElement(CheckboxPromo);
        Assert.assertTrue(checkbox.isSelected(), "Checkbox chưa được chọn");
    }

    public boolean isSelectedCheckboxPickup() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(CheckboxPickUp));
        (js).executeScript("arguments[0].scrollIntoView();", element);
        WebElement checkbox = driver.findElement(CheckboxPickUp);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        return checkbox.isSelected();
    }

    public boolean isDropDownCityDisplayed() {
        return driver.findElement(DropdownCity).isDisplayed();
    }

    public void SendKeysCity(String city) {
        wait.until(ExpectedConditions.elementToBeClickable(DropdownCity));
        driver.findElement(DropdownCity).sendKeys(city);
    }

    public String getCityName() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(DropdownCity));
        return getText(DropdownCity);
    }

    public void ClickButtonAgree() {
        wait.until(ExpectedConditions.elementToBeClickable(ButtonAgree));
        driver.findElement(ButtonAgree).click();
    }

    public boolean isDropDownDistrictDisplayed() {
        return driver.findElement(DropdownDistrict).isDisplayed();
    }

    public void SendKeysDistrict(String district) {
        wait.until(ExpectedConditions.elementToBeClickable(DropdownDistrict));
        driver.findElement(DropdownDistrict).sendKeys(district);
    }

    public boolean isDropDownAddressDisplayed() {
        return driver.findElement(DropdownAddress).isDisplayed();
    }

    public void SendKeysAddress(String address) {
        wait.until(ExpectedConditions.elementToBeClickable(DropdownAddress));
        driver.findElement(DropdownAddress).sendKeys(address);
    }


    public boolean isInputNoteDisplayed() {
        return driver.findElement(InputNoteShip).isDisplayed();
    }

    public void SendKeysInputNote(String note) {
        wait.until(ExpectedConditions.elementToBeClickable(InputNote));
        sendKeys(InputNote, note);
    }

    public void ClickCheckboxShip() {
        wait.until(ExpectedConditions.elementToBeClickable(CheckboxShipping));
        (js).executeScript("arguments[0].scrollIntoView();", driver.findElement(CheckboxShipping));
        driver.findElement(CheckboxShipping).click();
    }

    public String getInputName() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(InputName));
        return driver.findElement(InputName).getText();
    }

    public String getInputPhone() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(InputPhone));
        return driver.findElement(InputPhone).getText();
    }

    public boolean isDropdownCityShippingDisplayed() {
        return driver.findElement(DropdownCityShip).isDisplayed();
    }

    public void SendKeysCityShipping(String city) {
        wait.until(ExpectedConditions.elementToBeClickable(DropdownCityShip));
        driver.findElement(DropdownCityShip).sendKeys(city);
    }

    public boolean isDropdownDistrictShippingDisplayed() {
        return driver.findElement(DropdownDistrictShip).isDisplayed();
    }

    public void SendKeysDistrictShipping(String district) {
        wait.until(ExpectedConditions.elementToBeClickable(DropdownDistrictShip));
        driver.findElement(DropdownDistrictShip).sendKeys(district);
    }

    public boolean isDropdownAddressShippingDisplayed() {
        return driver.findElement(DropdownAddressShip).isDisplayed();
    }

    public void SendKeysAddressShipping(String address) {
        wait.until(ExpectedConditions.elementToBeClickable(DropdownAddressShip));
        driver.findElement(DropdownAddressShip).sendKeys(address);
    }

    public boolean isInputHomeNumberDisplayed() {
        return driver.findElement(InputHomeNumberShip).isDisplayed();
    }

    public void SendKeysInputHomeNumber(String homeNumber) {
        wait.until(ExpectedConditions.elementToBeClickable(InputHomeNumberShip));
        driver.findElement(InputHomeNumberShip).sendKeys(homeNumber);
    }

    public boolean isInputNoteShippingDisplayed() {
        return driver.findElement(InputNoteShip).isDisplayed();
    }

    public void SendKeysInputNoteShipping(String note) {
        wait.until(ExpectedConditions.elementToBeClickable(InputNoteShip));
        driver.findElement(InputNoteShip).sendKeys(note);
    }

    public String getPriceTemp() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(PriceTemp));
        return driver.findElement(PriceTemp).getText();
    }

    public void ClickButtonCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(ButtonContinue));
        driver.findElement(ButtonContinue).click();
    }


}
