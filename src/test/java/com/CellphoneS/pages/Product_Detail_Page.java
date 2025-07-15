package com.CellphoneS.pages;

import com.helpers.ValidateUIHelper;
import com.ultilities.logs.LogUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class Product_Detail_Page extends ValidateUIHelper {
    private static ValidateUIHelper validateUIHelper;

    public By TitleProduct = By.xpath("//div[@class='box-product-name']");
    public By FavoriteProduct = By.xpath("//button[@id='wishListBtn']");
    public By BoxRating = By.xpath("//div[@class='box-rating']");
    public By ColorPrice = By.xpath("//div[@class='box-product-variants']");
    public By CountStore = By.xpath("//div[@class='box-on-stock-count']//span[@class='count']");
    public By ProductPrice = By.xpath("//div[@class='box-product-price']");
    public By Sale_price = By.xpath("//div[@class='sale-price']");
    public By BasePrice = By.xpath("//div[@class='is-flex is-align-items-center']//del[@class='base-price']");
    public By PriceStickyBar = By.xpath("//div[@class='cta-product-price']");
    public By TitleProductBar = By.xpath("//div[@class='cta-product-info']");
    public By ProductThumbnail = By.xpath("//div[@class='box-gallery']");
    public By MainThumbnail = By.xpath("//div[contains(@class, 'gallery-slide') and contains(@class, 'gallery-top')]//div[contains(@class,'swiper-slide-active')]//img");
    public By CityOption = By.xpath("//div[@class='box-on-stock-option button__change-province']");
    public By DistrictOption = By.xpath("//select[@id='districtOptions']");
    public By SelectCity = By.xpath("//ul[@class='menu-list']");
    public By BoxAddress = By.xpath("//div[@class='box-on-stock-address']");
    public By BuyNow = By.xpath("//button[@class='button-desktop button-desktop-order is-flex is-justify-content-center is-align-items-center']");
    public By AddToCart = By.xpath("//button[@class='button-desktop button-add-to-cart']");
    public By InstallmentOption = By.xpath("//button[@class='button-desktop button-desktop-installment is-flex is-justify-content-center is-align-items-center']");
    public By InstallmentOption2 = By.xpath("//body/div[@id='__nuxt']/div[@id='__layout']/div[@id='layout-desktop']/div[@class='cps-container cps-body']/div/div[@id='productDetailV2']/div[@class='cps-block-order-button-desktop show']/div[@class='cta-action']/div[@class='installment-section']/div[@class='popup-installment show']/div[1]");
    public By activeTabBtn = By.xpath("//button[@class='tab-item active']");
    public By InstallmentOption3 = By.xpath("//body/div[@id='__nuxt']/div[@id='__layout']/div[@id='layout-desktop']/div[@class='cps-container cps-body']/div/div[@id='productDetailV2']/div[@class='cps-block-order-button-desktop show']/div[@class='cta-action']/div[@class='installment-section']/div[@class='popup-installment show']/div[1]");
    public By ButtonEvaluate = By.xpath("//button[contains(text(),'Viết đánh giá')]");
    public By EvaluateStar = By.xpath("(//div[@icon='star'])[3]");
    public By EvaluatePerformance = By.xpath("(//div[@icon='star'])[5]");
    public By EvaluateBatteryLife = By.xpath("(//div[@icon='star'])[2]");
    public By EvaluateCamera = By.xpath("(//div[@icon='star'])[4]");
    public By EvaluateComment = By.xpath("//textarea[@placeholder='Xin mời chia sẻ một số cảm nhận về sản phẩm (nhập tối thiểu 15 kí tự)']");
    public By EvaluateImage = By.xpath("//input[@id='image']");
    public By EvaluateButton = By.xpath("//button[contains(text(),'GỬI ĐÁNH GIÁ')]");
    public By EvaluateCount = By.xpath("//p[@class='boxReview-score__count']");
    public By StoreProduct = By.xpath("//div[@class='box-on-stock-stores']");
    public By ToastMessage = By.xpath("//div[@class='toasted toasted-primary success']");

    public Product_Detail_Page(WebDriver driver) {
        super(driver);
        this.driver = driver;
        js = (JavascriptExecutor) driver;
        validateUIHelper = new ValidateUIHelper(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public Cart_Page OpenCartPage() {
        LogUtils.info("Chọn phiên bản");
        selectVersionProduct("1TB");
        LogUtils.info("Chờ trang cập nhật lại");
        validateUIHelper.waitForPageLoaded();
        LogUtils.info("Chọn màu sắc");
        selectColorProduct("Titan Đen");
        LogUtils.info("Chờ trang cập nhật lại");
        validateUIHelper.waitForPageLoaded();

        WebElement Buynow = wait.until(ExpectedConditions.visibilityOfElementLocated(BuyNow));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", Buynow);
        clickElement(BuyNow);
        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='75%'");
        return new Cart_Page(driver);
    }


    public boolean isTitleProductDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(TitleProduct));
        return isElementDisplayed(TitleProduct);
    }

    // Lấy tiêu đề trang
    public String getTitleProduct() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(TitleProduct));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        String titleText = (String) js.executeScript("return arguments[0].textContent.trim();", titleElement);

        return titleText;
    }

    // Lấy tên thành phố
    public String getCityName() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(CityOption));
        return getText(CityOption);
    }

    public String getDistrictName() {
        WebElement select = driver.findElement(DistrictOption);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (String) js.executeScript("return arguments[0].selectedOptions[0].textContent.trim();", select);
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
        List<WebElement> options = driver.findElements
                (By.xpath("//div[@class='list-linked']//a"));

        String oldTitle = getTitleProduct();

        for (WebElement option : options) {
            String optionText = option.getText().replaceAll("\\s+", "").toLowerCase();
            String expectedText = version.replaceAll("\\s+", "").toLowerCase();

            if (optionText.contains(expectedText)) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                LogUtils.info("Đã chọn phiên bản: " + option.getText());


                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(driver -> {
                    String newTitle = getTitleProduct();
                    return !newTitle.equals(oldTitle);
                });
                return;
            }
        }

        throw new RuntimeException("Không tìm thấy phiên bản: " + version);
    }


    public void selectColorProduct(String color) {
        String normalizedColor = color.replaceAll("\\s+", "").toLowerCase();

        List<WebElement> colorOptions = driver.findElements(By.xpath("//ul[@class='list-variants']//a[contains(@class, 'button__change-color')]"));
        for (int i = 0; i < colorOptions.size(); i++) {
            try {
                WebElement option = driver.findElements(By.xpath("//ul[@class='list-variants']//a[contains(@class, 'button__change-color')]")).get(i);

                String text = option.getText().replaceAll("\\s+", "").toLowerCase();
                if (text.contains(normalizedColor)) {
                    wait.until(ExpectedConditions.elementToBeClickable(option));
                    js.executeScript("arguments[0].click();", option);
                    LogUtils.info("Đã chọn màu sắc: " + option.getText());
                    return;
                }
            } catch (StaleElementReferenceException e) {
                LogUtils.warn("Phần tử màu bị stale, thử lại lần nữa...");
                i--;
            }
        }
        throw new RuntimeException("Không tìm thấy màu sắc có tên: " + color);
    }



    // Lấy src ảnh lớn
    public String getMainImageSrc() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(MainThumbnail)).getAttribute("src");
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
        WebElement priceElement = wait.until(ExpectedConditions.presenceOfElementLocated(Sale_price));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String rawText = (String) js.executeScript("return arguments[0].textContent.trim();", priceElement);
        return normalizePriceText(rawText);
    }

    public String getBasePrice() {
        WebElement priceElement = wait.until(ExpectedConditions.presenceOfElementLocated(BasePrice));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String rawText = (String) js.executeScript("return arguments[0].textContent.trim();", priceElement);
        return normalizePriceText(rawText);
    }

    private String normalizePriceText(String rawText) {
        return rawText.replaceAll("[^0-9]", "");
    }

    public String getPriceStickyBar() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(PriceStickyBar));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        String priceText = (String) js.executeScript("return arguments[0].textContent.trim();", priceElement);

        return priceText;
    }

    public String getTitleStickyBar() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(TitleProductBar));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        String titleText = (String) js.executeScript("return arguments[0].textContent.trim();", titleElement);

        return titleText;
    }

    public String getProductThumbnail() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(ProductThumbnail));
       return getText(ProductThumbnail);
    }

    public String getCountStore() {
        String countStore = driver.findElement(CountStore).getText().trim();
        return countStore;
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

    public void ScrollToElement() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(StoreProduct));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    public void ClickCity() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(CityOption));
        clickElement(CityOption);
    }

    public void ClickSelectCity(String city) {
        String cityXpath = "//ul[@class='menu-list']/li[normalize-space()='" + city + "']";
        WebElement menuList = wait.until(ExpectedConditions.visibilityOfElementLocated(SelectCity));

        WebElement cityElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(cityXpath)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cityElement);
        cityElement.click();
    }

    // Kiểm tra box có chứa tên thành phố đã chọn không
    public boolean isAddressBoxContainsCity(String cityName) {
        WebElement box = wait.until(ExpectedConditions.visibilityOfElementLocated(BoxAddress));
        return box.getText().toLowerCase().contains(cityName.toLowerCase());
    }

    // lấy nội dung trong box address
    public String getAddressText() {
        WebElement box = wait.until(ExpectedConditions.visibilityOfElementLocated(BoxAddress));
        return box.getText();
    }

    public String ClickSelectDistrict(String districtName) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(DistrictOption));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        clickElement(DistrictOption);

        Select District = new Select(element);
        District.selectByVisibleText(districtName);
        return districtName;
    }

    public boolean isAddressBoxContainsCityAndDistrict(String cityName, String districtName) {
        WebElement box = wait.until(ExpectedConditions.visibilityOfElementLocated(BoxAddress));
        String boxText = box.getText().toLowerCase();

        return boxText.contains(cityName.toLowerCase()) && boxText.contains(districtName.toLowerCase());
    }

    // lấy nội dung trong box address
    public String getAddressText2() {
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

    public String getActiveTabText() {
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

    public boolean isToastMessageDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.presenceOfElementLocated(ToastMessage));
        Boolean toast = driver.findElement(ToastMessage).isDisplayed();
        Assert.assertTrue(toast, "Không hiển thị thông báo khi click button Thanh toán trả góp");
        return isElementDisplayed(ToastMessage);
    }
}
