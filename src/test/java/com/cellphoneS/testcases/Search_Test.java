package com.cellphoneS.testcases;

import com.cellphoneS.bases.BaseSetup;
import com.cellphoneS.bases.SignIn_Helpers;
import com.cellphoneS.pages.Homepage_page;
import com.cellphoneS.pages.Product_Detail_Page;
import com.cellphoneS.pages.Search_Page;
import com.helpers.CaptureHelpers;
import com.helpers.RecordVideo;
import com.helpers.ValidateUIHelper;
import com.ultilities.ExcelUtils;
import com.ultilities.LogUtils;
import com.ultilities.Properties_File;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

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


    @BeforeClass
    public void setUp() throws Exception {
        //gọi hàm khởi tạo properties
        Properties_File.setPropertiesFile();
        // Gọi lại hàm startRecord
//        try {
//            RecordVideo.startRecord("RecordVideo");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        // Lấy driver từ class cha BaseSetup
        driver = setupDriver(Properties_File.getPropValue("browser"));
        search_page = new Search_Page(driver);
        excelHelper = new ExcelUtils();
        validateUIHelper = new ValidateUIHelper(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        signIn_helpers = new SignIn_Helpers(driver);
        homepage_page = signIn_helpers.SignIn(driver);
        search_page = homepage_page.openSearchPage();
        log.info("Đã mở trang tìm kiếm");

    }

    @AfterMethod
    public void takeScreenshot(ITestResult result) throws InterruptedException {
        Thread.sleep(1000);
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                CaptureHelpers.captureScreenshot(driver, result.getName());
            } catch (Exception e) {
                LogUtils.info("Exception while taking screenshot " + e.getMessage());
            }
        }
    }

    @Test
    public void Suggest_Box(){
        LogUtils.info("Tìm kiếm với Iphone khi click icon Search");
        search_page.inputSearch3("iphone");

        LogUtils.info("Kiểm tra mục Có phải bạn muốn tìm hiển thị");
        search_page.isBoxCoPhaiBanMuonTimDisplayed();
        Assert.assertTrue(search_page.isBoxCoPhaiBanMuonTimDisplayed(), "Không hiển thị mục Có phải bạn muốn tìm");

        LogUtils.info("Kiểm tra mục Sản phẩm gợi ý hiển thị");
        search_page.isBoxSanPhamGoiYDisplayed();
        Assert.assertTrue(search_page.isBoxSanPhamGoiYDisplayed(), "Không hiển thị mục Sản phẩm gợi ý");

        LogUtils.info("Kiểm tra click vào từ khoá trong mục \"Có phải bạn muốn tìm\"");
        search_page.ClickLinkCoPhaiBanMuonTim();
        List<WebElement> productNames = driver.findElements(By.xpath("//div[@class='block-product-list-filter']"));
        String keyword = "iphone 15";

        for (WebElement product : productNames) {
            String name = product.getText().toLowerCase();
            Assert.assertTrue(name.contains(keyword), "Sản phẩm không chứa từ khóa: " + keyword);
        }


        LogUtils.info("Kiểm tra click vào từ khoá trong mục \"Sản phẩm gợi ý\"");
        search_page.ClickInputSearch();
        search_page.inputSearch3("iphone");
        search_page.ClickLinkSanPhamGoiY();

        LogUtils.info("Kiểm tra tiêu đề sản phẩm");
        String title = driver.getTitle();
        LogUtils.info("Tiêu đề sản phẩm: " + title);
        Assert.assertTrue(search_page.isTitleProductDisplayed(), "Không hiển thị tiêu đề sản phẩm");


    }

    @Test
    public void Search_Success() {
        LogUtils.info("Tìm kiếm với Iphone khi click icon Search");
        search_page.inputSearch("iphone");

        // Kiểm tra danh sách đã biến mất
        LogUtils.info("Kiểm tra danh sách gợi ý đã tự động ẩn sau khi click sản phẩm");
        search_page.checkSuggestBoxSearch2();

        LogUtils.info("Đợi trang chuyển hướng");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/catalogsearch/result?q=iphone"));

        LogUtils.info("Kiểm tra URL đúng");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/catalogsearch/result?q=iphone"), "URL sai: " + currentUrl);

        LogUtils.info("Kiểm tra tiêu đề kết quả: “Tìm thấy xxxx sản phẩm cho từ khoá 'iphone'”");
        WebElement resultText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h1[contains(text(),'Tìm th')]")
        ));
        Assert.assertTrue(resultText.getText().toLowerCase().contains("iphone"));

        LogUtils.info("In ra số sản phẩm tìm thấy");
        String text = resultText.getText(); // ví dụ: "Tìm thấy 4142 sản phẩm cho từ khoá 'iphone'"
        LogUtils.info(">>> Kết quả: " + text);

        LogUtils.info("Kiểm tra hiển thị danh sách sản phẩm");
        WebElement listProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='search-result-content']")
        ));
        Assert.assertTrue(listProduct.isDisplayed(), "Không hiển thị danh sách sản phẩm");

        LogUtils.info("Kiểm tra sản phẩm đầu tiên có chứa chữ iPhone");
        WebElement firstProduct = driver.findElement(By.xpath("//div[@class='block-product-list-filter']"));
        Assert.assertTrue(firstProduct.getText().toLowerCase().contains("iphone"));

    }
    @Test
    public void Search_Fail() {
        LogUtils.info("Tìm kiếm với fail khi click ENTER");
        search_page.inputSearch2("hhah8473hcfd");

        // Kiểm tra danh sách đã biến mất
        LogUtils.info("Kiểm tra danh sách gợi ý đã tự động ẩn sau khi click sản phẩm");
        search_page.checkSuggestBoxSearch2();

        LogUtils.info("Đợi trang chuyển hướng");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/catalogsearch/result?q=hhah8473hcfd"));

        LogUtils.info("Kiểm tra URL đúng");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/catalogsearch/result?q=hhah8473hcfd"), "URL sai: " + currentUrl);

        LogUtils.info("Kiểm tra tiêu đề kết quả: “Tìm thấy xxxx sản phẩm cho từ khoá 'xxx'");
        WebElement resultText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h1[contains(text(),'Tìm th')]")
        ));
        Assert.assertTrue(resultText.getText().toLowerCase().contains("hhah8473hcfd"));

        LogUtils.info("In ra số sản phẩm tìm thấy");
        String text = resultText.getText();
        LogUtils.info(">>> Kết quả: " + text);

        LogUtils.info("Kiểm tra hiển thị hình ảnh không tìm thấy");
        WebElement imageNoResult = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='no-result']//img")
        ));
        Assert.assertTrue(imageNoResult.isDisplayed(), "không hiển thị hình ảnh không tìm thấy");

        LogUtils.info("Kiểm tra hiển thị thông báo không tìm thấy");
        WebElement textNoResult = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(text(),'Không có kết quả bạn cần tìm')]")
        ));
        Assert.assertTrue(textNoResult.isDisplayed(), "Không hiển thị thông báo không tìm thấy");

    }

    @Test
    public void ClickProductSuggest() {
        LogUtils.info("Click vào sản phẩm gợi ý");
        search_page.ClickProductSuggest();

        // Kiểm tra danh sách đã biến mất
        LogUtils.info("Kiểm tra danh sách gợi ý đã tự động ẩn sau khi click sản phẩm");
        search_page.checkSuggestBoxSearch1();

        LogUtils.info("Đợi trang chuyển hướng");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("https://cellphones.com.vn/dien-thoai-samsung-galaxy-s25-ultra.html"));

        LogUtils.info("Kiểm tra URL đúng");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/dien-thoai-samsung-galaxy-s25-ultra.html"), "URL sai: " + currentUrl);

        LogUtils.info("Kiểm tra tiêu đề sản phẩm ");
        search_page.isTitleProductDisplayed();
        String title = driver.getTitle();
        LogUtils.info("Tiêu đề sản phẩm: " + title);
        Assert.assertTrue(search_page.isTitleProductDisplayed(), "Không hiển thị tiêu đề sản phẩm");

    }

    @Test
    public void ClickListSuggest() {
        LogUtils.info("Click vào danh sách gợi ý");
        search_page.ClickListSuggest();

        LogUtils.info("Đợi trang chuyển hướng");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("https://cellphones.com.vn/mobile/apple/iphone-16.html"));

        LogUtils.info("Kiểm tra hiển thị của slide");
        WebElement slide = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='block-top-sliding-banner is-flex']")
        ));
        Assert.assertTrue(slide.isDisplayed(), "Không hiển thị slide");

        LogUtils.info("Kiểm tra hiển thị list brand");
        WebElement listBrand = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='brands__content']//div[@class='list-brand']")
        ));
        Assert.assertTrue(listBrand.isDisplayed(), "Không hiển thị list brand");

        LogUtils.info("Kiểm tra hiển thị của danh sách sản phẩm");
        WebElement listProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='block-product-list-filter']")
        ));
        Assert.assertTrue(listProduct.isDisplayed(), "Không hiển thị danh sách sản phẩm");

        LogUtils.info("Kiểm tra sản phẩm có chứa chứa chữ trong List Suggest");
        WebElement firstProduct = driver.findElement(By.xpath("(//div[@class='product-info-container product-item'])[1]"));
        Assert.assertTrue(firstProduct.getText().toLowerCase().contains("iphone"));

    }

    @Test
    public void ClickInputSearch() {
        LogUtils.info("Click vào ô tìm kiếm");
        search_page.ClickInputSearch();

        LogUtils.info("Kiểm tra hiển thi danh sách sản phẩm gợi ý");
        search_page.isSuggestBoxSearchDisplayed();
        Assert.assertTrue(search_page.isLinkAdSearchDisplayed(), "Không hiển thị danh sách sản phẩm gợi ý");

        LogUtils.info("Kiểm tra click banner quảng cáo");
        search_page.ClickLinkAdSearch();
        String currentUrl = driver.getCurrentUrl();
        LogUtils.info("URL hiện tại: " + currentUrl);

    }

    @Test
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

        LogUtils.info("Kiểm tra hiển thị danh sách lịch sử tìm kiếm");
        search_page.isHistorySearchDisplayed();
        Assert.assertTrue(search_page.isHistorySearchDisplayed(), "Không hiển thị danh sách lịch sử tìm kiếm");

        LogUtils.info("Lấy danh sách các mục trong lịch sử tìm kiếm");
        List<String> actualHistory = search_page.getSearchHistoryItems();
        LogUtils.info("In ra danh sách lịch sử tìm kiếm");
        for (String item : actualHistory) {
            LogUtils.info(item);
        }

