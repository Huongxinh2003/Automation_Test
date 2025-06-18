package com.cellphoneS.pages;

import com.helpers.ValidateUIHelper;
import com.ultilities.LogUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Product_Detail_Page extends ValidateUIHelper {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private JavascriptExecutor js;


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
    public static By MainThumbnail = By.xpath("//div[@class='box-ksp is-flex is-flex-direction-row']");
    public By swiperThumbnailNext = By.xpath("//div[@class='swiper-button-next button__view-gallery-next']//div[@class='icon']");
    public By swiperThumbnailPrev = By.xpath("//div[@class='swiper-button-prev button__view-gallery-prev']");
    public static By ProductThumbnailSmall1 = By.xpath("//img[@alt='/i/p/iphone-16-pro-max-3.png2 - thumb']");
    public By ProductThumbnailSmall2 = By.xpath("//img[@alt='/i/p/iphone-16-pro-max-4.png3 - thumb']");
    public By CityOption = By.xpath("//div[@class='box-on-stock-option button__change-province']");
    public By DistrictOption = By.xpath("//select[@id='districtOptions']");
    public By SelectCity = By.xpath("//a[contains(text(),'Đà Nẵng')]");
    public By SelectDistrict = By.xpath("//option[@value='40']");
    public By AddressStore = By.xpath("//div[@class='box-on-stock-item']");
    public By BuyNow = By.xpath("//button[@class='button-desktop button-desktop-order is-flex is-justify-content-center is-align-items-center']");
    public By AddToCart = By.xpath("//button[@class='button-desktop button-add-to-cart']");
    public By InstallmentOption = By.xpath("//button[@class='button-desktop button-desktop-installment is-flex is-justify-content-center is-align-items-center']");
    public By InstallmentOption2 = By.xpath("////body/div[@id='__nuxt']/div[@id='__layout']/div[@id='layout-desktop']/div[@class='cps-container cps-body']/div/div[@id='productDetailV2']/div[@class='cps-block-order-button-desktop show']/div[@class='cta-action']/div[@class='installment-section']/div[@class='popup-installment show']/div[1]");
    public By InstallmentOption3 = By.xpath("//body/div[@id='__nuxt']/div[@id='__layout']/div[@id='layout-desktop']/div[@class='cps-container cps-body']/div/div[@id='productDetailV2']/div[@class='cps-block-order-button-desktop show']/div[@class='cta-action']/div[@class='installment-section']/div[@class='popup-installment show']/div[1]");
    public By ProductInfo = By.xpath("//div[@class='cta-product-info']");
    public By StickyProductBar = By.xpath("//div[@class='cps-block-order-button-desktop show']");
    public By LinkShowMore = By.xpath("//div[@class='btn-show-more']");
    public By ButtonEvaluate = By.xpath("//button[contains(text(),'Viết đánh giá')]");
    public By EvaluateStar = By.xpath("//div[@class='modal-review-star is-flex is-justify-content-space-between my-3 review-all']//div[4]//div[1]//*[name()='svg']//*[name()='path' and contains(@d,'M381.2 150')]");
    public By EvaluatePerformance = By.xpath("//div[@class='modal modal-review is-active']//div[4]//div[1]//div[4]//div[1]//*[name()='svg']");
    public By EvaluateBatteryLife = By.xpath("//div[@class='boxReview']//div[5]//div[1]//div[3]//div[1]//*[name()='svg']//*[name()='path' and contains(@d,'M381.2 150')]");
    public By EvaluateCamera = By.xpath("//div[6]//div[1]//div[4]//div[1]//*[name()='svg']//*[name()='path' and contains(@d,'M381.2 150')]");
    public By EvaluateComment = By.xpath("//textarea[@placeholder='Xin mời chia sẻ một số cảm nhận về sản phẩm (nhập tối thiểu 15 kí tự)']");
    public By EvaluateImage = By.xpath("//label[contains(text(),'Thêm hình ảnh')]");
    public By EvaluateButton = By.xpath("//button[contains(text(),'GỬI ĐÁNH GIÁ')]");
    public By Question = By.xpath("//textarea[@placeholder='Viết câu hỏi của bạn tại đây']");
    public By QuestionButton = By.xpath("//button[contains(text(),'Gửi câu hỏi')]");

    public Product_Detail_Page(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isTitleProductDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(TitleProduct));
        return isElementDisplayed(TitleProduct);
    }

    // Lấy tiêu đề trang
    public String getTitleProduct() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(TitleProduct));
        return driver.getTitle();
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

    // Lấy src của ảnh thumbnail lớn hiện tại
    public static String getMainThumbnailSrc() {
        WebElement mainImg = wait.until(ExpectedConditions.visibilityOfElementLocated(MainThumbnail));
        return mainImg.getAttribute("src");
    }


    // Click vào thumbnail nhỏ (truyền locator vào)
    public static void clickSmallThumbnail(By smallThumbnailLocator) {
        WebElement thumb = wait.until(ExpectedConditions.elementToBeClickable(smallThumbnailLocator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", thumb);

    }

    // Click thumbnail nhỏ rồi chờ ảnh lớn thay đổi
    public static void clickThumbnailAndWaitForMainChange(By smallThumbnailLocator) {
        String srcBefore = getMainThumbnailSrc();
        clickSmallThumbnail(smallThumbnailLocator);
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeToBe(MainThumbnail, "src", srcBefore)));
    }

    // Các getter để lấy locator thumbnail nhỏ (nếu cần)
    public static By getSmallThumbnail1() {
        return ProductThumbnailSmall1;
    }
    public static By getSmallThumbnail2() {
        return ProductPrice;
    }

