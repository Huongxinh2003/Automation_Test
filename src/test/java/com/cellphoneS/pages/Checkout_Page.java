package com.cellphoneS.pages;

import com.helpers.ValidateUIHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class Checkout_Page extends ValidateUIHelper {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static JavascriptExecutor js;

    //Trang thông tin
    public By TitleCheckout = By.xpath("//h1[contains(text(),'Thông tin giao hàng')]");
    public By CheckoutCart = By.xpath("//div[@class='view-list']");
    public By InputEmail = By.xpath("//input[@placeholder='Email']");
    public By CheckboxPromo = By.xpath("//input[@id='emailPromo']");
    public By CheckboxPickUp = By.xpath("//input[@id='pickup']");
    public By InputCity = By.xpath("//input[@placeholder='Đà Nẵng']");
    public By InputDistrict = By.xpath("//input[@placeholder='Chọn quận/huyện']");
    public By InputAddress = By.xpath("//input[@placeholder='Chọn địa chỉ cửa hàng']");
    public By InputNote = By.xpath("//textarea[@placeholder='Ghi chú']");
    public By CheckboxShipping = By.xpath("//input[@id='shipping']");
    public By InputName = By.xpath("//input[@placeholder='Họ tên người nhận']");
    public By InputPhone = By.xpath("//input[@placeholder='Số điện thoại người nhận']");
    public By InputCityShip = By.xpath("//input[@placeholder='Đà Nẵng']");
    public By InputDistrictShip = By.xpath("//input[@placeholder='Chọn quận/huyện']");
    public By InputAddressShip = By.xpath("//input[@placeholder='Chọn phường/xã']");
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


}
