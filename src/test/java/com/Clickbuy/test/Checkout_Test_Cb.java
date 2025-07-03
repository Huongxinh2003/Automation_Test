package com.Clickbuy.test;

import com.Clickbuy.helper.SignIn_Helper_Cb;
import com.Clickbuy.page.Checkout_Page_Cb;
import com.Clickbuy.page.Homepage_page_Cb;
import com.Clickbuy.page.Product_Detail_Page_Cb;
import com.Clickbuy.page.Search_Page_Cb;
import com.base.BaseSetup;
import com.helpers.ValidateUIHelper;
import com.ultilities.ExcelUtils;
import com.ultilities.Properties_File;
import com.ultilities.listeners.ReportListener;
import com.ultilities.logs.LogUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.Duration;

@Listeners(ReportListener.class)
public class Checkout_Test_Cb extends BaseSetup {
    private static final Logger log = LoggerFactory.getLogger(Checkout_Test_Cb.class);
    private WebDriver driver;
    public WebDriverWait wait;
    public ExcelUtils excelHelper;
    public ValidateUIHelper validateUIHelper;
    public Product_Detail_Page_Cb product_detail_page_cb;
    public Homepage_page_Cb homepage_page_cb;
    public Search_Page_Cb search_page_cb;
    public SignIn_Helper_Cb signIn_helpers_cb;
    public Checkout_Page_Cb checkout_page_cb;

    @BeforeClass(groups = {"UI_Test", "Function","Function_UI"})
    public void setupDriver() throws Exception {
        //gọi hàm khởi tạo properties
        Properties_File.setPropertiesFile();
        // Lấy driver từ class cha BaseSetup
        boolean headless = Boolean.parseBoolean(Properties_File.getPropValue("headless"));
        driver = setupDriver(Properties_File.getPropValue("browser"), headless);
        excelHelper = new ExcelUtils();
        signIn_helpers_cb = new SignIn_Helper_Cb(driver);
        validateUIHelper = new ValidateUIHelper(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        homepage_page_cb = signIn_helpers_cb.SignIn(driver);
        search_page_cb = homepage_page_cb.openSearchPage();
        product_detail_page_cb = new Product_Detail_Page_Cb(driver);
        checkout_page_cb = new Checkout_Page_Cb(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private String TitleProduct;
    @BeforeMethod(groups = {"Function", "UI_Test", "Function_UI"})
    public void SearchProduct1() {
        LogUtils.info("Thực hiện tìm kiếm sản phẩm 'iphone' và mở trang chi tiết");
        product_detail_page_cb = search_page_cb.openProductDetail("iphone");
        checkout_page_cb = product_detail_page_cb.openCheckoutPage();
        TitleProduct = product_detail_page_cb.getTitleProduct();
    }

    @Test(groups = "UI_Test",description = "Kiểm tra thông tin của trang chi tiết sản phẩm")
    public void verifyCheckoutPopup(){
        LogUtils.info("Kiểm tra popup trang chi tiết sản phẩm");
        checkout_page_cb.isPopupModalDisplayed();
        test.get().pass("Hiển thị popup thanh toán");

        LogUtils.info("Kiểm tra title của popup");
        checkout_page_cb.isTitlePopupModalDisplayed();
        test.get().pass("Hiển thị title của popup" + checkout_page_cb.getTitlePopupModal());

    }
}
