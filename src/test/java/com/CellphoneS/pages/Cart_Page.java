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
import java.util.List;
import java.util.stream.Collectors;

public class Cart_Page extends ValidateUIHelper {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static JavascriptExecutor js;

//    static String model = "iPhone 16 Pro Max";
//    static String capacity = "1TB";
//    static String color = "Titan Đen";
    public By CheckboxSelect = By.xpath("//div[@class='d-flex align-items-center justify-content-center']");
    public By ButtonDelete = By.xpath("//em[contains(text(),'Xóa sản phẩm đã chọn')]");
    public By CheckboxProduct = By.xpath("//label[@for='__BVID__32']");
    public By TitleProduct = By.xpath("//div[@class='product-name']");
    public By ImageProduct = By.xpath(("//label[@for='__BVID__32']"));
    public By PriceProduct = By.xpath("//p[contains(text(),'đ')]");
    public By ProductQuantity = By.xpath("//input[@readonly='readonly']");
    public By BoxProduct = By.xpath("//div[@class='block__product-item']");
    public By MinusButton = By.xpath("//span[@class='minus d-flex justify-content-center align-items-center']");
    public By PlusButton = By.xpath("//span[@class='plus d-flex justify-content-center align-items-center']");
    public By ToastMessage1 = By.xpath("//div[@class='toast-body']");
    public By PopupAnounce = By.xpath("//div[@id='cpsModalB2b___BV_modal_content_']");
    public By BoxPromotion = By.xpath("//div[@class='member-promotion']");
    public By BoxPromo = By.xpath("//div[@class='box_promo']");
    public By BuyCombo = By.xpath("//div[@class='combov2']");
    public By SelectBuyCombo = By.xpath("//body[1]/div[1]/div[1]/div[1]/div[2]/div[3]/div[1]/div[2]/div[4]/div[2]/div[1]/div[2]/div[1]/div[3]/button[1]");
    public By ShowMoreCombo = By.xpath("//span[contains(text(),'Xem tất cả')]");
    public By PriceTemp = By.xpath("//p[contains(text(),'đ')]");
    public By PriceSave = By.xpath("//div[@class='bmsm-info']");
    public By ButtonBuyNow = By.xpath("//button[contains(text(),'Mua ngay')]");


    public Cart_Page(WebDriver driver) {
        super(driver);
        Cart_Page.driver = driver;
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Lấy tiêu đề trang
    public static String getCartPageTitle() {
        return driver.getTitle();
    }

    // Kiểm tra tiêu đề trang
    //False
    public boolean verifyCartPageTitle() {
        String expectedTitle = "CellphoneS Cart";
        Assert.assertEquals(getCartPageTitle(), expectedTitle);
        return getCartPageTitle().equals(expectedTitle);
    }

    public void selectCheckbox() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(CheckboxSelect));
        clickElement(CheckboxSelect);
    }

    public boolean isCheckboxSelectDisplayed() {
        return isElementDisplayed(CheckboxSelect);
    }

    public void clickDelete() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(ButtonDelete));
        clickElement(ButtonDelete);
    }

    public List<String> getAllProductNamesInCart() {
        List<WebElement> productElements = driver.findElements(By.cssSelector(".item__name")); // chỉnh selector
        return productElements.stream().map(WebElement::getText).collect(Collectors.toList());
    }


    public boolean isSelectedProduct() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(CheckboxProduct));
        return isElementDisplayed(CheckboxProduct);
    }

    public boolean isTitleProductDisplayed() {
        return isElementDisplayed(TitleProduct);
    }

    public String getCartImageSrc() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ImageProduct)).getAttribute("src");
    }

    public String extractFileName(String url) {
        return url.split("\\?")[0].substring(url.lastIndexOf("/") + 1);
    }

    public String getPriceProduct() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(PriceProduct));
        return driver.findElement(PriceProduct).getText();
    }

    //Hàm chuển đổi string price sang int
    public int convertPriceStringToInt(String priceText) {
        // Ví dụ input: "5.690.000₫"
        priceText = priceText.replaceAll("[^\\d]", ""); // Bỏ dấu chấm, ₫
        return Integer.parseInt(priceText);
    }

    public void getPriceTempInt() {
        List<WebElement> priceElements = driver.findElements(PriceProduct);

        int total = 0;
        for (WebElement priceElement : priceElements) {
            String priceText = priceElement.getText();
            int price = convertPriceStringToInt(priceText);
            total += price;
        }

        LogUtils.info("Tổng giá của các sản phẩm trong giỏ: " + total + " VND");

        WebElement subtotalElement = driver.findElement(PriceTemp);
        int subtotal = convertPriceStringToInt(subtotalElement.getText());


        if (subtotal == total) {
            LogUtils.info("Tạm tính đúng");
        } else {
            Assert.assertEquals(total, subtotal,"Tạm tính sai. Tổng sản phẩm = " + total + ", nhưng Tạm tính = " + subtotal);
        }

    }

    public String getPriceTemp() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(PriceTemp));
        return driver.findElement(PriceTemp).getText();
    }

   public String getProductQuantity() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(ProductQuantity));
        return driver.findElement(ProductQuantity).getAttribute("value");
    }

    public boolean isBoxProductDisplayed() {
        return isElementDisplayed(BoxProduct);
    }

    public void clickMinusButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(MinusButton));
        clickElement(MinusButton);
    }
    public void clickPlusButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(PlusButton));
        clickElement(PlusButton);
    }

    public String getToastMessage() {
        try {
            WebElement toast = driver.findElement(ToastMessage1);
            return toast.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public Checkout_Page openCheckout() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(ButtonBuyNow));
        clickElement(ButtonBuyNow);
        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='70%'");
        return new Checkout_Page(driver);
    }

}
