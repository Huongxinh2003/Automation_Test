package com.Clickbuy.page;

import com.helpers.ValidateUIHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Search_Page_Cb extends ValidateUIHelper {
    private static WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private ValidateUIHelper validateUIHelper;

    public By inputSearch = By.xpath("//input[@id='inp-search']");
    public By iconSearch = By.xpath("//button[@title='Tìm kiếm']");
    public By BoxCoPhaiBanMuonTim = By.xpath("//div[@class='mb-1 category-box block']");
    public By BoxSanPhamGoiY = By.xpath("//div[@class='product-box']");
    public By LinkCoPhaiBanMuonTim = By.xpath("//div[@class='search']//a[1]//div[1]");
    public By LinkSanPhamGoiY = By.xpath("//header[@class='header']//li[1]//a[1]");
    public By BoxSuggest = By.xpath("//div[@class='suggest-item__list']");
    public static By ResultTitle = By.xpath("//div[@class='mgt15 mgb15 container']");
    public By TitleSearchFail = By.xpath("//h1[contains(text(),'Đường dẫn đã hết hạn truy cập hoặc không tồn tại!')]");
    public By SubTitleSearchFail = By.xpath("//h3[contains(text(),'Quý khách có thể tham khảo thêm các sản phẩm mới n')]");
    public By ButtonGoHomePage = By.xpath("//span[@id='countdown']");
    public static By LogoClickbuy = By.xpath("//img[@title='Clickbuy.com.vn - Hệ thống bán lẻ điện thoại, máy tính bảng, laptop, phụ kiện chính hãng']");
    public By ProductCard = By.xpath("//a[@title='iPhone 16 Pro Max 256GB Chính Hãng VN/A']");

    public Search_Page_Cb(WebDriver driver) {
        super(driver);
        Search_Page_Cb.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        validateUIHelper = new ValidateUIHelper(driver);
    }

    public Product_Detail_Page_Cb openProductDetail(String input) {
        sendKeys(inputSearch, input);
        clickElement(iconSearch);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(ProductCard));
        clickElement(ProductCard);
        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='70%'");
        return new Product_Detail_Page_Cb(driver);
    }

    public void clickInputSearch() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(inputSearch));
        input.click();
    }

    public void inputSearch1(String input) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputSearch));
        sendKeys(inputSearch, input);
        clickElement(iconSearch);
    }

    public void inputSearch2(String input) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputSearch));
        sendKeys(inputSearch, input);
    }


    public boolean isBoxCoPhaiBanMuonTimDisplayed() {
        return isElementDisplayed(BoxCoPhaiBanMuonTim);
    }

    public boolean isBoxSanPhamGoiYDisplayed() {
        return isElementDisplayed(BoxSanPhamGoiY);
    }

    public void clickLinkCoPhaiBanMuonTim() {
        clickElement(LinkCoPhaiBanMuonTim);
    }

    public void clickLinkSanPhamGoiY() {
        clickElement(LinkSanPhamGoiY);
    }

    public boolean isSuggestBoxDisplayed() {
        return isElementDisplayed(BoxSuggest);
    }

    public boolean isTitleSearchFailDisplayed() {
        return isElementDisplayed(TitleSearchFail);
    }

    public boolean isSubTitleSearchFailDisplayed() {
        return isElementDisplayed(SubTitleSearchFail);
    }
    public void clickButtonGoHomePage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(ButtonGoHomePage));
        clickElement(ButtonGoHomePage);
    }

}
