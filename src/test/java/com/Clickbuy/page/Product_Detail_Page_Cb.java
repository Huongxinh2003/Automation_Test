package com.Clickbuy.page;

import com.CellphoneS.pages.Cart_Page;
import com.helpers.ValidateUIHelper;
import com.ultilities.listeners.ReportListener;
import com.ultilities.logs.LogUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Product_Detail_Page_Cb extends ValidateUIHelper{
    private static WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private ValidateUIHelper validateUIHelper;

    public By ProductName = By.xpath("//h1[normalize-space()='iPhone 16 Pro Max 512GB Chính Hãng VN/A']");
    public By LeftBanner = By.xpath("//a[@class='leftside-banner']");
    public By RightBanner = By.xpath("//a[@class='rightside-banner']");
    public By Banner = By.xpath("//div[contains(@class,'product-slide-box')]//a[contains(@title,'iPhone 16 series')]");
    public By QuantityEvaluation = By.cssSelector("div[class='product-meta'] p");
    public By QuantityStar = By.xpath("//div[@class='product-meta']//ul");
    public By ProductThumb = By.xpath("//div[@class='product-slide']");
    public By BasePrice = By.xpath("//p[@class='price-old ']");
    public By SalePrice = By.xpath("//p[@class='price']");
    public By WarrantyContentBH = By.xpath("//th[@class='warranty-line basic ']");
    public By WarrantyContentBH_price = By.xpath("//strong[contains(text(),'35.950.000 ₫')]");
    public By WarrantyContent24M = By.xpath("//th[contains(@class,'warranty-line gold')]");
    public By WarrantyContent24M_price = By.xpath("//strong[contains(text(),'37.250.000 ₫')]");
    public By WarrantyContent24M_plus = By.xpath("//span[contains(text(),'( +1.300.000 ₫ )')]");
    public By ButtonWarrantyBH = By.xpath("//p[contains(@data-name,'BH chính hãng')]");
    public By ButtonWarranty24M = By.xpath("//div[@title='Gia hạn 24 tháng']");
    public By TitleSpecification = By.xpath("//div[contains(@class,'product-specification__title')]");
    public By BoxRating = By.xpath("//div[@class='block-rate__star']");
    public By GiftBox = By.xpath("//div[@class='gift-content']//p[1]");
    public By CityOption = By.xpath("//select[@name='area']");
    public By SelectAddress = By.xpath("//svg[contains(@class, 'svg-icon-map')]/parent::*");
    public By PopupAddress = By.xpath("substring-after(//div[@class='store']/h2, 'Địa chỉ:')");
    public By PopupPhone = By.xpath("substring-after(//div[@class='store']//a[@title='Hotline']/@href, 'tel:')");
    public By ListAddress = By.xpath("//div[@class='area-list show']//div[1]");
    public By ListPhone = By.xpath("//div[@class='area-list__item store-list__item store-select__item']//span[@class='phone-number']");
    public By BoxAddress = By.xpath("//div[@class='area-list__item store-list__item store-select__item']");
    public By PhoneContact = By.xpath("//input[@placeholder='Tư vấn qua số điện thoại']");
    public By ButtonSumit = By.xpath("//button[@class='submit_call']");
    public By ToastContact = By.xpath("//span[@class='close-jq-toast-single']");
    public By EvaluationInput = By.xpath("//textarea[@placeholder='Hãy để lại bình luận của bạn tại đây!']");
    public By EvaluationStar = By.xpath("//span[@class='star']");
    public By EvaluationSubmit = By.xpath("//button[@title='Gửi']");
    public static By ToastEvaluation = By.xpath("//div[@class='jq-toast-single jq-has-icon jq-icon-success']");
    public By ButtonBuyNow = By.xpath("//p[@class='add-to-cart']");
    public By ColorName = By.xpath("//p[contains(@class, 'flex') and contains(@class, 'active')]");
    public By ColorPrice = By.xpath(".//span[@class='font-normal']");
    public By ProductPrice = By.cssSelector("div[class='col col-sm-5'] p[class='price']");
    public By WarrantyActive = By.xpath("//p[@class='active']");


    public Product_Detail_Page_Cb(WebDriver driver) {
        super(driver);
        Product_Detail_Page_Cb.driver = driver;
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        validateUIHelper = new ValidateUIHelper(driver);
    }

    public Checkout_Page_Cb openCheckoutPage() {
        LogUtils.info("Chọn phiên bản");
        selectVersionProduct("512GB");
        validateUIHelper.waitForPageLoaded();
        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='80%'");
        LogUtils.info("Chọn màu sắc");
        selectColorProduct("Natural");

        ((JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll('iframe').forEach(f => f.style.display = 'none');"
        );

        WebElement Buynow = wait.until(ExpectedConditions.visibilityOfElementLocated(ButtonBuyNow));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", Buynow);
        (js).executeScript("arguments[0].click();", Buynow);

        js.executeScript("document.getElementById('popup-modal').style.display = 'inline-block';");
        LogUtils.info("Đã click được vào button 'Buy Now'");
        return new Checkout_Page_Cb(driver);
    }

    public boolean isProductNameDisplayed() {
        return isElementDisplayed(ProductName);
    }

    public String getProductName() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(ProductName));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String titleText = (String) js.executeScript("return arguments[0].textContent.trim();", titleElement);
        return titleText;
    }

    public void selectVersionProduct(String version) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='512GB']")));
        webElement.click();
    }

    public void selectColorProduct(String color) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@title='Natural']")));
        webElement.click();
    }

    public boolean isBoxRatingDisplayed() {
        return isElementDisplayed(BoxRating);
    }
    public boolean isQuantityEvaluationDisplayed() {
        return isElementDisplayed(QuantityEvaluation);
    }

    public boolean isQuantityStarDisplayed() {
        return isElementDisplayed(QuantityStar);
    }

    public String selectCity(String city) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(CityOption));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        clickElement(CityOption);

        Select City = new Select(element);
        City.selectByVisibleText(city);
        return city;
    }

    public void verifyListAddress() {
        Select cityDropdown = new Select(driver.findElement(CityOption));
        String selectedCity = cityDropdown.getFirstSelectedOption().getText().trim();

        List<WebElement> storeItems = driver.findElements(BoxAddress);
        for (WebElement item : storeItems) {
            if (!item.isDisplayed()) continue;

            // Lấy số điện thoại
            String phone = item.findElement(By.xpath(".//span[@class='phone-number']")).getText().trim();

            // Lấy toàn bộ text của <p>
            String fullText = item.findElement(By.xpath(".//p")).getText().trim();

            // Xóa phần số điện thoại khỏi chuỗi để lấy phần địa chỉ
            String address = fullText.replace(phone, "").trim();

            if (address.toLowerCase().contains(selectedCity.toLowerCase())) {
                LogUtils.info("Có địa chỉ tại " + selectedCity);
                LogUtils.info("SĐT: " + phone);
                LogUtils.info("Địa chỉ: " + address);
            } else {
                LogUtils.warn("Địa chỉ này không thuộc " + selectedCity + ": " + address);
            }
        }
    }


    public void ClickAddress(){
        // Lấy element thẻ <p> có địa chỉ và sđt — chính xác xpath bạn cung cấp
        WebElement pElement = driver.findElement(By.xpath("//p[@xpath='1']"));

        // Tìm svg bản đồ bên trong thẻ <p> đó
        WebElement mapIcon = pElement.findElement(By.xpath(".//svg[contains(@class, 'svg-icon-map')]"));

        // Dùng Javascript click vì SVG thường không click được bằng WebElement.click()
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", mapIcon);
    }

    public List<String> getAllAddresses() {
        List<String> addressList = new ArrayList<>();
        List<WebElement> storeItems = driver.findElements(BoxAddress);

        for (WebElement item : storeItems) {
            if (!item.isDisplayed()) continue;

            String phone = item.findElement(By.xpath(".//span[@class='phone-number']")).getText().trim();
            String fullText = item.findElement(By.xpath(".//p")).getText().trim();
            String address = fullText.replace(phone, "").trim();

            addressList.add(address);
        }
        return addressList;
    }

    public List<String> getAllPhones() {
        List<String> phoneList = new ArrayList<>();
        List<WebElement> storeItems = driver.findElements(BoxAddress);
        for (WebElement item : storeItems) {
            if (!item.isDisplayed()) continue;

            try {
                String phone = item.findElement(By.xpath(".//span[@class='phone-number']")).getText().trim();
                phoneList.add(phone);
            } catch (Exception e) {
                String fullText = item.findElement(By.xpath(".//p")).getText().trim();
                String phone = fullText.split(" ")[0].trim();
                phoneList.add(phone);
            }
        }
        return phoneList;
    }


    public String getPopupAddress(){
        WebElement popupElement = wait.until(ExpectedConditions.visibilityOfElementLocated(PopupAddress));
        String popupText = popupElement.getText();
        return popupText;
    }

    public String getPopupPhone(){
        WebElement popupElement = wait.until(ExpectedConditions.visibilityOfElementLocated(PopupPhone));
        String popupText = popupElement.getText();
        return popupText;
    }

    public void SendKeyPhoneContact(String phone){
        wait.until(ExpectedConditions.elementToBeClickable(PhoneContact));
        selectCity(phone);
    }

    public void ClickButtonSumit(){
        WebElement clickElement = wait.until(ExpectedConditions.elementToBeClickable(ButtonSumit));
        clickElement.click();
    }

    public void SendKeyEvaluation(String evaluation){
        wait.until(ExpectedConditions.elementToBeClickable(EvaluationInput));
        selectCity(evaluation);
    }

    public void SendKeyStar(int star){
        wait.until(ExpectedConditions.elementToBeClickable(EvaluationStar));
        List<WebElement> stars = driver.findElements(EvaluationStar);
        for (int i = 0; i < star; i++) {
            stars.get(i).click();
        }
    }

    public void ClickEvaluationSubmit(){
        WebElement clickElement = wait.until(ExpectedConditions.elementToBeClickable(EvaluationSubmit));
        clickElement.click();
    }

    public void clickGiftBox() {
        WebElement giftBox = wait.until(ExpectedConditions.elementToBeClickable(GiftBox));
        giftBox.click();
    }

