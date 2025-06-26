package com.CellphoneS.testcases;

import com.base.BaseSetup;
import com.CellphoneS.helpers.SignIn_Helpers;
import com.ultilities.listeners.ReportListener;
import com.CellphoneS.pages.Cart_Page;
import com.CellphoneS.pages.Homepage_page;
import com.CellphoneS.pages.Product_Detail_Page;
import com.CellphoneS.pages.Search_Page;
import com.helpers.CaptureHelpers;
import com.helpers.ValidateUIHelper;
import com.ultilities.ExcelUtils;
import com.ultilities.logs.LogUtils;
import com.ultilities.Properties_File;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.time.Duration;

@Listeners(ReportListener.class)
public class Cart_Test extends BaseSetup {

    private static final Logger log = LoggerFactory.getLogger(Cart_Test.class);
    private WebDriver driver;
    public WebDriverWait wait;
    public ExcelUtils excelHelper;
    public ValidateUIHelper validateUIHelper;
    public Product_Detail_Page product_detail_page;
    public SignIn_Helpers signIn_helpers;
    public Homepage_page homepage_page;
    public Search_Page search_page;
    public Cart_Page cart_page;

    @BeforeClass (groups = {"Function", "UI_Test"}, description = "Kiểm tra giỏ hàng")
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
        product_detail_page = new Product_Detail_Page(driver);
        log.info("Đã mở trang tìm kiếm");
    }

    @AfterMethod (groups = {"Function", "UI_Test"}, description = "Kiểm tra giỏ hàng")
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

    private String detailImage;
//    @Description("Thực hiện mở trang giỏ hàng")
    @BeforeMethod
    public void SearchProduct() {
        LogUtils.info("Thực hiện tìm kiếm sản phẩm 'iphone' và mở trang chi tiết");
        product_detail_page = search_page.openProductDetail("iphone");
        detailImage = cart_page.extractFileName(product_detail_page.getMainImageSrc());
        cart_page = product_detail_page.OpenCartPage();

    }

    @Test (groups = "UI_Test", description = "Kiểm tra tiêu đề trang giỏ hàng")
    public void VerifyTitleCartPage() {
        LogUtils.info("Kiểm tra hiển thị tiêu đề trang");
        cart_page.verifyCartPageTitle();
        String title = cart_page.getCartPageTitle();
        LogUtils.info("Tiêu đề trang " + title);
    }

    //Loại bỏ chọn màu sắc
    @Test (groups = "UI_Test", description = "Kiểm tra hiển thị thông tin sản phẩm trong giỏ hàng")
    public void verifyAfterAddToCart() {
        LogUtils.info("Kiểm tra checkbox sản phẩm đã được chọn");
        cart_page.isSelectedProduct();
        Assert.assertTrue(cart_page.isSelectedProduct(), "Checkbox sản phẩm chưa được chọn");

        LogUtils.info("Kiểm tra cart sản phẩm hiển thị trong giỏ");
        Assert.assertTrue(cart_page.isBoxProductDisplayed(), "Cart sản phẩm không hiển thị trong giỏ hàng");

        LogUtils.info("Kiểm tra hình ảnh sản phẩm hiển thị trong product detail và giỏ hàng");
        String cartImage = cart_page.extractFileName(cart_page.getCartImageSrc());
        Assert.assertEquals(detailImage, cartImage, "Hình ảnh sản phẩm không khớp");

        LogUtils.info("Kiêểm tra giá trên Cart sản phẩm và giá tạm tính");
        String price = cart_page.getPriceProduct();
        LogUtils.info("Giá sản phẩm: " + price);
        Assert.assertTrue(
                price.matches("\\d{1,3}(\\.\\d{3})+đ"),
                "Giá sản phẩm không hợp lệ: " + price
        );

        String priceTemp = cart_page.getPriceTemp();
        LogUtils.info("Giá tạm tính: "+ priceTemp);
        Assert.assertEquals(price, priceTemp, "Giá trên Cart sản phẩm và giá tạm tính không khớp");

        LogUtils.info("Kiểm tra số lượng saản phẩm khi thêm vaào giỏ");
        String quantity = cart_page.getProductQuantity();
        Assert.assertEquals(quantity, "1", "Số lượng sản phẩm không khớp");

    }

    @Test (groups = "Function", description = "Kiểm tra Min ")
    public void testCart_20_MinQuantityLimit() {
        cart_page.clickMinusButton();
        String qty = cart_page.getProductQuantity();
        String toast = cart_page.getToastMessage();

        Assert.assertEquals(qty, "1", "Số lượng đã bị giảm dưới 1");
        Assert.assertTrue(toast.contains("Số lượng sản phẩm đã giảm đến mức tối thiểu"), "Thông báo không đúng: " + toast);
    }

    @Test (groups = "Function", description = "Kiểm tra Max")
    public void testCart_21_MaxQuantityLimit() {
        if (cart_page.getProductQuantity().equals("3")) {
            cart_page.clickPlusButton();
            String qty = cart_page.getProductQuantity();
            Assert.assertEquals(qty, "3", "Số lượng đã tăng quá 3");
            Assert.assertTrue(cart_page.isBoxProductDisplayed(), "Không hiển thị popup thông báo khi vượt quá số lượng cho phép");
        }else {
            cart_page.clickPlusButton();
            cart_page.clickPlusButton();
            cart_page.clickPlusButton();
            String qty = cart_page.getProductQuantity();
            Assert.assertEquals(qty, "3", "Số lượng đã tăng quá 3");
            Assert.assertTrue(cart_page.isBoxProductDisplayed(), "Không hiển thị popup thông báo khi vượt quá số lượng cho phép");
        }
    }

    @Test (groups = "Function", description = "Kiểm tra xóa sản phẩm")
    public void deleteProduct() {
        LogUtils.info("Loại bỏ sản phẩm");
        cart_page.clickDelete();
        cart_page.isBoxProductDisplayed();
        String productName = "iPhone 16 Pro Max 1TB | Chính hãng VN/A - Titan Đen";
        Assert.assertNotEquals(cart_page.getAllProductNamesInCart(), productName, "Sản phẩm chưa bị xoá khỏi giỏ hàng");
    }
}