//    public boolean isMainImageChangedAfterClickingThumbnail() {
//        wait.until(ExpectedConditions.visibilityOfElementLocated(mainImage));
//        WebElement oldImage = driver.findElement(mainImage);
//        String oldSrc = oldImage.getAttribute("src");
//
//        List<WebElement> thumbnails = driver.findElements(thumbnailImages);
//        if (thumbnails.size() < 2) {
//            LogUtils.warn("Không đủ ảnh thumbnail để kiểm tra.");
//            return false;
//        }
//
//        // Click thumbnail thứ 2 (khác ảnh chính mặc định)
//        thumbnails.get(1).click();
//        LogUtils.info("Đã click thumbnail thứ 2.");
//
//        // Đợi ảnh chính thay đổi
//        wait.until(ExpectedConditions.stalenessOf(oldImage)); // đợi ảnh cũ không còn trong DOM
//
//        WebElement newImage = driver.findElement(mainImage);
//        String newSrc = newImage.getAttribute("src");
//        LogUtils.info("Src cũ: " + oldSrc);
//        LogUtils.info("Src mới: " + newSrc);
//
//        return !oldSrc.equals(newSrc);
//    }

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


    public void ClickProductThumbnail() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(ProductThumbnail));
        clickElement(ProductThumbnail);
    }

    public boolean isProductThumbnailDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(ProductThumbnail));
        return isElementDisplayed(ProductThumbnail);
    }

    public void ClickSwiperThumbnail() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(swiperThumbnailNext));
        clickElement(swiperThumbnailNext);
        clickElement(swiperThumbnailNext);
        wait.until(ExpectedConditions.visibilityOfElementLocated(swiperThumbnailPrev));
        clickElement(swiperThumbnailPrev);
        clickElement(swiperThumbnailPrev);
    }


    public void ClickCityOption() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(CityOption));
        clickElement(CityOption);
        clickElement(SelectCity);
    }

    public void ClickDistrictOption() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(DistrictOption));
        clickElement(DistrictOption);
        clickElement(SelectDistrict);
    }

    public boolean isCountStoreDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(CountStore));
        return isElementDisplayed(CountStore);
    }

    public boolean isAddressStoreDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(AddressStore));
        return isElementDisplayed(AddressStore);
    }

    public void ClickBuyNow() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(BuyNow));
        clickElement(BuyNow);
    }

    public void ClickAddToCart() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(AddToCart));
        clickElement(AddToCart);
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

    public boolean isProductInfoDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(ProductInfo));
        return isElementDisplayed(ProductInfo);
    }

    public boolean isStickyProductBarDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(StickyProductBar));
        return isElementDisplayed(StickyProductBar);
    }

    public void ClickLinkShowMore() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(LinkShowMore));
        clickElement(LinkShowMore);
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(EvaluateComment));
        sendKeys(EvaluateComment, comment);
    }

    public void ClickEvaluateImage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(EvaluateImage));
        clickElement(EvaluateImage);
    }

    public void ClickEvaluateButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(EvaluateButton));
        clickElement(EvaluateButton);
    }

    public void InputQuestion(String question) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Question));
        sendKeys(Question, question);
    }

    public void ClickQuestionButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(QuestionButton));
        clickElement(QuestionButton);
    }

}
