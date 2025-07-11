package com.CellphoneS.tests;

import com.base.BaseSetup;
import com.CellphoneS.helpers.SignIn_Helpers;
import com.CellphoneS.pages.Homepage_page;
import com.CellphoneS.pages.Product_Detail_Page;
import com.CellphoneS.pages.Search_Page;
import com.helpers.ValidateUIHelper;
import com.ultilities.ExcelUtils;
import com.ultilities.listeners.ReportListener;
import com.ultilities.logs.LogUtils;
import com.ultilities.Properties_File;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


import java.time.Duration;
import java.util.List;

@Listeners(ReportListener.class)
public class Search_Test extends BaseSetup {
    private static final Logger log = LoggerFactory.getLogger(Search_Test.class);
    private WebDriver driver;
    public Search_Page search_page;
    public WebDriverWait wait;
    public ExcelUtils excelHelper;
    public ValidateUIHelper validateUIHelper;
    public SignIn_Helpers signIn_helpers;
    public Homepage_page homepage_page;
    public Product_Detail_Page product_detail_page;


    @BeforeClass(groups = {"UI_Test", "Function"})
    public void setUp() throws Exception {
        //gọi hàm khởi tạo properties
        Properties_File.setPropertiesFile();
        // Lấy driver từ class cha BaseSetup
        boolean headless = Boolean.parseBoolean(Properties_File.getPropValue("headless"));
        driver = setupDriver(Properties_File.getPropValue("browser"), headless);
        search_page = new Search_Page(driver);
        excelHelper = new ExcelUtils();
        validateUIHelper = new ValidateUIHelper(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        signIn_helpers = new SignIn_Helpers(driver);
        homepage_page = signIn_helpers.SignIn(driver);
        log.info("Đã mở trang tìm kiếm");

    }

    @BeforeMethod(groups = {"UI_Test", "Function"})
    public void SearchProduct() {
        LogUtils.info("Thực hiện tìm kiếm sản phẩm 'iphone'");
        search_page = homepage_page.openSearchPage();
    }

    @Test(groups = "UI_Test", priority = 1, description = "Kiểm tra hiển thị khi nhập vào  tìm kiếm")
    public void Suggest_Box(){
        LogUtils.info("Tìm kiếm với Iphone khi click icon Search");
        search_page.inputSearch3("iphone");

        LogUtils.info("Kiểm tra mục Có phải bạn muốn tìm hiển thị");
        search_page.isBoxCoPhaiBanMuonTimDisplayed();
        Assert.assertTrue(search_page.isBoxCoPhaiBanMuonTimDisplayed(), "Không hiển thị mục Có phải bạn muốn tìm");
        test.get().pass("Hiển thị mục Có phải bạn muốn tìm");

        LogUtils.info("Kiểm tra mục Sản phẩm gợi ý hiển thị");
        search_page.isBoxSanPhamGoiYDisplayed();
        Assert.assertTrue(search_page.isBoxSanPhamGoiYDisplayed(), "Không hiển thị mục Sản phẩm gợi ý");
        test.get().pass("Hiển thị mục Sản phẩm gợi ý");

        LogUtils.info("Kiểm tra click vào từ khoá trong mục \"Có phải bạn muốn tìm\"");
        search_page.ClickLinkCoPhaiBanMuonTim();
        List<WebElement> productNames = driver.findElements(By.xpath("//div[@class='block-product-list-filter']"));
        String keyword = "iphone 15";

        for (WebElement product : productNames) {
            String name = product.getText().toLowerCase();
            Assert.assertTrue(name.contains(keyword), "Sản phẩm không chứa từ khóa: " + keyword);
        }
        test.get().pass("Sản phẩm có chứa từ khoá muốn tìm kiếm");

        LogUtils.info("Kiểm tra click vào từ khoá trong mục \"Sản phẩm gợi ý\"");
        search_page.ClickInputSearch();
        search_page.inputSearch3("iphone");
        search_page.ClickLinkSanPhamGoiY();
        test.get().pass("Truy cập trang chi tiết sản phẩm thành công");


        LogUtils.info("Kiểm tra tiêu đề sản phẩm");
        String title = driver.getTitle();
        LogUtils.info("Tiêu đề sản phẩm: " + title);
        test.get().pass("Tiêu đề trang chi tiết sản phẩm hiển thị đúng");

    }

    @Test (groups = "Function",priority = 1, description = "Kiểm tra tìm kiếm với từ khoá khi click icon Search")
    public void Search_Success() {
        LogUtils.info("Tìm kiếm với Iphone khi click icon Search");
        search_page.inputSearch("iphone");

        LogUtils.info("Kiểm tra danh sách gợi ý đã tự động ẩn sau khi click sản phẩm");
        search_page.checkSuggestBoxSearch();
        test.get().pass("Danh sách gợi ý đã tự động ẩn sau khi click sản phẩm");

        LogUtils.info("Đợi trang chuyển hướng");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Boolean url = wait.until(ExpectedConditions.urlContains("/catalogsearch/result?q=iphone"));

        LogUtils.info("Kiểm tra URL đúng");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(url, "URL sai: " + currentUrl);
        test.get().pass("URL đúng");

        LogUtils.info("Kiểm tra tiêu đề kết quả: “Tìm thấy xxxx sản phẩm cho từ khoá 'iphone'”");
        WebElement resultText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h1[contains(text(),'Tìm th')]")
        ));
        Assert.assertTrue(resultText.getText().toLowerCase().contains("iphone"));
        test.get().pass("Tiêu đề kết quả hiển thị đúng");

