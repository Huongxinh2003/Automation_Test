package com.cellphoneS.pages;

import com.helpers.ValidateUIHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Product_Detail_Page extends ValidateUIHelper {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;


    public By ProductCard = By.xpath("//div[@class='product-list-filter is-flex is-flex-wrap-wrap']//div[1]//div[1]//a[1]");
    public By TitleProduct = By.xpath("//h1[normalize-space()='iPhone 16 Pro Max 256GB | Chính hãng VN/A']");
    public By ProductPrice = By.xpath("//div[@class='box-product-price']");
    public By ProductThumbnail = By.xpath("//div[@class='gallery-slide gallery-top swiper-container swiper-container-initialized swiper-container-horizontal']//div[@class='swiper-slide swiper-slide-next']");
    public By ProductThumnailSmall = By.xpath("//div[@class='thumbnail-slide swiper-container gallery-thumbs swiper-container-initialized swiper-container-horizontal swiper-container-free-mode swiper-container-thumbs']//div[@class='swiper-wrapper']");
    public By VersionProduct = By.xpath("//div[@class='box-linked']");
    public By ColorProduct = By.xpath("//div[@class='box-product-variants']");
    public By CityOption = By.xpath("//div[@class='box-on-stock-option button__change-province']");
    public By DistrictOption = By.xpath("//select[@id='districtOptions']");
    public By SelectCity = By.xpath("//a[contains(text(),'Đà Nẵng')]");
    public By SelectDistrict = By.xpath("//option[@value='40']");
    public By FavoriteProduct = By.xpath("//div[@class='box-bottom-item']//button[@class='btn__effect button__add-wishlist inactive']//*[name()='svg']");
    public By BuyNow1 = By.xpath("//button[@class='btn-cta order-button button--large is-flex is-justify-content-center is-align-items-center']");
    public By AddToCart1 = By.xpath("//button[@class='btn-cta button button--small add-to-cart-button']");
    public By BuyNow2 = By.xpath("//button[@class='button-desktop button-desktop-order is-flex is-justify-content-center is-align-items-center']");
    public By AddToCart2 = By.xpath("//button[@class='button-desktop button-add-to-cart']");
    public By ProductInfo = By.xpath("//div[@class='cta-product-info']");
    

    public Product_Detail_Page(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickProductCard() {
        clickElement(ProductCard);
    }

    public boolean isTitleProductDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(TitleProduct));
        return isElementDisplayed(TitleProduct);
    }

}
