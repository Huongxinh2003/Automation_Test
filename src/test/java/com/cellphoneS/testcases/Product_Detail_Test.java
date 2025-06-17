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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class Product_Detail_Test extends BaseSetup {

    private static final Logger log = LoggerFactory.getLogger(Search_Test.class);
    private WebDriver driver;
    public WebDriverWait wait;
    public ExcelUtils excelHelper;
    public ValidateUIHelper validateUIHelper;
    public Product_Detail_Page product_detail_page;
    public SignIn_Helpers signIn_helpers;
    public Homepage_page homepage_page;
    public Search_Page search_page;

    @BeforeClass
    public void setupDriver() throws Exception {
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
        excelHelper = new ExcelUtils();
        validateUIHelper = new ValidateUIHelper(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        signIn_helpers = new SignIn_Helpers(driver);
        homepage_page = signIn_helpers.SignIn(driver);
        search_page = homepage_page.openSearchPage();
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

    @BeforeMethod
    public void SearchProduct() {
        LogUtils.info("Thực hiện tìm kiếm sản phẩm 'iphone' và mở trang chi tiết");
        product_detail_page = search_page.openProductDetail("iphone");
    }

    @Test
    public void Search() {
        LogUtils.info("Kiểm tra hiển thị tiêu đề sản phẩm");
        product_detail_page.isTitleProductDisplayed();
        Assert.assertTrue(product_detail_page.isTitleProductDisplayed(), "Tiêu đề sản phẩm không hiển thị");
    }


}
