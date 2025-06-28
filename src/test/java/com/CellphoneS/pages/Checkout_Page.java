package com.CellphoneS.pages;

import com.helpers.ValidateUIHelper;
import com.ultilities.logs.LogUtils;
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

    String city = "Hà Nội";
    //Trang thông tin
    public By TabInfo = By.xpath("//div[@class='nav__item nav__item--active']");
    public By TabPayment = By.xpath("//div[@class='nav__item nav__item--active']");
    public By CheckoutCard = By.xpath("//div[@class='view-list']");
    public By ProductQuantityCard = By.xpath("//span[@class='text-danger']");
    public By BasePrice = By.xpath("//p[@class='product__price--through']");
    public By PriceCard = By.xpath("//p[@class='product__price--show']");
    public By InputEmail = By.xpath("//input[@placeholder='Email']");
    public By NameCustomer = By.xpath("//p[contains(text(),'Phùng Hương')]");
    public By PhoneCustomer = By.xpath("//p[@class='customer-phone']");
    public By CheckboxPromo = By.xpath("//input[@id='emailPromo']");
    public By CheckboxPickUp = By.xpath("//input[@id='pickup']");
    public By DropdownCity = By.xpath(String.format("//input[@placeholder='%s']", city));
//    public By SuggestionCity = (By.xpath(String.format("//div[@class='dropdown__item dropdown__item--active']//span[contains(text(),'%s')]", city)));
    public By ButtonAgree = By.xpath("//button[contains(text(),'Đồng ý')]");
    public By DropdownDistrict = By.xpath("//input[@placeholder='Chọn quận/huyện']");
    public By DropdownAddress = By.xpath("//input[@placeholder='Chọn phường/xã']");
    public By InputNote = By.xpath("//input[@placeholder='Ghi chú khác (nếu có)']");
    public By CheckboxShipping = By.xpath("//input[@id='shipping']");
    public static By InputName = By.xpath("//input[@placeholder='Họ tên người nhận']");
    public By InputPhone = By.xpath("//input[@placeholder='Số điện thoại người nhận']");
    public By DropdownCityShip = By.xpath(String.format("//input[@placeholder='%s']", city));
    public By DropdownDistrictShip = By.xpath("//input[@placeholder='Chọn quận/huyện']");
    public By DropdownAddressShip = By.xpath("//input[@placeholder='Chọn phường/xã']");
    public By InputHomeNumberShip = By.xpath("//input[@placeholder='Số nhà, tên đường']");
    public By InputNoteShip = By.xpath("//input[@placeholder='Ghi chú khác (nếu có)']");
    public By PriceTemp = By.xpath("//span[@class='total']");
    public By ButtonContinue = By.xpath("//button[contains(text(),'Tiếp tục')]");
    public By InputVatNo = By.xpath("//input[@id='VAT-No']");
    public By InputVatYes = By.xpath("//input[@id='VAT-Yes']");

    //TRang thanh toán
    public By DiscountCode = By.xpath("//input[@placeholder='Nhập mã giảm giá (chỉ áp dụng 1 lần)']");
    public By ButtonApply1 = By.xpath("//button[contains(text(),'Xác nhận')]");
    public By ToastMessageCode = By.xpath("//div[@class='b-toaster-slot vue-portal-target']");
    public By ButtonApply = By.xpath("//button[contains(text(),'Áp dụng')]");
    public By ProductQuantity = By.cssSelector("div[class='info-payment'] div:nth-child(1) div:nth-child(1) p:nth-child(1)");
    public By BasePriceProduct = By.xpath("//p[contains(text(),'đ')]");
    public By TotalShipping = By.xpath("//p[contains(text(),'Miễn phí')]");
    public By DiscountPrice = By.xpath("//p[@class='quote-block__price']");
    public By TotalPrice = By.xpath("//p[@class='quote-bottom__value']");
    public By PaymentMethod = By.xpath("//div[@class='payment-quote__main']");
    public By ShopeePay = By.xpath("//p[normalize-space()='ShopeePay']");
    public By ApplyPaymentMethod = By.xpath("//button[contains(text(),'Xác nhận')]");
    public By TickPaymentMethod = By.xpath("//div[@class='list-payment__item list-payment__item--active list-payment__item-shopee_pay']//div[@class='payment-item__tick']//*[name()='svg']");
    public By CustomerName = By.xpath("//p[contains(text(),'Phùng Hương')]");
    public By PhoneNumber = By.xpath("//p[normalize-space()='0332019523']");
    public By Email = By.xpath("//p[normalize-space()='quynhhuong6319@gmail.com']");//p[normalize-space()='EMAIL']");
    public By Address = By.xpath("//p[contains(text(),',')]");
    public By Note = By.xpath("//p[contains(text(),'Tới nhận hàng ngày 28/07/2025')]");
    public By CheckboxTerms = By.xpath("//input[@type='checkbox']");
    public By TotalPriceTemp = By.xpath("//span[@class='total']");
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

    public String getTabPaymentActive() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(TabPayment)).getText();
    }

    public boolean isCheckoutCartDisplayed() {
        return isElementDisplayed(CheckoutCard);
    }

    public String getBasePrice() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(BasePrice)).getText();
    }

    public void SendKeysEmail(String email) {
        WebElement clear = driver.findElement(InputEmail);
        clear.clear();
        wait.until(ExpectedConditions.elementToBeClickable(InputEmail));
        driver.findElement(InputEmail).sendKeys(email);
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
        Assert.assertFalse(checkbox.isSelected(), "Checkbox được chọn");
    }

    public void clickCheckboxPromo() {
        WebElement checkbox = driver.findElement(CheckboxPromo);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
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
        // Click input
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(DropdownCity));
        (js).executeScript("arguments[0].scrollIntoView();", input);
        input.click();
        input.clear();
        input.sendKeys(city);

        // Chờ item hiện ra và click vào nó
        String xpath = "//div[contains(@class,'dropdown__item')]/span[normalize-space()='" + city + "']";
        WebElement cityOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        cityOption.click();
    }

    public void ClickButtonAgree() {
        wait.until(ExpectedConditions.elementToBeClickable(ButtonAgree));
        driver.findElement(ButtonAgree).click();
    }

    public boolean isDropDownDistrictDisplayed() {
        return driver.findElement(DropdownDistrict).isDisplayed();
    }

    public void SendKeysDistrict(String district) {
        // Click input
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(DropdownDistrict));
        input.click();
        input.sendKeys(district);

        // Chờ item hiện ra và click vào nó
        String xpath = "//div[contains(@class,'dropdown__item')]/span[normalize-space()='" + district + "']";
        WebElement cityOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        cityOption.click();
    }

    public boolean isDropDownAddressDisplayed() {
        return driver.findElement(DropdownAddress).isDisplayed();
    }

    public void SendKeysAddress(String address) {
        // Click input
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(DropdownAddress));
        (js).executeScript("arguments[0].scrollIntoView();", input);
        input.click();
        input.sendKeys(address);

        // Chờ item hiện ra và click vào nó
        String xpath = "//div[contains(@class,'dropdown__item')]/span[normalize-space()='" + address + "']";
        WebElement cityOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        cityOption.click();
    }

    public String getAddressName() {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(DropdownAddress));
        return input.getAttribute("placeholder");
    }


    public boolean isInputNoteDisplayed() {
        return driver.findElement(InputNoteShip).isDisplayed();
    }

    public void SendKeysInputNote(String note) {
        wait.until(ExpectedConditions.elementToBeClickable(InputNote));
        sendKeys(InputNote, note);
    }

    public String getNote() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(InputNoteShip));
        return getText(InputNoteShip);
    }

    public void CLickInputVatNo() {
        wait.until(ExpectedConditions.elementToBeClickable(InputVatNo));
        driver.findElement(InputVatNo).click();
    }
    public void ClickInputVatYes() {
        wait.until(ExpectedConditions.elementToBeClickable(InputVatYes));
        driver.findElement(InputVatYes).click();
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
        // Click input
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(DropdownCityShip));
        input.click();
        input.clear();
        input.sendKeys(city);

        // Chờ item hiện ra và click vào nó
        String xpath = "//div[contains(@class,'dropdown__item')]/span[normalize-space()='" + city + "']";
        WebElement cityOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        cityOption.click();
    }

    public boolean isDropdownDistrictShippingDisplayed() {
        return driver.findElement(DropdownDistrictShip).isDisplayed();
    }

    public void SendKeysDistrictShipping(String district) {
        // Click input
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(DropdownDistrictShip));
        input.click();
        input.sendKeys(district);

        // Chờ item hiện ra và click vào nó
        String xpath = "//div[contains(@class,'dropdown__item')]/span[normalize-space()='" + district + "']";
        WebElement cityOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        cityOption.click();
    }

    public boolean isDropdownAddressShippingDisplayed() {
        return driver.findElement(DropdownAddressShip).isDisplayed();
    }

    public void SendKeysAddressShipping(String address) {
        // Click input
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(DropdownAddressShip));
        input.click();
        input.sendKeys(address);

        // Chờ item hiện ra và click vào nó
        String xpath = "//div[contains(@class,'dropdown__item')]/span[normalize-space()='" + address + "']";
        WebElement cityOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        cityOption.click();
    }

    public boolean isInputHomeNumberDisplayed() {
        return driver.findElement(InputHomeNumberShip).isDisplayed();
    }

    public void SendKeysInputHomeNumber(String homeNumber) {
        // Click input
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(InputHomeNumberShip));
        input.click();
        input.sendKeys(homeNumber);

    }

    public boolean isInputNoteShippingDisplayed() {
        return driver.findElement(InputNoteShip).isDisplayed();
    }

    public void SendKeysInputNoteShipping(String note) {
        wait.until(ExpectedConditions.elementToBeClickable(InputNoteShip));
        driver.findElement(InputNoteShip).sendKeys(note);
    }

    public String getPriceCard() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(PriceCard));
        return driver.findElement(PriceCard).getText();
    }

    public String getPriceTemp() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(PriceTemp));
        return driver.findElement(PriceTemp).getText();
    }

    public void ClickButtonCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(ButtonContinue));
        driver.findElement(ButtonContinue).click();
    }

    public void SendkeysDiscountCode(String discountCode) {
        wait.until(ExpectedConditions.elementToBeClickable(DiscountCode));
        sendKeys(DiscountCode, discountCode);
    }

    public void ClickButtonApply() {
        wait.until(ExpectedConditions.elementToBeClickable(ButtonApply));
        clickElement(ButtonApply);
    }

    public void ClickButtonApply1() {
        wait.until(ExpectedConditions.elementToBeClickable(ButtonApply1));
        clickElement(ButtonApply1);
    }

    public String getToastMessageCode() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(ToastMessageCode));
        return driver.findElement(ToastMessageCode).getText();
    }

    public String getProductQuantityPayment() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ProductQuantity)).getText();
    }

    public String getProductQuantityInfo() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ProductQuantityCard)).getText();
    }

    public String getBasePriceProduct() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(BasePriceProduct)).getText();
    }

    public String getTotalShipping() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(TotalShipping)).getText();
    }

    public String getDiscountPrice() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(DiscountPrice)).getText();
    }

    public String getTotalPrice() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(TotalPrice));
        (js).executeScript("arguments[0].scrollIntoView();", element);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(TotalPrice)).getText();
    }

    public void ClickPaymentMethod() {
        wait.until(ExpectedConditions.elementToBeClickable(PaymentMethod));
        (js).executeScript("arguments[0].scrollIntoView();", driver.findElement(PaymentMethod));
        clickElement(PaymentMethod);
    }

    public void ClickShopeePay() {
        wait.until(ExpectedConditions.elementToBeClickable(ShopeePay));
        (js).executeScript("arguments[0].scrollIntoView();", driver.findElement(ShopeePay));
        clickElement(ShopeePay);
    }

    public boolean isTickPaymentMethodDisplayed() {
        return isElementDisplayed(TickPaymentMethod);
    }

    public void ClickApplyPaymentMethod() {
        wait.until(ExpectedConditions.elementToBeClickable(ApplyPaymentMethod));
        clickElement(ApplyPaymentMethod);
    }

    public String getCustomerName() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(CustomerName));
        (js).executeScript("arguments[0].scrollIntoView();", element);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(CustomerName)).getText();
    }

    public String getPhoneNumber() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(PhoneNumber)).getText();
    }

    public String getEmail() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(Email)).getText();
    }

    public String getAddress() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(Address)).getText();
    }

    public String getNote2() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(Note)).getText();
    }

    public boolean isCheckboxTermsSelected() {
        WebElement checkbox = driver.findElement(CheckboxTerms);
        if(!checkbox.isSelected()){
            LogUtils.info("Chưa tích chọn checkbox");
        }else {
            LogUtils.info("Đã tích chọn checkbox");
        }
        return checkbox.isSelected();
    }

    public String getTotalPriceTemp() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(TotalPriceTemp)).getText();
    }

    public void ClickListProduct() {
        wait.until(ExpectedConditions.elementToBeClickable(ListProduct));
        clickElement(ListProduct);
    }

    public boolean isTitleListProductDisplayed() {
        return isElementDisplayed(TitleListProduct);
    }


}
