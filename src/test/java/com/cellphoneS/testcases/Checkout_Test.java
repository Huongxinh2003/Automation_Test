package com.cellphoneS.testcases;

import com.base.BaseSetup;
import com.cellphoneS.helpers.SignIn_Helpers;
import com.cellphoneS.pages.*;
import com.helpers.CaptureHelpers;
import com.helpers.ValidateUIHelper;
import com.ultilities.ExcelUtils;
import com.ultilities.listeners.ReportListener;
import com.ultilities.logs.LogUtils;
import com.ultilities.Properties_File;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.time.Duration;

@Listeners(ReportListener.class)
public class Checkout_Test extends BaseSetup {

    private static final Logger log = LoggerFactory.getLogger(Checkout_Test.class);
    private WebDriver driver;
    public WebDriverWait wait;
    public ExcelUtils excelHelper;
    public ValidateUIHelper validateUIHelper;
    public Product_Detail_Page product_detail_page;
    public SignIn_Helpers signIn_helpers;
    public Homepage_page homepage_page;
    public Search_Page search_page;
    public Cart_Page cart_page;
    public JavascriptExecutor js;
    public Checkout_Page checkout_page;

    @BeforeClass
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
        search_page = homepage_page.openSearchPage();
        cart_page = new Cart_Page(driver);
        checkout_page = new Checkout_Page(driver);
        log.info("Đã mở trang tìm kiếm");
    }

    @BeforeMethod
    public void SearchProduct() {
        LogUtils.info("Thực hiện tìm kiếm sản phẩm 'iphone' và mở trang chi tiết");
        product_detail_page = search_page.openProductDetail("iphone");
        cart_page = product_detail_page.OpenCartPage();
        checkout_page = cart_page.openCheckout();
    }

    @Test
    public void verifyTitleCheckout() {
        LogUtils.info("Kiem tra title trang checkout");
        Checkout_Page.verifyTitleCheckout();
        LogUtils.info("Tiêu đề trang: " + Checkout_Page.getTitleCheckout());
    }

}