//        LogUtils.info("So sánh danh sách lịch sử tìm kiếm với dữ liệu mong đợi");
//        List<String> expectedHistory = Arrays.asList( "samsung galaxy", "iphone");
//        for (String expectedItem : expectedHistory) {
//            Assert.assertTrue(actualHistory.contains(expectedItem),
//                    "Không tìm thấy '" + expectedItem + "' trong danh sách lịch sử tìm kiếm");
//        }

        LogUtils.info("Kiểm tra nút Xóa lịch sử tìm kiếm");
        search_page.DeleteHistory();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(search_page.HistorySearch));

        List<WebElement> remaining = driver.findElements(search_page.HistorySearch);
        System.out.println("Số item còn lại: " + remaining.size());
        Assert.assertTrue(remaining.isEmpty(), "Lịch sử vẫn còn sau khi xóa");

        LogUtils.info("Kiểm tra hoàn tất - Lịch sử tìm kiếm hiển thị chính xác");
    }

    @Test
    public void TimeForSearch1() {
        LogUtils.info("Click vào ô tìm kiếm để kiểm tra thời gian hiển thị danh sách gợi ý");
        long startTimeClick = System.currentTimeMillis();

        search_page.ClickInputSearch();

        long endTimeClick = System.currentTimeMillis();
        long durationClick = endTimeClick - startTimeClick;

        LogUtils.info("Thời gian hiển thị danh sách gợi ý sau khi click: " + durationClick + " ms");

    }

    @Test
    public void TimeForSearch2() {
        LogUtils.info("Nhập từ khóa để kiểm tra thời gian hiển thị danh sách gợi ý");
        long startTimeInput = System.currentTimeMillis();

        search_page.inputSearch3("iphone 16");

        long endTimeInput = System.currentTimeMillis();
        long durationInput = endTimeInput - startTimeInput;

        LogUtils.info("Thời gian hiển thị danh sách gợi ý sau khi nhập: " + durationInput + " ms");

    }

    @Test
    public void DeleteSearch() {
        LogUtils.info("Click X trên thanh tìm kếm khi nhập từ khoá");
        search_page.SearchAndcloseSearch("iphone 16");

        LogUtils.info("Kiểm tra danh sách gợi ý sau khi click X");
        search_page.isSuggestBoxSearchDisplayed();
        Assert.assertTrue(search_page.isLinkAdSearchDisplayed(), "Không hiển thị danh sách sản phẩm gợi ý");

        LogUtils.info("Click X trên thanh tìm kiếm khi chưa nhập từ khoá");
        search_page.closeSearch();
        search_page.isSuggestBoxSearchDisplayed();
        Assert.assertFalse(search_page.isSuggestBoxSearchDisplayed(), "Hiển thị danh sách sản phẩm gợi ý");
    }

}