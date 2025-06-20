package com.cellphoneS.pages;

import com.helpers.ValidateUIHelper;
import com.ultilities.LogUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Product_Detail_Page extends ValidateUIHelper {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static JavascriptExecutor js;
    private static ValidateUIHelper validateUIHelper;


    //public By ProductCard = By.xpath("//div[@class='product-list-filter is-flex is-flex-wrap-wrap']//div[1]//div[1]//a[1]");
    public By TitleProduct = By.xpath("//div[@class='box-product-name']");
    public By FavoriteProduct = By.xpath("//div[@class='box-bottom-item']//div[@id='wishListBtn']");
    public By BoxRating = By.xpath("//div[@class='box-rating']");
    public By ColorPrice = By.xpath("//div[@class='box-product-variants']");
    public By CountStore = By.xpath("//span[@class='count']");
    public static By ProductPrice = By.xpath("//div[@class='box-product-price']");
    public By Sale_price = By.xpath("//div[@class='sale-price']");
    public By BasePrice = By.xpath("//div[@class='is-flex is-align-items-center']//del[@class='base-price']");
    public By PriceStickyBar = By.xpath("//div[@class='cta-product-price']");
    public By TitleProductBar = By.xpath("//div[@class='cta-product-info']");
    public By ProductThumbnail = By.xpath("//div[@class='box-gallery']");
    public static By MainThumbnail = By.xpath("//div[contains(@class, 'gallery-slide') and contains(@class, 'gallery-top')]//div[contains(@class,'swiper-slide-active')]//img");
    public static By SliderThumbnail =  By.xpath("//div[contains(@class,'gallery-thumbs')]//div[contains(@class,'swiper-slide-thumb-active') and not(contains(@class,'ksp-thumbs'))]//img");
    public static By swiperThumbnailNext = By.xpath("//div[@class='swiper-button-next button__view-gallery-next']//div[@class='icon']");
    public static By swiperThumbnailPrev = By.xpath("//div[@class='swiper-button-prev button__view-gallery-prev']");
    public static By ProductThumbnailSmall1 = By.xpath("//img[@alt='/i/p/iphone-16-pro-max-3.png2 - thumb']");
    public static By ProductThumbnailSmall2 = By.xpath("//img[@alt='/i/p/iphone-16-pro-max-4.png3 - thumb']");
    public static By CityOption = By.xpath("//div[@class='box-on-stock-option button__change-province']");
    public static By DistrictOption = By.xpath("//select[@id='districtOptions']");
    public static By SelectCity = By.xpath("//ul[@class='menu-list']");
    public static By SelectDistrict = By.xpath("//option[@value='40']");
    public static By BoxAddress = By.xpath("//div[@class='box-on-stock-address']");
    public By BuyNow = By.xpath("//button[@class='button-desktop button-desktop-order is-flex is-justify-content-center is-align-items-center']");
    public By AddToCart = By.xpath("//button[@class='button-desktop button-add-to-cart']");
    public By InstallmentOption = By.xpath("//button[@class='button-desktop button-desktop-installment is-flex is-justify-content-center is-align-items-center']");
    public By InstallmentOption2 = By.xpath("//body/div[@id='__nuxt']/div[@id='__layout']/div[@id='layout-desktop']/div[@class='cps-container cps-body']/div/div[@id='productDetailV2']/div[@class='cps-block-order-button-desktop show']/div[@class='cta-action']/div[@class='installment-section']/div[@class='popup-installment show']/div[1]");
    public static By activeTabBtn = By.xpath("//button[@class='tab-item active']");
    public By InstallmentOption3 = By.xpath("//body/div[@id='__nuxt']/div[@id='__layout']/div[@id='layout-desktop']/div[@class='cps-container cps-body']/div/div[@id='productDetailV2']/div[@class='cps-block-order-button-desktop show']/div[@class='cta-action']/div[@class='installment-section']/div[@class='popup-installment show']/div[1]");
    public By ButtonEvaluate = By.xpath("//button[contains(text(),'Viết đánh giá')]");
    public By EvaluateStar = By.xpath("//div[@class='modal-review-star is-flex is-justify-content-space-between my-3 review-all']//div[4]//div[1]//*[name()='svg']//*[name()='path' and contains(@d,'M381.2 150')]");
    public By EvaluatePerformance = By.xpath("//div[@class='modal modal-review is-active']//div[4]//div[1]//div[4]//div[1]//*[name()='svg']");
    public By EvaluateBatteryLife = By.xpath("//div[@class='boxReview']//div[5]//div[1]//div[3]//div[1]//*[name()='svg']//*[name()='path' and contains(@d,'M381.2 150')]");
    public By EvaluateCamera = By.xpath("//div[6]//div[1]//div[4]//div[1]//*[name()='svg']//*[name()='path' and contains(@d,'M381.2 150')]");
    public By EvaluateComment = By.xpath("//textarea[@placeholder='Xin mời chia sẻ một số cảm nhận về sản phẩm (nhập tối thiểu 15 kí tự)']");
    public By EvaluateImage = By.xpath("//input[@id='image']");
    public By EvaluateButton = By.xpath("//button[contains(text(),'GỬI ĐÁNH GIÁ')]");
    public By EvaluateCount = By.xpath("//p[@class='boxReview-score__count']");
    public static By StoreProduct = By.xpath("//div[@class='box-on-stock-stores']");


    public Product_Detail_Page(WebDriver driver) {
        super(driver);
        Product_Detail_Page.driver = driver;
        js = (JavascriptExecutor) driver;
        this.validateUIHelper = new ValidateUIHelper(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public Cart_Page OpenCartPage() {
        LogUtils.info("Chọn phiên bản");
        selectVersionProduct("512GB");
        LogUtils.info("Chờ trang cập nhật lại");
        validateUIHelper.waitForPageLoaded();
        LogUtils.info("Chọn màu sắc");
        selectColorProduct("Titan Đen");
        LogUtils.info("Chờ trang cập nhật lại");
        validateUIHelper.waitForPageLoaded();

        WebElement Buynow = wait.until(ExpectedConditions.visibilityOfElementLocated(BuyNow));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", Buynow);
        clickElement(BuyNow);
        return new Cart_Page(driver);
    }

    public boolean isTitleProductDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(TitleProduct));
        return isElementDisplayed(TitleProduct);
    }

    // Lấy tiêu đề sản phẩm
    public String getTitleProduct() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(TitleProduct));
        return driver.getTitle();
    }

    // Lấy tên thành phố
    public String getCityName() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(CityOption));
        return getText(CityOption);
    }

    public String getDistrictName() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(DistrictOption));
        return getText(DistrictOption);
    }

    public void isFavoriteProductDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(FavoriteProduct));
        isElementDisplayed(FavoriteProduct);
    }

    public void ClickFavoriteProduct() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(FavoriteProduct));
        clickElement(FavoriteProduct);
    }

    public void DeleteFavoriteProduct() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(FavoriteProduct));
        clickElement(FavoriteProduct);
    }

    public void selectVersionProduct(String version) {
        List<WebElement> versionOptions = driver.findElements(By.xpath("//a[@class='item-linked button__link linked-1 false']"));
        for (WebElement option : versionOptions) {
            String text = option.getText().replaceAll("\\s+", "").toLowerCase();
            if (text.contains(version.replaceAll("\\s+", "").toLowerCase())) {
                option.click();
                LogUtils.info("Đã chọn phiên bản: " + option.getText());
                return;
            }
        }
        throw new RuntimeException("Không tìm thấy phiên bản có tên: " + version);
    }

    public void selectColorProduct(String color) {
        List<WebElement> colorOptions = driver.findElements(By.xpath("//a[@title='Titan Đen']"));
        for (WebElement option : colorOptions) {
            String text = option.getText().replaceAll("\\s+", "").toLowerCase();
            if (text.contains(color.replaceAll("\\s+", "").toLowerCase())) {
                option.click();
                LogUtils.info("Đã chọn màu sắc: " + option.getText());
                return;
            }
        }
        throw new RuntimeException("Không tìm thấy màu sắc có tên: " + color);
    }

    // Lấy src ảnh lớn
    public static String getMainImageSrc() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(MainThumbnail)).getAttribute("src");
    }

    // Lấy src thumbnail nhỏ đang active
    public static String getActiveSmallThumbnailSrc() {
        WebElement thumb = wait.until(ExpectedConditions.presenceOfElementLocated(SliderThumbnail));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", thumb);
        wait.until(ExpectedConditions.visibilityOf(thumb));
        return thumb.getAttribute("src");
    }

    // Click nút Next → đợi ảnh lớn thay đổi
    public static void clickSwiperNextAndWaitForMainImageChange(String previousSrc) {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(swiperThumbnailNext));
        btn.click();
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeToBe(MainThumbnail, "src", previousSrc)));
    }

    // Tiện ích lấy tên file ảnh từ URL
    public static String getFileNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    // Click ảnh nhỏ bất kỳ
    public static void clickThumbnail(By thumbnailBy) {
        WebElement thumb = wait.until(ExpectedConditions.elementToBeClickable(thumbnailBy));
        thumb.click();
    }

    // In src ảnh lớn
    public static void printMainImageSrc() {
        WebElement img = wait.until(ExpectedConditions.visibilityOfElementLocated(MainThumbnail));
//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", img);
        String src = img.getAttribute("src");
        System.out.println("Thumnail hiện tại: " + src);
    }

    public String getBoxRating() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(BoxRating));
        return getText(BoxRating);
    }

    public String getColorPrice() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(ColorPrice));
        return getText(ColorPrice);
    }

    public String getProductPrice() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(ProductPrice));
        return getText(ProductPrice);
    }

    public String getSalePrice() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Sale_price));
        return getText(Sale_price);
    }

    public String getBasePrice() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(BasePrice));
        return getText(BasePrice);
    }

    public String getPriceStickyBar() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(PriceStickyBar));
        return getText(PriceStickyBar);
    }

    public String getTitleStickyBar() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(TitleProductBar));
        return getText(TitleProductBar);
    }

    public String getProductThumbnail() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(ProductThumbnail));
       return getText(ProductThumbnail);
    }

    public String getCountStore() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(CountStore));
        return getText(CountStore);
    }

    public int calculateDiscountPercentage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Lấy giá sale (giá sau khi giảm)
        WebElement salePriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(Sale_price));
        String salePriceText = salePriceElement.getText().replace(".", "").replace("đ", "").trim();
        int salePrice = Integer.parseInt(salePriceText);

        // Lấy giá gốc (giá trước khi giảm)
        WebElement basePriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(BasePrice));
        String basePriceText = basePriceElement.getText().replace(".", "").replace("đ", "").trim();
        int basePrice = Integer.parseInt(basePriceText);

        // Tính phần trăm giảm
        int discountPercentage = (int) Math.round(((double)(basePrice - salePrice) / basePrice) * 100);

        LogUtils.info("Giá gốc: " + basePrice + " | Giá sale: " + salePrice + " | Giảm: " + discountPercentage + "%");
        return discountPercentage;
    }

    public static void ScrollToElement() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(StoreProduct));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    public static void ClickCity() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(CityOption));
        clickElement(CityOption);
    }

    public static void ClickSelectCity(String city) {
        // Tạo xpath cho li chứa tên thành phố
        String cityXpath = "//ul[@class='menu-list']/li[normalize-space()='" + city + "']";

        // Đợi menu list hiển thị
        WebElement menuList = wait.until(ExpectedConditions.visibilityOfElementLocated(SelectCity));

        // Đợi và click thành phố
        WebElement cityElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(cityXpath)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cityElement);
        cityElement.click();
    }

    // Kiểm tra box có chứa tên thành phố đã chọn không
    public static boolean isAddressBoxContainsCity(String cityName) {
        WebElement box = wait.until(ExpectedConditions.visibilityOfElementLocated(BoxAddress));
        return box.getText().toLowerCase().contains(cityName.toLowerCase());
    }

    // lấy nội dung trong box address
    public static String getAddressText() {
        WebElement box = wait.until(ExpectedConditions.visibilityOfElementLocated(BoxAddress));
        return box.getText();
    }

    public static String ClickSelectDistrict(String districtName) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(DistrictOption));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
