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

    public By ProductName = By.cssSelector(".product-name");
    public By LeftBanner = By.xpath("//a[@class='leftside-banner']");
    public By RightBanner = By.xpath("//a[@class='rightside-banner']");
    public By Banner = By.xpath("//div[contains(@class,'product-slide-box')]//a[contains(@title,'iPhone 16 series')]");
    public By QuantityEvaluation = By.xpath("//div[contains(@class,'product-meta')]//p[contains(text(),'đánh giá')]");
    public By ProductThumb = By.xpath("//div[@class='product-slide']");
    public By BasePrice = By.xpath("//p[@class='price-old ']");
    public By SalePrice = By.xpath("//p[@class='price']");
    public By WarrantyContentBH = By.xpath("//th[@class='warranty-line basic ']");
    public By WarrantyContentBH_price = By.xpath("//strong[contains(text(),'₫')]");
    public By WarrantyContent24M = By.xpath("//th[contains(@class,'warranty-line gold')]");
    public By ButtonWarrantyBH = By.xpath("//p[contains(@data-name,'BH chính hãng')]");
    public By ButtonWarranty24M = By.xpath("//div[@title='Gia hạn 24 tháng']");
    public By TitleSpecification = By.xpath("//div[contains(@class,'product-specification__title')]");
    public By BoxRating = By.xpath("//div[contains(@class,'block-rate__star')]//span[contains(text(),'đánh giá')]");
    public By GiftBox = By.xpath("//div[@class='gift-content']//p[1]");
    public By CityOption = By.xpath("//select[@name='area']");
    public By BoxAddress = By.xpath("//div[@class='area-list__item store-list__item store-select__item']");
    public By PhoneContact = By.xpath("//input[@placeholder='Tư vấn qua số điện thoại']");
    public By ButtonSumit = By.xpath("//button[@class='submit_call']");
    public By EvaluationInput = By.xpath("//textarea[@placeholder='Hãy để lại bình luận của bạn tại đây!']");
    public By EvaluationStar = By.xpath("//span[@class='star']");
    public By EvaluationSubmit = By.xpath("//button[@title='Gửi']");
    public By ButtonBuyNow = By.xpath("//div[@class='order-available']//p[@class='product-action__item add-to-cart']");
    public By VersionName = By.xpath("//div[contains(@class,'related_versions')]//div[contains(@class,'list-item') and contains(@class,'active')]//a");
    public By VersionPrice = By.xpath("//div[contains(@class,'related_versions')]//div[contains(@class,'list-item') and contains(@class,'active')]//span[contains(@class,'js-format-price')]");
    public By ColorName = By.xpath("//p[contains(@class, 'flex') and contains(@class, 'active')]");
    public By ColorPrice = By.xpath(".//span[contains(@class, 'js-format-price')]");
    public By ProductPrice = By.xpath("//p[@class='price']");
    public By WarrantyActive = By.xpath("//p[@class='active']");
    public By CustomerPromotionPrice = By.xpath("//div[@class='event-price-product event-member-price-product']");
    public By ButtonBuyNowPrice = By.xpath("//span[@class='price']");



    public Product_Detail_Page_Cb(WebDriver driver) {
        super(driver);
        Product_Detail_Page_Cb.driver = driver;
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        validateUIHelper = new ValidateUIHelper(driver);
    }


    public void selectProductOptions() throws InterruptedException {
        LogUtils.info("Chọn phiên bản");
        selectVersionProduct("512GB");
        validateUIHelper.waitForPageLoaded();
        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='75%'");
        LogUtils.info("Chọn màu sắc");
        selectColorProduct("Natural");

        Thread.sleep(1000);
    }

    public Checkout_Page_Cb clickBuyNowToOpenPopup() throws InterruptedException {
        Thread.sleep(1000);

        ((JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll('iframe').forEach(f => f.style.display = 'none');"
        );

        WebElement Buynow = driver.findElement(ButtonBuyNow);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", Buynow);

        Thread.sleep(500);
        js.executeScript("arguments[0].click();", Buynow);
        js.executeScript("document.getElementById('popup-modal').style.display = 'inline-block';");
        LogUtils.info("Đã click được vào button 'Buy Now'");
        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='75%'");
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
        WebElement element = driver.findElement(By.xpath("//a[@title='512GB']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        element.click();
    }

    public void selectColorProduct(String color) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement selectColor = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@title='Natural']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", selectColor);
        selectColor.click();
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

            try {
                String phone = item.findElement(By.xpath(".//span[@class='phone-number']")).getText().trim();
                List<WebElement> pTags = item.findElements(By.xpath(".//p"));
                if (pTags.isEmpty()) continue;

                String address = pTags.get(0).getText().replace(phone, "").trim();

                if (address.toLowerCase().contains(selectedCity.toLowerCase())) {
                    LogUtils.info("Có địa chỉ tại " + selectedCity + " - SĐT: " + phone + " - Địa chỉ: " + address);
                } else {
                    LogUtils.warn("Không thuộc " + selectedCity + ": " + address);
                }
            } catch (Exception e) {
                LogUtils.warn("Lỗi khi xử lý item: " + e.getMessage());
            }
        }
    }

    public void verifyAllAddressesInCity(String expectedCity) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement allAddresses1 = driver.findElement(By.xpath("//div[contains(@class, 'area-list__item')]//p"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", allAddresses1);
        List<WebElement> allAddresses = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//div[contains(@class, 'area-list__item')]//p")
        ));

        boolean allValid = true;
        int countVisible = 0;

        for (WebElement address : allAddresses) {
            if (!address.isDisplayed()) {
                continue;
            }

            countVisible++;
            String addressText = address.getText().trim();
            LogUtils.info("Địa chỉ hiển thị: " + addressText);

            if (!addressText.toLowerCase().contains(expectedCity.toLowerCase())) {
                LogUtils.info("Địa chỉ không chứa '" + expectedCity + "': " + addressText);
                allValid = false;
            }
        }

        if (countVisible == 0) {
            LogUtils.info("Không có địa chỉ nào đang hiển thị.");
            return;
        }

        if (allValid) {
            LogUtils.info("Tất cả địa chỉ đang hiển thị đều thuộc " + expectedCity + ".");
        }
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

    public String getColorName() {
        WebElement colorElement = driver.findElement(ColorName);
        String colorName = colorElement.getAttribute("data-name").trim();
        System.out.println("Tên màu trang Product Detail: " + colorName);
        return colorName;
    }


    public String getColorPrice() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement activeColor = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'list-item') and contains(@class,'active')]")
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

    public String getActiveWarranty() {
        WebElement activeOutside = driver.findElement(WarrantyActive);
        String titleOutside = activeOutside.getAttribute("data-name").trim();
        System.out.println("Bảo hành active: " + titleOutside);
        return titleOutside;
    }

    public String getTitleSpecification() {
        WebElement titleSpecification = driver.findElement(TitleSpecification);
        String title = titleSpecification.getText().trim();
        System.out.println("Tiêu đề thông số kỹ thuật: " + title);
        return title;
    }

    public String getWarrantyContentBH_price() {
        WebElement contentBH_price = driver.findElement(WarrantyContentBH_price);
        String contentPrice = contentBH_price.getText().trim();
        System.out.println("Nội dung bảo hành (thô): " + contentPrice);

        // Chuẩn hóa: bỏ dấu chấm, phẩy, khoảng trắng và ký tự ₫
        String normalizedPrice = contentPrice.replaceAll("[^\\d]", "");
        System.out.println("Nội dung bảo hành (chuẩn hóa): " + normalizedPrice);

        return normalizedPrice;
    }

    public String getCustomerService() {
        WebElement customerService = driver.findElement(CustomerPromotionPrice);
        String contentPrice = customerService.getText().trim();
        System.out.println("Giá khuyến mãi cho khách hàng: " + contentPrice);
        return contentPrice;
    }

    public int getReviewCount() {
        WebElement reviewText = driver.findElement(QuantityEvaluation);
        String rawText = reviewText.getText();
        int reviewCount = Integer.parseInt(rawText.replaceAll("[^0-9]", ""));
        LogUtils.info("Số lượng đánh giá: " + reviewCount);
        return reviewCount;
    }

    public String getActiveVersionName() {
        WebElement activeVersion = driver.findElement(VersionName);
        String versionName = activeVersion.getAttribute("title").trim();  // hoặc aria-label
        LogUtils.info("Tên phiên bản đang được chọn: " + versionName);
        return versionName;
    }

    public String getActiveVersionPrice() {
        WebElement priceElement = driver.findElement(VersionPrice);
        String priceText = priceElement.getText().trim();
        LogUtils.info("Giá phiên bản đang được chọn (thô): " + priceText);

        // Chuẩn hóa: bỏ dấu chấm, phẩy, khoảng trắng và ký tự ₫
        String normalizedPrice = priceText.replaceAll("[^\\d]", "");
        LogUtils.info("Giá phiên bản đang được chọn (chuẩn hóa): " + normalizedPrice);

        return normalizedPrice;
    }

    public String getButtonBuyNowPrice() {
        WebElement priceElement = driver.findElement(ButtonBuyNowPrice);
        String priceText = priceElement.getText().trim();
        LogUtils.info("Giá mua ngay: " + priceText);
        return priceText;
    }

    public int getRatingCount() {
        WebElement ratingElement = driver.findElement(BoxRating);
        String text = ratingElement.getText(); // (53 đánh giá và nhận xét)
        int count = Integer.parseInt(text.replaceAll("[^0-9]", ""));
        LogUtils.info("Số lượng đánh giá (block-rate): " + count);
        return count;
    }



}
