package com.cellphoneS.pages;

import com.helpers.ValidateUIHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Search_Page extends ValidateUIHelper {
    private static WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    private final By searchInput = By.xpath("//input[@id='inp$earch']");
    private final By IconSearch = By.xpath("//button[@type='submit']//div//*[name()='svg']");
    private final By  closeSearch = By.xpath("//span[@id='btn-close-search']");
    private final By LinkAdSearch = By.xpath("//img[@alt='B2S 2025']");
    private final By TextHistory = By.xpath("//div[@id='history-search']");
    private final By TextTrending = By.xpath("//div[@class='trending-search']//div[@class='is-flex is-align-items-center']");
    private final By DeleteHistory = By.xpath("//a[contains(text(),'Xóa tất cả')]");
    private final By LinkTrending = By.xpath("//p[normalize-space()='iPhone 16 Series']");
    private final By TextCoPhaiBanMuonTim = By.xpath("//p[contains(text(),'Có phải bạn muốn tìm')]");
    private final By TextSanPhamGoiY = By.xpath("//p[contains(text(),'Sản phẩm gợi ý')]");
    private final By LinkCoPhaiBanMuonTim = By.xpath("//div[normalize-space()='iPhone 15 | 15 Plus | 15 Pro | 15 Pro Max']");
    private final By LinkSanPhamGoiY = By.xpath("//div[@class='mt-2 product-box']//a[1]");
    private final By TimKiem1 = By.xpath("//h1[contains(text(),'Tìm th')]");

    public Search_Page(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void inputSearch(String searchText) {
        sendKeys(searchInput, searchText);
        clickElement(IconSearch);
    }

    public void inputSearch2(String searchText) {
        sendKeys(searchInput, searchText);
        sendKeys(searchInput, String.valueOf(Keys.ENTER));
    }

    public void SearchAndcloseSearch (String searchText) {
        sendKeys(searchInput, searchText);
        clickElement(closeSearch);
    }

    public void closeSearch() {
        clickElement(closeSearch);
    }

    public void ClickLinkAdSearch() {
        clickElement(LinkAdSearch);
    }

    public void ClickLinkTrending() {
        clickElement(LinkTrending);
    }
    public boolean isTextHistoryDisplayed() {
        return isElementDisplayed(TextHistory);
    }
    public boolean isTextTrendingDisplayed() {
        return isElementDisplayed(TextTrending);
    }
    public void DeleteHistory() {
        clickElement(DeleteHistory);
    }
    public boolean isTextCoPhaiBanMuonTimDisplayed() {
        return isElementDisplayed(TextCoPhaiBanMuonTim);
    }
    public boolean isTextSanPhamGoiYDisplayed() {
        return isElementDisplayed(TextSanPhamGoiY);
    }
    public void ClickLinkCoPhaiBanMuonTim() {
        clickElement(LinkCoPhaiBanMuonTim);
    }
    public void ClickLinkSanPhamGoiY() {
        clickElement(LinkSanPhamGoiY);
    }
    public void scrollToElement() {
        js.executeScript("window.scrollBy(0, 1000)");
    }
    public boolean isTimKiem1Displayed() {
        return isElementDisplayed(TimKiem1);
    }
}