//    public String getColorName(){
//        // Lấy phần tử chứa tên màu
//        WebElement colorNameElement = driver.findElement(ColorNameAndPrice);
//        String colorName = colorNameElement.getText().trim();
//        System.out.println("Tên màu trang Product Detail: " + colorName);
//        return colorName;
//    }

    public String getColorName() {
        WebElement colorElement = driver.findElement(ColorName);
        String colorName = colorElement.getAttribute("data-name").trim();
        System.out.println("Tên màu trang Product Detail: " + colorName);
        return colorName;
    }


    public String getColorPrice() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement activeColor = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(@class, 'flex') and contains(@class, 'active')]")
        ));
        WebElement priceElement = activeColor.findElement(ColorPrice);
        String colorPrice = priceElement.getText().trim();
        System.out.println("Giá màu trong trang Product Detail: " + colorPrice);
        return colorPrice;
    }

    public String getProductPrice() {
        String price = driver.findElement(ProductPrice).getText().trim();
        System.out.println("Giá sản phẩm: " + price);
        return price;
    }

//    String colorName = getColorName();
//String colorPrice = getColorPrice();
//
//Assert.assertNotNull(colorName, "Tên màu không được null");
//Assert.assertTrue(colorPrice.contains("₫"), "Giá không đúng định dạng");
//
//System.out.println("Màu: " + colorName + " | Giá: " + colorPrice);

    public String getActiveWarranty() {
        WebElement activeOutside = driver.findElement(WarrantyActive);
        String titleOutside = activeOutside.getAttribute("data-name").trim();
        System.out.println("Bảo hành active: " + titleOutside);
        return titleOutside;
    }
}
