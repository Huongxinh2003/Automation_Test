package com.cellphoneS.testcases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.base.BaseTest;
import com.cellphoneS.helpers.SignIn_Helpers;
import com.cellphoneS.pages.*;
import com.helpers.CaptureHelpers;
import com.helpers.ValidateUIHelper;
import com.ultilities.ExcelUtils;
import com.ultilities.extentreports.ExtentManager;
import com.ultilities.listeners.ReportListener;
import com.ultilities.Properties_File;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.time.Duration;

import static com.ultilities.extentreports.ExtentManager.extent;

@Listeners(ReportListener.class)
public class Checkout_Test extends BaseTest {

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
        ExtentReports extent = ExtentManager.getExtentReports();
        log.info("Đã mở trang tìm kiếm");
    }

    private String productCityName;
    @BeforeMethod
    public void SearchProduct() {
        test.get().info("Thực hiện tìm kiếm sản phẩm 'iphone' và mở trang chi tiết");
        product_detail_page = search_page.openProductDetail("iphone");
        productCityName = product_detail_page.getCityName();
        cart_page = product_detail_page.OpenCartPage();
        checkout_page = cart_page.openCheckout();
    }

    @Test
    public void verifyTitleCheckout() {
        test.get().info("Kiem tra title trang checkout");
        Checkout_Page.verifyTitleCheckout();
        test.get().info("Tiêu đề trang: " + Checkout_Page.getTitleCheckout());
    }

    @Test
    public void verifyDisplayCheckout() {
        test.get().info("Kiểm tra sản phẩm chuyen sang trang Thông tin");
        String activeTab = checkout_page.getTabInfoActive();
        Assert.assertTrue(activeTab.contains("THÔNG TIN"), "Tab không phải 'THÔNG TIN'");

        test.get().info("Kiểm tra hiển thị Card sản phâm");
        Assert.assertTrue(checkout_page.isCheckoutCartDisplayed(), "Card sản phần không hiển thị");

        test.get().info("Kiểm tra check box ưu đãi từ CellphoneS");
        checkout_page.isSelectedCheckboxPromotion();

        test.get().info("Kiểm tra thông tin tự động điền");
        String expectedName = "Phùng Hương";
        String expectedPhone = "0332019523";
        String expectedEmail = "quynhhuong6319@gmail.com";

//        Assert.assertEquals(checkout_page.getNameCustomer(), expectedName, "Tự động điền tên người dùng sai");
//        Assert.assertEquals(checkout_page.getPhoneCustomer(), expectedPhone, "Tự động điền sđt sai");
//        Assert.assertEquals(checkout_page.getInputEmail(),  expectedEmail, "Tự động điền email sai");

        test.get().info("Kiểm tra tên khách hàng");
        String actualName = checkout_page.getNameCustomer();
        if (actualName.equals(expectedName)) {
            test.get().pass("Tên chính xác: " + actualName);
        } else {
            test.get().fail("Tên không đúng. Thực tế: " + actualName + ", Kỳ vọng: " + expectedName);
        }

        test.get().info("Kiểm tra sđt khách hàng");
        String actualPhone = checkout_page.getPhoneCustomer();
        if (actualPhone.equals(expectedPhone)) {
            test.get().pass("SĐT chính xác: " + actualPhone);
        } else {
            test.get().fail("SĐT không đúng. Thực tế: " + actualPhone + ", Kỳ vọng: " + expectedPhone);
        }

        test.get().info("Kiểm tra email khách hàng");
        String actualEmail = checkout_page.getInputEmail();
        if (actualEmail.equals(expectedEmail)) {
            test.get().pass("Email chính xác: " + actualEmail);
        } else {
            test.get().fail("Email không đúng. Thực tế: " + actualEmail + ", Kỳ vọng: " + expectedEmail);
        }

    }

    @Test
    public void verifyPaymennInfo() {
        test.get().info("Kiểm tra thống tin 'Nhận hàng tại cửa hàng'");
        checkout_page.isSelectedCheckboxPickup();
        if (checkout_page.isSelectedCheckboxPickup()) {
            test.get().pass("Checkbox 'Nhận tại cửa hàng' đã được chọn.");
        } else {
            test.get().fail("Checkbox 'Nhận tại cửa hàng' chưa được chọn.");
        }

        test.get().info("Kiểm tra hiển thị cu form 'Nhận tại cửa hàng'");
        Assert.assertTrue(checkout_page.isDropDownCityDisplayed(), "Dropdown city không hiển thị");
        Assert.assertTrue(checkout_page.isDropDownDistrictDisplayed(), "Dropdown district không hiển thị");
        Assert.assertTrue(checkout_page.isDropDownAddressDisplayed(), "Dropdown address không hiển thị");
        Assert.assertTrue(checkout_page.isInputNoteDisplayed(), "Input note không hiển thị");

//        test.get().info("So sánh tên thành phố trong product detail và checkout");
//        String checkoutCityName = checkout_page.getCityName();
//        Assert.assertEquals(checkoutCityName,productCityName, "Thành phố được chọn khác nhau");

        test.get().info("Kiểm tra nhâp thống tin 'Nhận tại cửa hàng'");
        checkout_page.SendKeysCity("Hà Nội");
//        checkout_page.ClickButtonAgree();
        checkout_page.SendKeysDistrict("Huyện Gia Lâm");
        checkout_page.SendKeysAddress("51 Ngô Xuân Quảng, Thị trấn Trâu Quỳ, Huyện Gia Lâm, Hà Nội");
//        checkout_page.SendKeysInputNote("Tới nhận hàng ngày 28/07/2025");

        test.get().info("Chuyển sang tab 'Thanh toán'");
        checkout_page.ClickButtonCheckout();

        test.get().info("Chuyển sang tab 'Thanh toán' thành công");

    }

    @Test
    public void verifyPaymennInfo2() {
        test.get().info("Kiểm tra thống tin 'Giao hàng tận nơi'");
        checkout_page.ClickCheckboxShip();

        String expectedName = "Phùng Hương";
        String expectedPhone = "0332019523";

        test.get().info("Kiểm tra tên khách hàng");
        if (checkout_page.getInputName().equals(expectedName)) {
            test.get().pass("Tên khách hàng chính xác: " + checkout_page.getInputName());
        } else {
            test.get().fail("Tên khách hàng không chính xác. Thực tế: " + checkout_page.getInputName() + ", Kỳ vọng: " + expectedName);
        }

        test.get().info("Kiểm tra sđt khách hàng");
        if (checkout_page.getInputPhone().equals(expectedPhone)) {
            test.get().pass("SĐT khách hàng chính xác: " + checkout_page.getInputPhone());
        } else {
            test.get().fail("SĐT khách hàng không chính xác. Thực tế: " + checkout_page.getInputPhone() + ", Kỳ vọng: " + expectedPhone);
        }

        test.get().info("Kiểm tra hiển thị cu form 'Giao hàng tận nơi'");
        Assert.assertTrue(checkout_page.isDropdownCityShippingDisplayed(), "Dropdown city không hiển thị");
        Assert.assertTrue(checkout_page.isDropdownDistrictShippingDisplayed(), "Dropdown district không hiển thị");
        Assert.assertTrue(checkout_page.isDropdownAddressShippingDisplayed(), "Dropdown address không hiển thị");
        Assert.assertTrue(checkout_page.isInputHomeNumberDisplayed(), "Input home number não hiển thị");
        Assert.assertTrue(checkout_page.isInputNoteShippingDisplayed(), "Input note não hiển thị");

        test.get().info("Kiểm tra nhâp thống tin 'Giao hàng tận nơi'");
        checkout_page.SendKeysCityShipping("Hà Nội");
        checkout_page.SendKeysDistrictShipping("Huyện Gia Lâm");
        checkout_page.SendKeysAddressShipping("Thị trấn Trâu Quỳ");
        checkout_page.SendKeysInputHomeNumber("51 Ngô Xuân Quảng");
        checkout_page.SendKeysInputNoteShipping("ship hàng ngày 28/07/2025");

        test.get().info("Chuyển sang tab 'Thanh toán'");
        checkout_page.ClickButtonCheckout();

        test.get().info("Chuyển sang tab 'Thanh toán' thành công");

    }

}
