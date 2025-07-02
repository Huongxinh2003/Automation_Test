package com.Clickbuy.page;

import com.helpers.ValidateUIHelper;
import com.ultilities.logs.LogUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Product_Detail_Page_Cb extends ValidateUIHelper{
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static JavascriptExecutor js;

    public By TitleProduct = By.xpath("//h1[contains(text(),'iPhone 13 128GB chính hãng VN/A - Tặng BH rơi vỡ v')]");
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
    public By DropdownCity = By.xpath("//div[@class='area-select store-select']");
    public By ListAddress = By.xpath("//div[@class='area-list show']");
    public By PhoneContact = By.xpath("//input[@placeholder='Tư vấn qua số điện thoại']");
    public By ButtonSumit = By.xpath("//button[@class='submit_call']");
    public By ToastContact = By.xpath("//span[@class='close-jq-toast-single']");
    public By EvaluationInput = By.xpath("//textarea[@placeholder='Hãy để lại bình luận của bạn tại đây!']");
    public By EvaluationStar = By.xpath("//span[@class='star']");
    public By EvaluationSubmit = By.xpath("//button[@title='Gửi']");
    public By ToastEvaluation = By.xpath("//div[@class='jq-toast-single jq-has-icon jq-icon-success']");


    public Product_Detail_Page_Cb(WebDriver driver) {
        super(driver);
        Product_Detail_Page_Cb.driver = driver;
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public boolean isTitleProductDisplayed() {
        return isElementDisplayed(TitleProduct);
    }

    public String getTitleProduct() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(TitleProduct));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String titleText = (String) js.executeScript("return arguments[0].textContent.trim();", titleElement);
        return titleText;
    }

    public void selectVersionProduct(String version) {
        List<WebElement> options = driver.findElements
                (By.xpath("//div[@class='related_versions list']"));

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
        List<WebElement> colorOptions = driver.findElements(By.xpath("//div[@class='list-variant list key_1']"));
        for (WebElement option : colorOptions) {
            String text = option.getText().replaceAll("\\s+", "").toLowerCase();
            if (text.contains(color.replaceAll("\\s+", "").toLowerCase())) {
                wait.until(ExpectedConditions.elementToBeClickable(option));
                (js).executeScript("arguments[0].click();", option);
                LogUtils.info("Đã chọn màu sắc: " + option.getText());
                return;
            }
        }
        throw new RuntimeException("Không tìm thấy màu sắc có tên: " + color);
    }
}
