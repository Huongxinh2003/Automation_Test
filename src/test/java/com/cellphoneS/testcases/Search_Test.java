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
        LogUtils.info("Tìm kiếm với Iphone");
        search_page.inputSearch("iphone");

        // Đợi trang chuyển hướng
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/catalogsearch/result?q=iphone"));

        // Kiểm tra URL đúng
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/catalogsearch/result?q=iphone"), "URL sai: " + currentUrl);

        // Kiểm tra tiêu đề kết quả: “Tìm thấy xxxx sản phẩm cho từ khoá 'iphone'”
        // Kiểm tra tiêu đề kết quả: “Tìm thấy xxxx sản phẩm cho từ khoá 'iphone'”
        WebElement resultText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(text(),'Tìm thấy') and contains(text(),'sản phẩm cho từ khoá')]")
        ));
        Assert.assertTrue(resultText.getText().toLowerCase().contains("iphone"));

        // Kiểm tra sản phẩm đầu tiên có chứa chữ iPhone
        WebElement firstProduct = driver.findElement(By.cssSelector(".product.name.product-item-name"));
        Assert.assertTrue(firstProduct.getText().toLowerCase().contains("iphone"));

        // (Tùy chọn) In ra số sản phẩm tìm thấy
        String text = resultText.getText(); // ví dụ: "Tìm thấy 4142 sản phẩm cho từ khoá 'iphone'"
        LogUtils.info(">>> Kết quả: " + text);

    }

    public void Search_Fail() {
        LogUtils.info("Tìm kiếm với Iphone");
        search_page.inputSearch2("xzyyy123");


    }



}