//        clickElement(DistrictOption);
//        WebElement selectDistrict = wait.until(ExpectedConditions.visibilityOfElementLocated(SelectDistrict));
        Select District = new Select(element);
        District.selectByVisibleText(districtName);
        return districtName;
    }

    public static boolean isAddressBoxContainsCityAndDistrict(String cityName, String districtName) {
        WebElement box = wait.until(ExpectedConditions.visibilityOfElementLocated(BoxAddress));
        String boxText = box.getText().toLowerCase();

        return boxText.contains(cityName.toLowerCase()) && boxText.contains(districtName.toLowerCase());
    }

    // lấy nội dung trong box address
    public static String getAddressText2() {
        WebElement box = wait.until(ExpectedConditions.visibilityOfElementLocated(BoxAddress));
        return box.getText();
    }


    public void ClickBuyNow() {
        WebElement Buynow = wait.until(ExpectedConditions.visibilityOfElementLocated(BuyNow));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", Buynow);
        clickElement(BuyNow);
    }

    public void ClickAddToCart() {
        WebElement ClickAddToCart = wait.until(ExpectedConditions.visibilityOfElementLocated(AddToCart));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", ClickAddToCart);
        clickElement(AddToCart);
    }

    public static String getActiveTabText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(activeTabBtn)).getText();
    }

    public void ClickInstallmentOption() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(InstallmentOption));
        clickElement(InstallmentOption);
    }

    public void ClickInstallmentOption2() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(InstallmentOption2));
        clickElement(InstallmentOption2);
    }

    public void ClickInstallmentOption3() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(InstallmentOption3));
        clickElement(InstallmentOption3);
    }

    public void ClickButtonEvaluate() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(ButtonEvaluate));
        clickElement(ButtonEvaluate);
    }

    public void ClickEvaluateStar() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(EvaluateStar));
        clickElement(EvaluateStar);
    }

    public void ClickEvaluatePerformance() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(EvaluatePerformance));
        clickElement(EvaluatePerformance);
    }

    public void ClickEvaluateBatteryLife() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(EvaluateBatteryLife));
        clickElement(EvaluateBatteryLife);
    }

    public void ClickEvaluateCamera() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(EvaluateCamera));
        clickElement(EvaluateCamera);
    }

    public void InputEvaluateComment(String comment) {
        WebElement Comment = wait.until(ExpectedConditions.visibilityOfElementLocated(EvaluateComment));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", Comment);
        sendKeys(EvaluateComment, comment);
    }

    public void ClickEvaluateImage(String imagePath) {
        WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(EvaluateImage));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", input);
        input.sendKeys(imagePath);
    }

    public void ClickEvaluateButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(EvaluateButton));
        clickElement(EvaluateButton);
    }

    public String getCountEvaluateProduct() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(EvaluateCount));
        return getText(EvaluateCount);
    }

}
