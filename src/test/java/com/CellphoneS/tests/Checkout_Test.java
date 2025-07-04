package com.CellphoneS.tests;

import com.aventstack.extentreports.ExtentReports;
import com.base.BaseSetup;
import com.CellphoneS.helpers.SignIn_Helpers;
import com.CellphoneS.pages.*;
import com.helpers.ValidateUIHelper;
import com.ultilities.ExcelUtils;
import com.ultilities.extentreports.ExtentManager;
import com.ultilities.listeners.ReportListener;
import com.ultilities.Properties_File;
import com.ultilities.logs.LogUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

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

    @BeforeClass(groups = {"Function", "UI_Test", "Function_UI"})
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
    @BeforeMethod(groups = {"Function", "UI_Test", "Function_UI"})
    public void SearchProduct() {
        test.get().info("Thực hiện tìm kiếm sản phẩm 'iphone' và mở trang chi tiết");
        product_detail_page = search_page.openProductDetail("iphone");
        productCityName = product_detail_page.getCityName();
        cart_page = product_detail_page.OpenCartPage();
        checkout_page = cart_page.openCheckout();
    }

    @Test(groups= "UI_Test", priority = 1, description = "Kiểm tra title trang checkout")
    public void verifyTitleCheckout() {
        LogUtils.info("Kiem tra title trang checkout");
        checkout_page.verifyTitleCheckout();
        test.get().info("Tiêu đề trang: " + Checkout_Page.getTitleCheckout());
    }

    @Test (groups= "Function_UI",priority = 1, description = "Kiểm tra hiển thị và các chức " +
            "năng trên trang checkout")
    public void verifyDisplayTabInfo() {
        LogUtils.info("Kiểm tra sản phẩm chuyển sang trang Thông tin");
        String activeTab = checkout_page.getTabInfoActive();
        Assert.assertTrue(activeTab.contains("THÔNG TIN"), "Tab không phải 'THÔNG TIN'");

        LogUtils.info("Kiểm tra hiển thị Card sản phâm");
        Assert.assertTrue(checkout_page.isCheckoutCartDisplayed(),
                "Card sản phần không hiển thị");

        test.get().info("Kiểm tra check box ưu đãi từ CellphoneS");
        checkout_page.isSelectedCheckboxPromotion();

        LogUtils.info("Kiểm tra thông tin tự động điền");
        String expectedName = "Phùng Hương";
        String expectedPhone = "0332019523";

        Assert.assertEquals(checkout_page.getNameCustomer(), expectedName,
                "Tự động điền tên người dùng sai");
        Assert.assertEquals(checkout_page.getPhoneCustomer(), expectedPhone,
                "Tự động điền sđt sai");

        test.get().info("Kiểm tra tên khách hàng");
        String actualName = checkout_page.getNameCustomer();
        if (actualName.equals(expectedName)) {
            test.get().pass("Tên chính xác: " + actualName);
        } else {
            test.get().fail("Tên không đúng. Thực tế: " + actualName +
                    ", Kỳ vọng: " + expectedName);
        }

        test.get().info("Kiểm tra sđt khách hàng");
        String actualPhone = checkout_page.getPhoneCustomer();
        if (actualPhone.equals(expectedPhone)) {
            test.get().pass("SĐT chính xác: " + actualPhone);
        } else {
            test.get().fail("SĐT không đúng. Thực tế: " + actualPhone + ", " +
                    "Kỳ vọng: " + expectedPhone);
        }
        LogUtils.info("Kiểm tra nhập Email mới");
        checkout_page.SendKeysEmail("huongcan6319@gmail.com");
        test.get().pass("Thành công nhập Email mới");

        LogUtils.info("Kiểm tra click checkbox nhận ưu đãi");
        checkout_page.clickCheckboxPromo();
        test.get().pass("Thành công click checkbox nhận ưu đãi");
    }

    //Chuyển về thành phố Hà Nội
    @Test (groups = "Function",priority = 1, description = "Kiểm tra nhập thống tin 'Nhận hàng tại cửa hàng'")
    public void verifyPaymentInfo() throws Exception {
       LogUtils.info("Kiểm tra thống tin 'Nhận hàng tại cửa hàng'");
        checkout_page.isSelectedCheckboxPickup();
        if (checkout_page.isSelectedCheckboxPickup()) {
            test.get().pass("Checkbox 'Nhận tại cửa hàng' đã được chọn.");
        } else {
            test.get().fail("Checkbox 'Nhận tại cửa hàng' chưa được chọn.");
        }

        LogUtils.info("Kiểm tra hiển thị của form 'Nhận tại cửa hàng'");
        Assert.assertTrue(checkout_page.isDropDownCityDisplayed(), "Dropdown city không hiển thị");
        Assert.assertTrue(checkout_page.isDropDownDistrictDisplayed(), "Dropdown district không hiển thị");
        Assert.assertTrue(checkout_page.isDropDownAddressDisplayed(), "Dropdown address không hiển thị");
        Assert.assertTrue(checkout_page.isInputNoteDisplayed(), "Input note không hiển thị");

        LogUtils.info("Kiểm tra nhâp thống tin 'Nhận tại cửa hàng'");
        checkout_page.SendKeysCity("Hồ Chí Minh");
        checkout_page.ClickButtonAgree();
        excelHelper.setExcelFile("src/test/resources/TestData.xlsx", "Checkout");
        checkout_page.SendKeysDistrict(excelHelper.getCellData("District", 1));
        checkout_page.SendKeysAddress(excelHelper.getCellData("Address",1));
        checkout_page.SendKeysInputNote(excelHelper.getCellData("Note",1));
        checkout_page.CLickInputVatNo();
        checkout_page.ClickButtonCheckout();
        test.get().pass("Chuyển sang tab 'Thanh toán' thành công");
    }

    //Chuyển về thành phố Hà Nội
    @Test (groups = "Function",priority = 2, description = "Kiểm tra nhập thống tin 'Giao hàng tận nơi'")
    public void verifyPaymennInfo2() throws Exception {
        LogUtils.info("Kiểm tra thống tin 'Giao hàng tận nơi'");
        checkout_page.ClickCheckboxShip();

        LogUtils.info("Thông tin khách hàng mong đợi");
        String expectedName = "Phùng Hương";
        String expectedPhone = "0332019523";

        LogUtils.info("Kiểm tra tên khách hàng");
        if (checkout_page.getInputName().equals(expectedName)) {
            test.get().pass("Tên khách hàng chính xác: " + checkout_page.getInputName());
        } else {
            test.get().fail("Tên khách hàng không chính xác. Thực tế: " + checkout_page.getInputName() +
                    ", Kỳ vọng: " + expectedName);
        }

       LogUtils.info("Kiểm tra sđt khách hàng");
        if (checkout_page.getInputPhone().equals(expectedPhone)) {
            test.get().pass("SĐT khách hàng chính xác: " + checkout_page.getInputPhone());
        } else {
            test.get().fail("SĐT khách hàng không chính xác. Thực tế: " + checkout_page.getInputPhone() +
                    ", Kỳ vọng: " + expectedPhone);
        }

        LogUtils.info("Kiểm tra hiển thị cu form 'Giao hàng tận nơi'");
        Assert.assertTrue(checkout_page.isDropdownCityShippingDisplayed(), "Dropdown city không hiển thị");
        Assert.assertTrue(checkout_page.isDropdownDistrictShippingDisplayed(), "Dropdown district không hiển thị");
        Assert.assertTrue(checkout_page.isDropdownAddressShippingDisplayed(), "Dropdown address không hiển thị");
        Assert.assertTrue(checkout_page.isInputHomeNumberDisplayed(), "Input home number không hiển thị");
        Assert.assertTrue(checkout_page.isInputNoteShippingDisplayed(), "Input note não hiển thị");

        LogUtils.info("Kiểm tra nhâp thống tin 'Giao hàng tận nơi'");
        checkout_page.SendKeysCityShipping("Hồ Chí Minh");
        checkout_page.ClickButtonAgree();
        excelHelper.setExcelFile("src/test/resources/TestData.xlsx", "Checkout");
        checkout_page.SendKeysDistrict(excelHelper.getCellData("District", 1));
        checkout_page.SendKeysAddress(excelHelper.getCellData("Address2",1));
        checkout_page.SendKeysInputHomeNumber(excelHelper.getCellData("HomeNumber",1));
        checkout_page.SendKeysInputNote(excelHelper.getCellData("Note",1));
        checkout_page.CLickInputVatNo();

        test.get().info("Chuyển sang tab 'Thanh toán'");
        checkout_page.ClickButtonCheckout();

        test.get().pass("Kiểm tra nhập thống tin 'Giao hàng tận nơi' thành công");
    }

    @Test (groups = "UI_Test", priority = 2, description = "Kiểm tra đồng bộ giá sản phẩm trên trang thanh toán")
    public void verifyPrice() {
        test.get().info("Giá sản phẩm trên Card là: "+ checkout_page.getPriceCard());
        test.get().info("Giá sản phẩm trên thanh toán là: "+ checkout_page.getPriceTemp());
        LogUtils.info("So sánh giá sản phẩm trên trang thanh toán");
        Assert.assertEquals(checkout_page.getPriceCard(),checkout_page.getPriceTemp(),"Giá sản phẩm không khớp");

        test.get().pass("Kiểm tra đông bộ giá sản phẩm trên trang thanh toán thành công");
    }

    //Măc định chọn HN
    @Test (groups = "Function",priority = 3,description = "Kiểm tra chuyển sang tab 'Thanh toán'")
    public void verifySwitchTab() throws Exception {
        LogUtils.info("Nhập thông tin");
        checkout_page.SendKeysCity("Hồ Chí Minh");
        checkout_page.ClickButtonAgree();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        excelHelper.setExcelFile("src/test/resources/TestData.xlsx", "Checkout");
        checkout_page.SendKeysDistrict(excelHelper.getCellData("District", 1));
        checkout_page.SendKeysAddress(excelHelper.getCellData("Address",1));
        checkout_page.SendKeysInputNote(excelHelper.getCellData("Note",1));
        checkout_page.CLickInputVatNo();

        LogUtils.info("Kiểm tra chuyển sang tab 'Thanh toán'");
        checkout_page.ClickButtonCheckout();
        String activeTab = checkout_page.getTabPaymentActive();
        Assert.assertTrue(activeTab.contains("THANH TOÁN"),"Chưa chuyển sang tab 'Thanh toán'");

        test.get().pass("Kiem tra chuyển sang tab 'Thanh toán' thành công");

    }

    //mặc định chọnu HN
    @Test (groups = "UI_Test", priority = 3, description = "Kiểm tra hiển thị thông tin bên trang thanh toán")
    public void verifyTabPayment() throws Exception {
        int quantityInfo = Integer.parseInt(checkout_page.getProductQuantityInfo());
        String BasePriceInfo = checkout_page.getBasePrice();
        LogUtils.info("Nhập thông tin");
        checkout_page.SendKeysCity("Hồ Chí Minh");
        checkout_page.ClickButtonAgree();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        excelHelper.setExcelFile("src/test/resources/TestData.xlsx", "Checkout");
        checkout_page.SendKeysDistrict(excelHelper.getCellData("District", 1));
        checkout_page.SendKeysAddress(excelHelper.getCellData("Address",1));
        checkout_page.SendKeysInputNote(excelHelper.getCellData("Note",1));
        checkout_page.CLickInputVatNo();
        LogUtils.info("Kiểm tra chuyển sang tab 'Thanh toán'");
        checkout_page.ClickButtonCheckout();

        LogUtils.info("Kiểm tra nhập sai mã giảm giá");
        checkout_page.SendkeysDiscountCode("123456");
        checkout_page.ClickButtonApply();
        checkout_page.ClickButtonApply1();

        LogUtils.info("Kiểm tra thông báo hiển thị");
        String expectedMessage = "Mã giảm giá không khả dụng. Vui lòng kiểm tra lại.";
        String actualMessage = checkout_page.getToastMessageCode();
        if(expectedMessage.equals(actualMessage)){
            test.get().pass("Thông báo hiển thị đúng");
        }else {
            test.get().fail("Thông báo hiển thị sai");
        }

        LogUtils.info("Kiểm tra đồng bộ số lượng sản phẩm");
        String quantityPayment = checkout_page.getProductQuantityPayment().replaceAll("[^0-9]", "");
        if (quantityPayment.equals(quantityInfo)) {
            test.get().pass("Số lượng sản phẩm đồng bộ");
        }else {
            test.get().fail("Số lượng sản phẩm không đồng bộ");
        }

        LogUtils.info("Kiểm tra đồng bộ giá niêm yết");
        String BasePricePayment = checkout_page.getBasePriceProduct();
        if (BasePriceInfo.equals(BasePricePayment)) {
            test.get().pass("Giá niêm yết đồng bộ");
        }else {
            test.get().fail("Giá niêm yết không đồng bộ");
        }

        LogUtils.info("Kiểm tra hiển thị phí vận chuyển");
        checkout_page.getTotalShipping();
        test.get().info("Phí vận chuyển là: " + checkout_page.getTotalShipping());

        LogUtils.info("Kiểm tra số tiền được giảm giá");
        checkout_page.getDiscountPrice();
        test.get().info("Số tiền được giảm giá là: " + checkout_page.getDiscountPrice());

        LogUtils.info("Kiểm tra tổng tiền thanh toán");
        checkout_page.getTotalPrice();
        String BasePrice = checkout_page.getBasePriceProduct().replaceAll("[^0-9]", "");
        String DiscountPrice = checkout_page.getDiscountPrice().replaceAll("[^0-9]", "");
        String TotalPrice = checkout_page.getTotalPrice().replaceAll("[^0-9]", "");

        LogUtils.info("Chuyển sang kiểu long để tính toán");
        long basePrice = Long.parseLong(BasePrice);
        long discountPrice = Long.parseLong(DiscountPrice);
        long displayedTotalPrice = Long.parseLong(TotalPrice);

        // Tính toán tổng tiền thanh toán
        long calculatedTotalPrice = basePrice - discountPrice;
        test.get().info("Tổng tiền thanh toán là: " + calculatedTotalPrice);

        if (calculatedTotalPrice == displayedTotalPrice) {
            test.get().pass("Tổng tiền thanh toán khớp: " + calculatedTotalPrice);
        } else {
            test.get().fail("Tổng tiền không khớp. Tính được: " + calculatedTotalPrice +
                    " - Trang hiển thị: " + displayedTotalPrice);
        }

        test.get().pass("Kiểm tra đôồng bộ thành công");
    }


    //mặc định chọnu HN
    @Test (groups = "Function",priority = 4, description = "Kiểm tra chọn phương thức thanh toán")
    public void selectPaymentMethod() throws Exception {
        LogUtils.info("Nhập thông tin");
        checkout_page.SendKeysCity("Hồ Chí Minh");
        checkout_page.ClickButtonAgree();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        excelHelper.setExcelFile("src/test/resources/TestData.xlsx", "Checkout");
        checkout_page.SendKeysDistrict(excelHelper.getCellData("District", 1));
        checkout_page.SendKeysAddress(excelHelper.getCellData("Address",1));
        checkout_page.SendKeysInputNote(excelHelper.getCellData("Note",1));
        checkout_page.CLickInputVatNo();
        LogUtils.info("Kiểm tra chuyển sang tab 'Thanh toán'");
        checkout_page.ClickButtonCheckout();

        LogUtils.info("Kiểm tra chọn phương thức thanh toán");
        checkout_page.ClickPaymentMethod();
        checkout_page.ClickShopeePay();
        checkout_page.isTickPaymentMethodDisplayed();
        Assert.assertTrue(checkout_page.isTickPaymentMethodDisplayed(),"Chưa chọn phương thức thanh toán");
        checkout_page.ClickApplyPaymentMethod();

        LogUtils.info("Kiểm tra thông báo");
        checkout_page.getToastMessageCode();
        String expectedMessage = "Thêm phương thức thanh toán thành công.";
        String actualMessage = checkout_page.getToastMessageCode();
        if(expectedMessage.equals(actualMessage)){
            test.get().pass("Thông báo hiển thị đúng");
        }else {
            test.get().fail("Thông báo hiển thị sai");
        }

        test.get().pass("Kiểm tra chọn phương thức thanh toán thành công");
    }

    //Mặc định ở HN
    @Test (groups = "UI_Test",priority = 4, description = "Kiểm tra hiển thị đồng bộ thông tin bên trang thanh toán")
    public void PaymentInfo() throws Exception {
        String nameCustomerInfo = checkout_page.getNameCustomer();
        String phoneCustomerInfo = checkout_page.getPhoneCustomer();
        String emailCustomerInfo = "quynhhuong6319@gmail.com";

        test.get().info("Nhập thông tin");
        checkout_page.SendKeysCity("Hồ Chí Minh");
        checkout_page.ClickButtonAgree();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        excelHelper.setExcelFile("src/test/resources/TestData.xlsx", "Checkout");
        checkout_page.SendKeysDistrict(excelHelper.getCellData("District", 1));
        checkout_page.SendKeysAddress(excelHelper.getCellData("Address",1));
        checkout_page.CLickInputVatNo();

        LogUtils.info("Lấy thông tin trang Thông tin");
        String AddressInfo = checkout_page.getAddressName();
        checkout_page.SendKeysInputNote(excelHelper.getCellData("Note",1));
        String NoteInfo = checkout_page.getNote();

        test.get().info("Kiểm tra chuyển sang tab 'Thanh toán'");
        checkout_page.ClickButtonCheckout();

        LogUtils.info("Lấy thông tin trang Thanh toán");
        String nameCustomerPayment = checkout_page.getCustomerName();
        String phoneCustomerPayment = checkout_page.getPhoneNumber();
        String emailCustomerPayment = checkout_page.getEmail();

        LogUtils.info("Kiểm tra đồng bộ thông tin khách hàng");
        LogUtils.info("Đồng bộ tên");
        if (nameCustomerInfo.equals(nameCustomerPayment)) {
            test.get().pass("Tên khách hàng chính xác");
        }else {
            test.get().fail("Tên khách hàng không chính xác");
        }

        LogUtils.info("Đồng bộ SĐT");
        if (phoneCustomerInfo.equals(phoneCustomerPayment)) {
            test.get().pass("SĐT khách hàng chính xác");
        }else {
            test.get().fail("SĐT khách hàng không chính xác");
        }

        LogUtils.info("Đồng bộ email");
        if (emailCustomerInfo.equals(emailCustomerPayment)) {
            test.get().pass("Email khách hàng chính xác");
        }else {
            test.get().fail("Email khách hàng không chính xác");
        }

        LogUtils.info("Kiểm tra lấy thông tin địa chỉ và ghi chú");
        String AddressPayment = checkout_page.getAddress();
        String NotePayment = checkout_page.getNote2();

        LogUtils.info("Kiểm tra đồng bộ thông tin");
        LogUtils.info("Đồng bộ địa chỉ");
        if (AddressInfo.equals(AddressPayment)) {
            test.get().pass("Địa chỉ khách hàng chính xác");
        }else {
            test.get().fail("Địa chỉ khách hàng không chính xác");
        }

        LogUtils.info("Đồng bộ ghi chú");
        if (NoteInfo.equals(NotePayment)) {
            test.get().pass("Ghi chú khách hàng chính xác");
        }else {
            test.get().fail("Ghi chú khách hàng không chính xác");
        }

        checkout_page.isCheckboxTermsSelected();
        if (checkout_page.isCheckboxTermsSelected()) {
            test.get().pass("Checkbox 'Đồng ý điều khoản' đã được chọn");
        }else {
            test.get().fail("Checkbox 'Đồng ý điều khoản' chưa được chọn");
        }

        checkout_page.ClickListProduct();
        checkout_page.isTitleListProductDisplayed();
        if (checkout_page.isTitleListProductDisplayed()) {
            test.get().pass("Hiển thị danh sách sản phẩm đang được chọn");
        }else {
            test.get().fail("Không hiển thị danh sách sản phẩm đang được chọn");
        }

        test.get().pass("Kiểm tra đồng bộ thành công");
    }

    @Test (groups = "UI_Test",priority = 5, description = "Kiểm tra hiển thị giá thanh toán")
    public void verifyPricePayment() throws Exception {
        LogUtils.info("Nhập thông tin");
        checkout_page.SendKeysCity("Hồ Chí Minh");
        checkout_page.ClickButtonAgree();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        excelHelper.setExcelFile("src/test/resources/TestData.xlsx", "Checkout");
        checkout_page.SendKeysDistrict(excelHelper.getCellData("District", 1));
        checkout_page.SendKeysAddress(excelHelper.getCellData("Address",1));
        checkout_page.SendKeysInputNote(excelHelper.getCellData("Note",1));
        checkout_page.CLickInputVatNo();
        LogUtils.info("Kiểm tra chuyển sang tab 'Thanh toán'");
        checkout_page.ClickButtonCheckout();

        LogUtils.info("Kiểm tra hiển thị giá thanh toán");
        String TotalPrice = checkout_page.getTotalPrice();
        String Pricetemp = checkout_page.getTotalPriceTemp();
        if (TotalPrice.equals(Pricetemp)) {
            test.get().pass("Giá thanh toán chính xác");
        }else {
            test.get().fail("Giá thanh toán không chính xác");
        }
        test.get().pass("Thành công hiển thị giá trong trang Thanh toán");
    }
}
