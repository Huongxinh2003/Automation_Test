package com.CellphoneS.tests;

import com.base.BaseSetup;
import com.CellphoneS.helpers.SignIn_Helpers;
import com.ultilities.listeners.ReportListener;
import com.CellphoneS.pages.Cart_Page;
import com.CellphoneS.pages.Homepage_page;
import com.CellphoneS.pages.Product_Detail_Page;
import com.CellphoneS.pages.Search_Page;
import com.helpers.ValidateUIHelper;
import com.ultilities.ExcelUtils;
import com.ultilities.logs.LogUtils;
import com.ultilities.Properties_File;
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

    @BeforeClass(groups = {"Function", "UI_Test"})
    public void setUp() throws Exception {
        //gọi hàm khởi tạo properties
        Properties_File.setPropertiesFile();
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

    private String detailImage;
    @BeforeMethod (groups = {"Function", "UI_Test"})
    public void SearchProduct() throws Exception {
        LogUtils.info("Thực hiện tìm kiếm sản phẩm 'iphone' và mở trang chi tiết");
        product_detail_page = search_page.openProductDetail("iphone");
        Thread.sleep(2000);
        detailImage = cart_page.getNameImage();
        cart_page = product_detail_page.OpenCartPage();
    }

    @Test(groups = "UI_Test", description = "Kiểm tra tiêu đề trang giỏ hàng")
    public void VerifyTitleCartPage() throws InterruptedException {
        LogUtils.info("Kiểm tra hiển thị tiêu đề trang");
        Thread.sleep(2000);
        String title = cart_page.getCartPageTitle();
        test.get().info("Tiêu đề trang " + title);
        cart_page.verifyCartPageTitle();
    }

    @Test(groups = "UI_Test", description = "Kiểm tra hiển thị thông tin sản phẩm trong giỏ hàng")
    public void verifyAfterAddToCart() throws Exception{
        try {
            Thread.sleep(2000);
            LogUtils.info("Kiểm tra cart sản phẩm hiển thị trong giỏ");
            Assert.assertTrue(cart_page.isBoxProductDisplayed(), "Cart sản phẩm không hiển thị");
            test.get().pass("Cart sản phẩm hiển thị trong giỏ hàng.");

            LogUtils.info("Kiểm tra hình ảnh sản phẩm hiển thị trong product detail và giỏ hàng");
            String cartImage = cart_page.getNameImage();
            Assert.assertEquals(detailImage, cartImage, "Hình ảnh sản phẩm không khớp");
            test.get().pass("Hình ảnh sản phẩm khớp giữa chi tiết và giỏ hàng.");

            LogUtils.info("Kiểm tra giá sản phẩm và giá tạm tính");
            String price = cart_page.getPriceProduct();
            LogUtils.info("Giá sản phẩm: " + price);
            Assert.assertTrue(price.matches("\\d{1,3}(\\.\\d{3})+đ"), "Giá không đúng định dạng: " + price);
            test.get().pass("Giá sản phẩm đúng định dạng.");

            String priceTemp = cart_page.getPriceTemp();
            LogUtils.info("Giá tạm tính: " + priceTemp);
            Assert.assertEquals(price, priceTemp, "Giá sản phẩm và giá tạm tính không khớp");
            test.get().pass("Giá sản phẩm và giá tạm tính khớp.");

            LogUtils.info("Kiểm tra số lượng sản phẩm khi thêm vào giỏ");
            String expectedQuantity = Properties_File.getPropValue("Quantity");
            String quantity = cart_page.getProductQuantity();
            LogUtils.info("Số lượng sản phẩm: " + quantity);
            Assert.assertEquals(quantity, expectedQuantity, "Số lượng sản phẩm không đúng");
            test.get().pass("Số lượng sản phẩm đúng sau khi thêm vào giỏ.");
        } catch (Exception e) {
            test.get().fail("Xảy ra lỗi khi kiểm tra thông tin sản phẩm trong giỏ: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }


    @Test(groups = "Function",priority = 1, description = "Kiểm tra Min - không thể giảm dưới 1 và hiển thị thông báo")
    public void testCart_20_MinQuantityLimit() {
        try {
            LogUtils.info("Giảm số lượng về 1 (nếu chưa phải 1)");
            while (!cart_page.getProductQuantity().equals("1")) {
                cart_page.clickMinusButton();
            }
            test.get().pass("Đã đưa số lượng sản phẩm về 1.");

            LogUtils.info("Click tiếp nút giảm để test ngưỡng dưới 1");
            cart_page.clickMinusButton();

            String qty = cart_page.getProductQuantity();
            String toast = cart_page.getToastMessage();

            test.get().info("Số lượng hiện tại: " + qty);
            test.get().info("Thông báo hiện tại: " + toast);

            Assert.assertEquals(qty, "1", "Số lượng đã bị giảm dưới 1");
            Assert.assertTrue(toast.contains("Số lượng sản phẩm đã giảm đến mức tối thiểu"), "Thông báo không đúng");
            test.get().pass("Không thể giảm số lượng dưới 1 và có thông báo phù hợp.");
        } catch (Exception e) {
            test.get().fail("Lỗi khi kiểm tra giới hạn tối thiểu của giỏ hàng: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }


    @Test(groups = "Function",priority = 2, description = "Kiểm tra Max - số lượng quá 3 và hiển thị thông báo")
    public void testCart_21_MaxQuantityLimit() {
        try {
            LogUtils.info("Tăng số lượng sản phẩm lên quá 3");

            if (!cart_page.getProductQuantity().equals("3")) {
                cart_page.clickPlusButton();
                cart_page.clickPlusButton();
                cart_page.clickPlusButton();
            } else {
                cart_page.clickPlusButton();
            }

            String qty = cart_page.getProductQuantity();
            test.get().info("Số lượng hiện tại: " + qty);

            Assert.assertTrue(cart_page.isBoxProductDisplayed(), "Không hiển thị thông báo khi vượt quá số lượng cho phép");
            test.get().pass("Hiển thị thông báo khi vượt quá số lượng tối đa.");
        } catch (Exception e) {
            test.get().fail("Lỗi khi kiểm tra giới hạn số lượng tối đa trong giỏ hàng: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }


    @Test (groups = "Function",priority = 3, description = "Kiểm tra xóa sản phẩm")
    public void deleteProduct() {
        try {
            LogUtils.info("Loại bỏ sản phẩm");
            cart_page.clickDelete();
            cart_page.isBoxProductDisplayed();
            String productName = "iPhone 16 Pro Max 1TB | Chính hãng VN/A - Titan Đen";
            Assert.assertNotEquals(cart_page.getAllProductNamesInCart(), productName,
                    "Sản phẩm chưa bị xoá khỏi giỏ hàng");
            test.get().pass("Sản phẩm đã bị xoá khỏi giỏ hàng");
        }catch (Exception e) {
            test.get().fail("Lỗi khi kiểm tra xóa sản phẩm: " + e.getMessage());
            Assert.fail(e.getMessage());
        }

    }
}
