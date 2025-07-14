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
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

    @BeforeClass(groups = {"UI_Test", "Function", "Function_UI"})
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

    private String ProductName;
    private String ColorName;
    private String ColorPrice;
    private String ProductPrice;
    private String WarrantyActive;
    @BeforeMethod(groups = {"Function", "UI_Test", "Function_UI"})
    public void SearchProduct1() throws InterruptedException {
        driver.navigate().refresh();
        LogUtils.info("Thực hiện tìm kiếm sản phẩm 'iphone' và mở trang chi tiết");
        product_detail_page_cb = search_page_cb.openProductDetail("iphone");
        product_detail_page_cb.selectProductOptions();
        validateUIHelper.waitForPageLoaded();

        ProductName = product_detail_page_cb.getProductName();
        ColorName = product_detail_page_cb.getColorName();
        ColorPrice = product_detail_page_cb.getColorPrice();
        ProductPrice = product_detail_page_cb.getProductPrice();
        WarrantyActive = product_detail_page_cb.getActiveWarranty();

        checkout_page_cb = product_detail_page_cb.clickBuyNowToOpenPopup();

    }

    @Test(groups = "UI_Test", description = "Kiểm tra thông tin của trang chi tiết sản phẩm")
    public void verifyCheckoutPopup() {
        LogUtils.info("Kiểm tra popup trang chi tiết sản phẩm");
        checkout_page_cb.isPopupModalDisplayed();
        test.get().pass("Hiển thị popup thanh toán");

        LogUtils.info("Kiểm tra title của popup");
        checkout_page_cb.isTitlePopupModalDisplayed();
        test.get().pass("Hiển thị title của popup: " + checkout_page_cb.getTitlePopupModal());

        try {
            LogUtils.info("Kiểm tra tên sản phẩm");
            String ProductNamePopup = checkout_page_cb.getProductNamePopup();
            LogUtils.info("Title popup: " + ProductNamePopup);
            Assert.assertEquals(ProductNamePopup, ProductName);
            test.get().pass("Tên sản phẩm trong popup khớp");
        }catch (Exception e) {
            test.get().fail("Tên sản phẩm trong popup KHÔNG khớp");
        }

        String colorNamePopup = checkout_page_cb.getColorNamePopup();
        try {
            LogUtils.info("Kiểm tra tên màu");
            Assert.assertEquals(colorNamePopup, ColorName);
            test.get().pass("Tên màu khớp");
        }catch (Exception e) {
            test.get().fail("Tên màu KHÔNG khớp");
        }

        String colorPricePopup = checkout_page_cb.getColorPricePopup();
        try {
            LogUtils.info("Kiểm tra giá theo màu sắc");
            Assert.assertEquals(colorPricePopup, ColorPrice);
            test.get().pass("Giá theo màu khớp");
        }catch (Exception e) {
            test.get().fail("Giá theo màu KHÔNG khớp");
            test.get().info("Giá trong popup: " + colorPricePopup);
            test.get().info("Gía trong trang chi tiết sản phẩm: " + ColorPrice);
        }

        LogUtils.info("Kiểm tra tên màu trong mô tả sản phẩm");
        String SpanNameColor = checkout_page_cb.getSpanColor();
        if (SpanNameColor.toLowerCase().contains(colorNamePopup.toLowerCase())) {
            test.get().pass("Tên màu '" + colorNamePopup + "' có trong '" + SpanNameColor + "'");
        } else {
            test.get().fail("Tên màu '" + colorNamePopup + "' KHÔNG có trong '" + SpanNameColor + "'");
            Assert.fail("Tên màu không khớp với mô tả sản phẩm.");
        }

        try {
            LogUtils.info("Kiểm tra giá sản phẩm");
            String productPricePopup = checkout_page_cb.getProductPricePopup();
            Assert.assertEquals(productPricePopup, ProductPrice);
            test.get().pass("Giá sản phẩm khớp");
        }catch (Exception e) {
            test.get().fail("Giá sản phẩm KHÔNG khớp");
        }

        LogUtils.info("Kiểm tra bảo hành active bên ngoài và trong popup");
        String warrantyPopup = checkout_page_cb.getActiveWarrantyPopup();
        if (warrantyPopup.equalsIgnoreCase(WarrantyActive)) {
            test.get().pass("Bảo hành active bên ngoài và popup KHỚP: " + WarrantyActive);
        } else {
            test.get().fail("Bảo hành active KHÔNG khớp!");
            test.get().info("Bên ngoài: " + WarrantyActive);
            test.get().info("Trong popup: " + warrantyPopup);
            Assert.fail("Bảo hành active bên ngoài và trong popup không khớp.");
        }

        LogUtils.info("Kiểm tra tên bảo hành trong mô tả sản phẩm");
        String spanWarranty = checkout_page_cb.getSpanWarranty();
        if (spanWarranty.toLowerCase().contains(warrantyPopup.toLowerCase())) {
            test.get().pass("Tên bảo hành '" + warrantyPopup + "' có trong '" + spanWarranty + "'");
        }else {
            test.get().fail("Tên bảo hành '" + warrantyPopup + "' KHÔNG có trong '" + spanWarranty + "'");
            Assert.fail("Tên bảo hành không khớp với mô tả sản phẩm.");
        }
    }

    @Test(groups = "Function", description = "Kiểm tra bỏ trống nhập các thông tin")
    public void verifyCheckoutForm() {
        try {
            LogUtils.info(" Bỏ trống các trường thông tin trong popup");
            checkout_page_cb.inputName("");
            test.get().pass("Bỏ trống tên");

            checkout_page_cb.inputPhone("");
            test.get().pass("Bỏ trống sđt");

            checkout_page_cb.inputEmail("");
            test.get().pass("Bỏ trống email");

            LogUtils.info("Kiểm tra nút đặt hàng");
            checkout_page_cb.clickButtonBuy();
            test.get().pass("Click nút đặt hàng thành công");

            LogUtils.info("Kiểm tra bị highlight đỏ");
            WebElement nameField = checkout_page_cb.getInputNameElement();
            WebElement phoneField = checkout_page_cb.getInputPhoneElement();
            WebElement emailField = checkout_page_cb.getInputEmailElement();

            Assert.assertTrue("Tên KHÔNG bị highlight đỏ",
                    nameField.getAttribute("class").contains("input-error"));
            test.get().pass("Ô nhập tên bị highlight đỏ khi để trống");

            Assert.assertTrue("SĐT KHÔNG bị highlight đỏ",
                    phoneField.getAttribute("class").contains("input-error"));
            test.get().pass("Ô nhập SĐT bị highlight đỏ khi để trống");

            Assert.assertFalse("Email bị highlight đỏ",
                    emailField.getAttribute("class").contains("input-error"));
            test.get().pass("Ô nhập email không bị highlight đỏ khi để trống");

            LogUtils.info("Kiểm tra thống báo lỗi");
            String expectedError = "Vui lòng không bỏ trống thông tin!";
            String actualError = checkout_page_cb.getFailToast();
            Assert.assertEquals(actualError, expectedError);
            test.get().pass("Thông báo lỗi hiển thị đúng");
        }catch (Exception e) {
            test.get().fail("Thông báo lỗi hiển thị không đúng");
        }
    }

}
