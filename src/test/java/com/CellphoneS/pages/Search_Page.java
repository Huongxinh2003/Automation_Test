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
        Search_Page.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public static String getTitleCheckout() {
        return driver.getTitle();
    }

    public static boolean verifyTitleCheckout() {
        String expectedTitle = "CellphoneS Checkout";
        Assert.assertEquals(getTitleCheckout(), expectedTitle);
        return getTitleCheckout().equals(expectedTitle);
    }

    public Product_Detail_Page openProductDetail(String searchText) {
        sendKeys(searchInput, searchText);
        sendKeys(searchInput, String.valueOf(Keys.ENTER));

        By overlay = By.cssSelector("div.header-overlay");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        try {
            // Đợi overlay biến mất
            wait.until(ExpectedConditions.invisibilityOfElementLocated(overlay));
        } catch (TimeoutException e) {
            System.out.println("Overlay vẫn còn sau 30s - tiếp tục để tránh fail chuỗi test.");
        }

        // Đảm bảo card sản phẩm đã hiện ra và sẵn sàng click
        wait.until(ExpectedConditions.visibilityOfElementLocated(ProductCard));
        wait.until(ExpectedConditions.elementToBeClickable(ProductCard));

        clickElement(ProductCard);
        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='75%'");
        return new Product_Detail_Page(driver);
    }


    public void ClickInputSearch() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(searchInput));
        input.click();
    }

    public void inputSearch(String searchText) {
        sendKeys(searchInput, searchText);
        WebElement click = driver.findElement(IconSearch);
        click.click();
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
        wait.until(ExpectedConditions.visibilityOfAllElements(historyItems));
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
        WebElement ProductSug = driver.findElement(ProductSuggest);
        ProductSug.click();
    }

    public void ClickListSuggest() {
        WebElement suggest = driver.findElement(ListSuggest);
        suggest.click();
    }

    public boolean isTitleProductDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(TitleProduct));
        return isElementDisplayed(TitleProduct);
    }

}
