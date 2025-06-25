package com.CellphoneS.pages;

import com.helpers.ValidateUIHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Search_Page extends ValidateUIHelper {
    private static WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    private final By searchInput = By.xpath("//input[@id='inp$earch']");
    private final By IconSearch = By.xpath("//button[@type='submit']//div//*[name()='svg']");
    private final By closeSearch = By.xpath("//span[@id='btn-close-search']");
    private final By suggestBoxSearch = By.xpath("//div[@class='suggest-search']");
    private final By LinkAdSearch = By.xpath("//img[@alt='B2S 2025']");
    private final By TextHistory = By.xpath("//p[contains(text(),'Lịch sử tìm kiếm')]");
    public By HistorySearch = By.xpath("//div[@class='is-block search-history-box']");
    private final By TextTrending = By.xpath("//div[@class='trending-search']//div[@class='is-flex is-align-items-center']");
    private final By DeleteHistory = By.xpath("//a[contains(text(),'Xóa tất cả')]");
    private final By BoxCoPhaiBanMuonTim = By.xpath("//div[@class='mb-1 category-box block']");
    private final By BoxSanPhamGoiY = By.xpath("//div[@class='mt-2 product-box']");
    private final By LinkCoPhaiBanMuonTim = By.xpath("//div[normalize-space()='iPhone 15 | 15 Plus | 15 Pro | 15 Pro Max']");
    private final By LinkSanPhamGoiY = By.xpath("//div[@class='mt-2 product-box']//a[1]");
    private final By ProductSuggest = By.xpath("//p[normalize-space()='S25 Ultra']");
    private final By ListSuggest = By.xpath("//p[normalize-space()='iPhone 16 Series']");
    public By TitleProduct = By.xpath("//h1[normalize-space()='Samsung Galaxy S25 Ultra 12GB 256GB']");
    public By ProductCard = By.xpath("//div[@class='product-list-filter is-flex is-flex-wrap-wrap']//div[1]//div[1]//a[1]");

    public Search_Page(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public Product_Detail_Page openProductDetail(String searchText) {
        sendKeys(searchInput, searchText);
        sendKeys(searchInput, String.valueOf(Keys.ENTER));

        // Đợi overlay biến mất
        By overlay = By.cssSelector("div.header-overlay");

        List<WebElement> overlays = driver.findElements(overlay);
        if (!overlays.isEmpty() && overlays.get(0).isDisplayed()) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(overlay));
        }
        clickElement(ProductCard);
        return new Product_Detail_Page(driver);
    }

    public void ClickInputSearch() {
        clickElement(searchInput);
    }

    public void inputSearch(String searchText) {
        sendKeys(searchInput, searchText);
        clickElement(IconSearch);
    }

    public void inputSearch2(String searchText) {
        sendKeys(searchInput, searchText);
        sendKeys(searchInput, String.valueOf(Keys.ENTER));
    }
    public void inputSearch3(String searchText) {
        sendKeys(searchInput, searchText);
    }

    public void SearchAndcloseSearch (String searchText) {
        sendKeys(searchInput, searchText);
        clickElement(closeSearch);
    }

    public void closeSearch() {
        clickElement(closeSearch);
    }

    public boolean isSuggestBoxSearchDisplayed() {
        return isElementDisplayed(suggestBoxSearch);
    }
    public void checkSuggestBoxSearch() {
        try {
            // Đợi tối đa 10s để hộp gợi ý biến mất
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            boolean disappeared = wait.until(ExpectedConditions.invisibilityOfElementLocated(suggestBoxSearch));
            Assert.assertTrue(disappeared, "Danh sách gợi ý vẫn còn hiển thị sau khi click sản phẩm");
        } catch (TimeoutException e) {
            Assert.fail("Danh sách gợi ý không biến mất sau khi chờ");
        }
    }

    public void ClickLinkAdSearch() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(LinkAdSearch));
        clickElement(LinkAdSearch);
    }

    public boolean isLinkAdSearchDisplayed() {
        return isElementDisplayed(LinkAdSearch);
    }

    public boolean isTextHistoryDisplayed() {
        return isElementDisplayed(TextHistory);
    }

    public boolean isHistorySearchDisplayed() {
        return isElementDisplayed(HistorySearch);
    }

    public List<String> getSearchHistoryItems() {
        List<String> historyTexts = new ArrayList<>();

        List<WebElement> historyItems = driver.findElements(HistorySearch);

        for (WebElement item : historyItems) {
            historyTexts.add(item.getText().trim());
        }
        return historyTexts;
    }

    public boolean isTextTrendingDisplayed() {
        return isElementDisplayed(TextTrending);
    }

    public void DeleteHistory() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(DeleteHistory));
        clickElement(DeleteHistory);
    }
    public boolean isBoxCoPhaiBanMuonTimDisplayed() {
        return isElementDisplayed(BoxCoPhaiBanMuonTim);
    }

    public boolean isBoxSanPhamGoiYDisplayed() {
        return isElementDisplayed(BoxSanPhamGoiY);
    }

    public void ClickLinkCoPhaiBanMuonTim() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(LinkCoPhaiBanMuonTim));
        clickElement(LinkCoPhaiBanMuonTim);
    }
    public void ClickLinkSanPhamGoiY() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(LinkSanPhamGoiY));
        clickElement(LinkSanPhamGoiY);
    }

    public void ClickProductSuggest() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(ProductSuggest));
        clickElement(ProductSuggest);
    }

    public void ClickListSuggest() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(ListSuggest));
        clickElement(ListSuggest);
    }

    public boolean isTitleProductDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(TitleProduct));
        return isElementDisplayed(TitleProduct);
    }

}
