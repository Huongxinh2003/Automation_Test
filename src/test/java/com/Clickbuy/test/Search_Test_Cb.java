package com.Clickbuy.test;

import com.Clickbuy.helper.SignIn_Helper_Cb;
import com.Clickbuy.page.Homepage_page_Cb;
import com.Clickbuy.page.Search_Page_Cb;
import com.base.BaseSetup;
import com.helpers.ValidateUIHelper;
import com.ultilities.ExcelUtils;
import com.ultilities.Properties_File;
import com.ultilities.listeners.ReportListener;
import com.ultilities.logs.LogUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
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
public class Search_Test_Cb extends BaseSetup {
    private static final Logger log = LoggerFactory.getLogger(Search_Test_Cb.class);
    private WebDriver driver;
    public Search_Page_Cb search_page_cb;
    public WebDriverWait wait;
    public ExcelUtils excelHelper;
    public ValidateUIHelper validateUIHelper;
    public SignIn_Helper_Cb signIn_helpers_cb;
    public Homepage_page_Cb homepage_page_Cb;
    public Product_Detail_Test_Cb product_detail_page_cb;

    @BeforeClass(groups = {"UI_Test", "Function"})
    public void setUp() throws Exception {
        Properties_File.setPropertiesFile();
        boolean headless = Boolean.parseBoolean(Properties_File.getPropValue("headless"));
        driver = setupDriver(Properties_File.getPropValue("browser"), headless);
        search_page_cb = new Search_Page_Cb(driver);
        excelHelper = new ExcelUtils();
        validateUIHelper = new ValidateUIHelper(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        signIn_helpers_cb = new SignIn_Helper_Cb(driver);
        homepage_page_Cb = signIn_helpers_cb.SignIn(driver);
        log.info("Đã mở trang tìm kiếm");
    }

    @BeforeMethod(groups = {"UI_Test", "Function"})
    public void SearchProduct() {
        LogUtils.info("Thực hiện tìm kiếm sản phẩm 'iphone'");
        search_page_cb = homepage_page_Cb.openSearchPage();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Test(groups = "UI_Test")
    public void suggestBox(){
        LogUtils.info("Tìm kiếm với Iphone khi click icon Search");
        search_page_cb.inputSearch2("iphone");

        LogUtils.info("Kiểm tra mục Có phải bạn muốn tìm hiển thị");
        search_page_cb.isBoxCoPhaiBanMuonTimDisplayed();
        Assert.assertTrue(search_page_cb.isBoxCoPhaiBanMuonTimDisplayed(), "Không hiển thị mục Có phải bạn muốn tìm");

        LogUtils.info("Kiểm tra mục Sản phẩm gợi ý hiển thị");
        search_page_cb.isBoxSanPhamGoiYDisplayed();
        Assert.assertTrue(search_page_cb.isBoxSanPhamGoiYDisplayed(), "Không hiển thị mục Sản phẩm gợi ý");

        LogUtils.info("Kiểm tra click vào từ khoá trong mục \"Có phải bạn muốn tìm\"");
        search_page_cb.clickLinkCoPhaiBanMuonTim();
        List<WebElement> productNames = driver.findElements
                (By.xpath("//div[@class='list-products']//h3[@class='title_name']"));
        String keyword = "iPhone 15";

        boolean found = false;
        for (WebElement title : productNames) {
            if (title.getText().toLowerCase().contains("iphone 15")) {
                found = true;
                break;
            }
        }
        Assert.assertTrue(found, "Không có sản phẩm nào chứa từ khoá 'iPhone 15' trong tiêu đề.");


        LogUtils.info("Kiểm tra click vào từ khoá trong mục \"Sản phẩm gợi ý\"");
        search_page_cb.clickInputSearch();
        search_page_cb.inputSearch2("iphone");
        search_page_cb.clickLinkSanPhamGoiY();

        LogUtils.info("Kiểm tra tiêu đề sản phẩm");
        String title = driver.getTitle();
        LogUtils.info("Tiêu đề sản phẩm: " + title);
    }

    @Test (groups = "Function")
    public void search_Success() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        LogUtils.info("Tìm kiếm với Iphone khi click icon Search");
        search_page_cb.inputSearch1("iphone");
        test.get().info("Tìm kiếm với Iphone khi click icon Search");

        LogUtils.info("Kiểm tra danh sách gợi ý đã tự động ẩn sau khi click sản phẩm");
        search_page_cb.isSuggestBoxDisplayed();

        LogUtils.info("Đợi trang chuyển hướng");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Boolean url = wait.until(ExpectedConditions.urlContains("/tim-kiem?key=iphone"));

        LogUtils.info("Kiểm tra URL đúng");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(url, "URL sai: " + currentUrl);

        LogUtils.info("Kiểm tra tiêu đề kết quả");
        WebElement resultText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                Search_Page_Cb.ResultTitle));
        Assert.assertTrue(resultText.getText().toLowerCase().contains("iphone"));

        LogUtils.info("In ra số sản phẩm tìm thấy");
        String text = resultText.getText();
        LogUtils.info(">>> Kết quả: " + text);

        LogUtils.info("Kiểm tra hiển thị danh sách sản phẩm");
        WebElement listProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='listdata']")
        ));
        Assert.assertTrue(listProduct.isDisplayed(), "Không hiển thị danh sách sản phẩm");

        LogUtils.info("Kiểm tra sản phẩm đầu tiên có chứa chữ iPhone");
        WebElement firstProduct = driver.findElement(By.xpath("//div[@class='list-products']//h3[@class='title_name']"));
        Assert.assertTrue(firstProduct.getText().toLowerCase().contains("iphone"));

        test.get().info("Đã kiểm tra thành công");
    }

    @Test(groups = "Function", description = "Kiểm tra tìm kiếm với từ khoá không hợp lệ")
    public void search_Fail() {
        LogUtils.info("Tìm kiếm với fail khi click ENTER");
        search_page_cb.inputSearch1("hhah8473hcfd");

        validateUIHelper.waitForPageLoaded();

        LogUtils.info("Kiểm tra tiêu đề kết quả");
        search_page_cb.isSubTitleSearchFailDisplayed();
        Assert.assertTrue(search_page_cb.isSubTitleSearchFailDisplayed(), "Không hiển thị tiêu đề kết quả");
        search_page_cb.isTitleSearchFailDisplayed();
        Assert.assertTrue(search_page_cb.isTitleSearchFailDisplayed(), "Không hiển thị tiêu đề kết quả");

//        LogUtils.info("Kiểm tra click button quay về trang chủ");
//        search_page_cb.clickButtonGoHomePage();
//        Boolean url1 = wait.until(ExpectedConditions.urlToBe("https://clickbuy.com.vn/"));
//        Assert.assertEquals(url1,"Không chuyển về trang Homepage");
//        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        search_page_cb.clickInputSearch();
//        search_page_cb.inputSearch3("hhah8473hcfd");

        LogUtils.info("Kiểm tra chuyển trang sau khi đồng hồ đếm ngược ở button kêt thúc");
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlToBe("https://cellphones.com.vn/"),
                    ExpectedConditions.visibilityOfElementLocated(Search_Page_Cb.LogoClickbuy)
            ));
            test.get().pass("Đã tự động trở về trang chủ sau khi countdown.");
        } catch (TimeoutException e) {
            test.get().fail("Không trở về trang chủ.");
        }
        test.get().info("Đã kiểm tra thành công");
    }
}