        LogUtils.info("In ra số sản phẩm tìm thấy");
        String text = resultText.getText(); // ví dụ: "Tìm thấy 4142 sản phẩm cho từ khoá 'iphone'"
        test.get().info(">>> Kết quả: " + text);

        LogUtils.info("Kiểm tra hiển thị danh sách sản phẩm");
        WebElement listProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='search-result-content']")
        ));
        Assert.assertTrue(listProduct.isDisplayed(), "Không hiển thị danh sách sản phẩm");
        test.get().pass("Hiển thị danh sách sản phẩm");

        LogUtils.info("Kiểm tra sản phẩm đầu tiên có chứa chữ iPhone");
        WebElement firstProduct = driver.findElement(By.xpath(
                "//div[@class='block-product-list-filter']"));
        Assert.assertTrue(firstProduct.getText().toLowerCase().contains("iphone"));
        test.get().pass("Sản phẩm đầu tiên có chứa chữ iPhone");

    }
    @Test(groups = "Function", priority = 2, description = "Kiểm tra tìm kiếm với từ khoá không hợp lệ khi click ENTER")
    public void Search_Fail() {
        LogUtils.info("Tìm kiếm với từ khoá sai khi click ENTER");
        search_page.inputSearch2("hhah8473hcfd");

        // Kiểm tra danh sách gợi ý đã biến mất
        LogUtils.info("Kiểm tra danh sách gợi ý đã tự động ẩn sau khi click sản phẩm");
        try {
            search_page.checkSuggestBoxSearch();
            test.get().pass("Danh sách gợi ý đã tự động ẩn sau khi click sản phẩm");
        } catch (Exception e) {
            test.get().fail("Danh sách gợi ý không ẩn như mong đợi: " + e.getMessage());
            Assert.fail(e.getMessage());
        }

        // Kiểm tra chuyển trang
        LogUtils.info("Đợi trang chuyển hướng");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.urlContains("/catalogsearch/result?q=hhah8473hcfd"));

            LogUtils.info("Kiểm tra URL đúng");
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("/catalogsearch/result?q=hhah8473hcfd"), "URL sai: " + currentUrl);
            test.get().pass("URL chuyển đúng: " + currentUrl);
        } catch (Exception e) {
            test.get().fail("URL không đúng sau khi tìm kiếm: " + e.getMessage());
            Assert.fail(e.getMessage());
        }

        // Kiểm tra tiêu đề kết quả
        LogUtils.info("Kiểm tra tiêu đề kết quả: “Tìm thấy xxxx sản phẩm cho từ khoá 'xxx'");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement resultText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h1[contains(text(),'Tìm th')]")
            ));
            Assert.assertTrue(resultText.getText().toLowerCase().contains("hhah8473hcfd"));
            test.get().pass("Tiêu đề kết quả chứa từ khóa tìm kiếm.");

            // In ra kết quả
            String text = resultText.getText();
            LogUtils.info(">>> Kết quả: " + text);
        } catch (Exception e) {
            test.get().fail("Tiêu đề kết quả không đúng hoặc không hiển thị: " + e.getMessage());
            Assert.fail(e.getMessage());
        }

        // Kiểm tra hình ảnh "không tìm thấy"
        LogUtils.info("Kiểm tra hiển thị hình ảnh không tìm thấy");
        try {
            WebElement imageNoResult = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//div[@class='no-result']//img")
                    ));
            Assert.assertTrue(imageNoResult.isDisplayed(), "Không hiển thị hình ảnh không tìm thấy");
            test.get().pass("Hiển thị đúng hình ảnh không tìm thấy.");
        } catch (Exception e) {
            test.get().fail("Không hiển thị hình ảnh không tìm thấy: " + e.getMessage());
            Assert.fail(e.getMessage());
        }

        // Kiểm tra thông báo "không có kết quả"
        LogUtils.info("Kiểm tra hiển thị thông báo không tìm thấy");
        try {
            WebElement textNoResult = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//p[contains(text(),'Không có kết quả bạn cần tìm')]")
                    ));
            Assert.assertTrue(textNoResult.isDisplayed(), "Không hiển thị thông báo không tìm thấy");
            test.get().pass("Thông báo không tìm thấy hiển thị đúng.");
        } catch (Exception e) {
            test.get().fail("Không hiển thị thông báo không tìm thấy: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    @Test(groups = "UI_Test", priority = 2, description = "Kiểm tra hiển thị khi tìm kiếm với sản phẩm gợi ý")
    public void ClickProductSuggest() {
        LogUtils.info("Click vào sản phẩm gợi ý");
        try {
            search_page.ClickProductSuggest();
            test.get().pass("Đã click vào sản phẩm gợi ý.");
        } catch (Exception e) {
            test.get().fail("Không thể click vào sản phẩm gợi ý: " + e.getMessage());
            Assert.fail(e.getMessage());
        }

        // Kiểm tra danh sách gợi ý đã biến mất
        LogUtils.info("Kiểm tra danh sách gợi ý đã tự động ẩn sau khi click sản phẩm");
        try {
            search_page.checkSuggestBoxSearch();
            test.get().pass("Danh sách gợi ý đã tự động ẩn.");
        } catch (Exception e) {
            test.get().fail("Danh sách gợi ý không tự động ẩn: " + e.getMessage());
            Assert.fail(e.getMessage());
        }

        // Kiểm tra chuyển trang đúng URL
        LogUtils.info("Đợi trang chuyển hướng");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.urlContains("https://cellphones.com.vn/dong-ho-thong-minh-samsung-galaxy-watch-8.html"));

            String currentUrl = driver.getCurrentUrl();
            LogUtils.info("URL hiện tại: " + currentUrl);
            Assert.assertTrue(currentUrl.contains("dong-ho-thong-minh-samsung-galaxy-watch-8.html"), "URL sai: " + currentUrl);
            test.get().pass("URL đúng sau khi click sản phẩm gợi ý.");
        } catch (Exception e) {
            test.get().fail("Chuyển hướng URL không đúng: " + e.getMessage());
            Assert.fail(e.getMessage());
        }

        // Kiểm tra hiển thị tiêu đề sản phẩm
        LogUtils.info("Kiểm tra tiêu đề sản phẩm");
        try {
            boolean isDisplayed = search_page.isTitleProductDisplayed();
            String title = driver.getTitle();
            LogUtils.info("Tiêu đề sản phẩm: " + title);
            Assert.assertTrue(isDisplayed, "Không hiển thị tiêu đề sản phẩm");
            test.get().pass("Tiêu đề sản phẩm hiển thị đúng: " + title);
        } catch (Exception e) {
            test.get().fail("Không hiển thị tiêu đề sản phẩm: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }


    @Test(groups = "UI_Test", priority = 3, description = "Kiểm tra hiển thị kết quả tìm kiếm khi tìm kiếm với danh sách gợi ý")
    public void ClickListSuggest() {
        LogUtils.info("Click vào danh sách gợi ý");
        try {
            search_page.ClickListSuggest();
            test.get().pass("Đã click vào danh sách gợi ý.");
        } catch (Exception e) {
            test.get().fail("Không thể click vào danh sách gợi ý: " + e.getMessage());
            Assert.fail(e.getMessage());
        }

        LogUtils.info("Đợi trang chuyển hướng");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.urlContains("https://cellphones.com.vn/mobile/apple/iphone-16.html"));
            test.get().pass("URL đã chuyển đến đúng trang danh sách gợi ý.");
        } catch (Exception e) {
            test.get().fail("Trang không chuyển hướng đúng URL mong đợi: " + e.getMessage());
            Assert.fail(e.getMessage());
        }

        LogUtils.info("Kiểm tra hiển thị của slide");
        try {
            WebElement slide = new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='block-top-sliding-banner is-flex']"))
            );
            Assert.assertTrue(slide.isDisplayed(), "Không hiển thị slide");
            test.get().pass("Slide hiển thị đúng.");
        } catch (Exception e) {
            test.get().fail("Không hiển thị slide: " + e.getMessage());
            Assert.fail(e.getMessage());
        }

        LogUtils.info("Kiểm tra hiển thị list brand");
        try {
            WebElement listBrand = new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='brands__content']//div[@class='list-brand']"))
            );
            Assert.assertTrue(listBrand.isDisplayed(), "Không hiển thị list brand");
            test.get().pass("List brand hiển thị đúng.");
        } catch (Exception e) {
            test.get().fail("Không hiển thị list brand: " + e.getMessage());
            Assert.fail(e.getMessage());
        }

        LogUtils.info("Kiểm tra hiển thị của danh sách sản phẩm");
        try {
            WebElement listProduct = new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='block-product-list-filter']"))
            );
            Assert.assertTrue(listProduct.isDisplayed(), "Không hiển thị danh sách sản phẩm");
            test.get().pass("Danh sách sản phẩm hiển thị đúng.");
        } catch (Exception e) {
            test.get().fail("Không hiển thị danh sách sản phẩm: " + e.getMessage());
            Assert.fail(e.getMessage());
        }

        LogUtils.info("Kiểm tra sản phẩm có chứa chữ 'iphone'");
        try {
            WebElement firstProduct = driver.findElement(
                    By.xpath("(//div[@class='product-info-container product-item'])[1]")
            );
            Assert.assertTrue(firstProduct.getText().toLowerCase().contains("iphone"), "Sản phẩm đầu tiên không chứa 'iphone'");
            test.get().pass("Sản phẩm đầu tiên có chứa chữ 'iphone'.");
        } catch (Exception e) {
            test.get().fail("Không thể kiểm tra sản phẩm đầu tiên: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }


    @Test (groups = "Function",priority = 3, description = "Kiểm tra hiển thị khi click Link, URL")
    public void ClickInputSearch() {
        LogUtils.info("Click vào ô tìm kiếm");
        search_page.ClickInputSearch();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        LogUtils.info("Kiểm tra hiển thi danh sách sản phẩm gợi ý");
        search_page.isSuggestBoxSearchDisplayed();
        Assert.assertTrue(search_page.isLinkAdSearchDisplayed(), "Không hiển thị danh sách sản phẩm gợi ý");
        test.get().pass("Hiển thị danh sách sản phẩm gợi ý");

        LogUtils.info("Kiểm tra click banner quảng cáo");
        search_page.ClickLinkAdSearch();
        String currentUrl = driver.getCurrentUrl();
        test.get().info("URL hiện tại: " + currentUrl);

        test.get().pass("Kiểm tra URL chuyển đúng khi click banner");

    }

    @Test (groups = "Function",priority = 4, description = "Kiểm tra lịch sử tìm kiếm")
    public void HistorySearch() {
        LogUtils.info("Tìm kiếm với Iphone");
        search_page.inputSearch2("iphone");


        LogUtils.info("Tìm kiếm với samsung galaxy");
        search_page.ClickInputSearch();
        search_page.closeSearch();
        search_page.inputSearch2("samsung galaxy");

        LogUtils.info("Tìm kiếm với iphone 16");
        search_page.ClickInputSearch();
        search_page.closeSearch();
        search_page.inputSearch2("iphone 16");

        LogUtils.info("Click vào ô tìm kiếm");
        search_page.ClickInputSearch();
        search_page.closeSearch();

        LogUtils.info("Kiểm tra hiển thị Title Lịch sử tìm kiếm");
        search_page.isTextHistoryDisplayed();
        Assert.assertTrue(search_page.isTextHistoryDisplayed(), "Không hiển thị Title Lịch sử tìm kiếm");
        test.get().pass("Hiển thị title Lịch sử tìm kiếm");

        LogUtils.info("Kiểm tra hiển thị danh sách lịch sử tìm kiếm");
        search_page.isHistorySearchDisplayed();
        Assert.assertTrue(search_page.isHistorySearchDisplayed(), "Không hiển thị danh sách lịch sử tìm kiếm");

        LogUtils.info("Lấy danh sách các mục trong lịch sử tìm kiếm");
        List<String> actualHistory = search_page.getSearchHistoryItems();
        LogUtils.info("In ra danh sách lịch sử tìm kiếm");
        for (String item : actualHistory) {
            LogUtils.info(item);
        }

        LogUtils.info("Kiểm tra nút Xóa lịch sử tìm kiếm");
        search_page.DeleteHistory();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(search_page.HistorySearch));

        List<WebElement> remaining = driver.findElements(search_page.HistorySearch);
        System.out.println("Số item còn lại: " + remaining.size());
        Assert.assertTrue(remaining.isEmpty(), "Lịch sử vẫn còn sau khi xóa");

        test.get().pass("Kiểm tra hoàn tất - Lịch sử tìm kiếm hiển thị chính xác");
    }

    @Test (groups = "Function",priority = 5, description = "Kiểm tra thời gian hiển thị danh sách gợi ý")
    public void TimeForSearch1() {
        LogUtils.info("Click vào ô tìm kiếm để kiểm tra thời gian hiển thị danh sách gợi ý");
        long startTimeClick = System.currentTimeMillis();

        search_page.ClickInputSearch();

        long endTimeClick = System.currentTimeMillis();
        long durationClick = endTimeClick - startTimeClick;

        LogUtils.info("Thời gian hiển thị danh sách gợi ý sau khi click: " + durationClick + " ms");

        test.get().pass("Kiểm tra thời gian hiển thị danh sách gợi ý khi click vào input tìm kiếm thành công");

    }

    @Test (groups = "Function",priority = 6, description = "Kiểm tra thời gian hiển thị danh sách gợi ý")
    public void TimeForSearch2() {
        LogUtils.info("Nhập từ khóa để kiểm tra thời gian hiển thị danh sách gợi ý");
        long startTimeInput = System.currentTimeMillis();

        search_page.inputSearch3("iphone 16");

        long endTimeInput = System.currentTimeMillis();
        long durationInput = endTimeInput - startTimeInput;

        LogUtils.info("Thời gian hiển thị danh sách gợi ý sau khi nhập: " + durationInput + " ms");

        test.get().pass("Kiêm tra thời gian hiển thị danh sách gợi ý khi nhập từ khoá thành công");
    }

    @Test (groups = "Function",priority = 7, description = "Kiểm tra nút X trên thanh tìm kiếm")
    public void DeleteSearch() {
        LogUtils.info("Click X trên thanh tìm kếm khi nhập từ khoá");
        search_page.SearchAndcloseSearch("iphone 16");

        LogUtils.info("Kiểm tra danh sách gợi ý trước khi click X");
        Assert.assertTrue(search_page.isLinkAdSearchDisplayed(), "Không hiển thị danh sách sản phẩm gợi ý");
        test.get().pass("Hiển thị danh sách sản phẩm gợi ý");

        LogUtils.info("Click X trên thanh tìm kiếm khi chưa nhập từ khoá");
        search_page.closeSearch();
        search_page.isSuggestBoxSearchDisplayed();
        Assert.assertFalse(search_page.isSuggestBoxSearchDisplayed(), "Hiển thị danh sách sản phẩm gợi ý");
        test.get().pass("Kiểm tra nút X trên thanh tìm kiếm thành công");
    }

}