package com.cellphoneS.testcases;

import com.cellphoneS.bases.BaseSetup;
import com.cellphoneS.bases.SignIn_Helpers;
import com.cellphoneS.pages.Homepage_page;
import com.cellphoneS.pages.Search_Page;
import com.cellphoneS.pages.SignIn_Page;
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

public class Search_Test extends BaseSetup {
    private static final Logger log = LoggerFactory.getLogger(SignIn_Test.class);
    private WebDriver driver;
    public Search_Page search_page;
    public WebDriverWait wait;
    public ExcelUtils excelHelper;
    public ValidateUIHelper validateUIHelper;
    public SignIn_Helpers signIn_helpers;
    public Homepage_page homepage_page;


    @BeforeClass(groups = "Function")
    public void setUp() throws Exception {
        //gọi hàm khởi tạo properties
        Properties_File.setPropertiesFile();
        // Gọi lại hàm startRecord
        try {
            RecordVideo.startRecord("RecordVideo");
        }catch (Exception e){
            e.printStackTrace();
        }
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

    // Nó sẽ thực thi sau mỗi lần thực thi testcase (@Test)
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
    public void Search_Success() {
        LogUtils.info("Tìm kiếm với Iphone khi click icon Search");
        search_page.inputSearch("iphone");

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
        WebElement firstProduct = driver.findElement(By.xpath("(//div[@class='product-info-container product-item'])[1]"));
        Assert.assertTrue(firstProduct.getText().toLowerCase().contains("iPhone"));

    }
    @Test
    public void Search_Fail() {
        LogUtils.info("Tìm kiếm với Iphone khi click ENTER");
        search_page.inputSearch2("hhah8473hcfd");

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

        LogUtils.info("Đợi trang chuyển hướng");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("https://cellphones.com.vn/dien-thoai-samsung-galaxy-s25-ultra.html"));

        LogUtils.info("Kiểm tra URL đúng");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/dien-thoai-samsung-galaxy-s25-ultra.html"), "URL sai: " + currentUrl);

        LogUtils.info("Kiểm tra tiêu đề sản phẩm ");
        search_page.isTitleProductDisplayed();
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
        Assert.assertTrue(firstProduct.getText().toLowerCase().contains("iPhone"));

    }

    @Test
    public void ClickInputSearch() {
        LogUtils.info("Click vào ô tìm kiếm");
        search_page.ClickInputSearch();

        LogUtils.info("Kiểm tra hiển thi danh sách sản phẩm gợi ý");

    }

}
