package com.CellphoneS.tests;

import com.base.BaseSetup;
import com.CellphoneS.helpers.SignIn_Helpers;
import com.CellphoneS.pages.Homepage_page;
import com.CellphoneS.pages.Product_Detail_Page;
import com.CellphoneS.pages.Search_Page;
import com.helpers.ValidateUIHelper;
import com.ultilities.ExcelUtils;
import com.ultilities.listeners.ReportListener;
import com.ultilities.logs.LogUtils;
import com.ultilities.Properties_File;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


import java.time.Duration;

import static org.testng.Assert.assertTrue;

@Listeners(ReportListener.class)
public class Product_Detail_Test extends BaseSetup {

    private static final Logger log = LoggerFactory.getLogger(Product_Detail_Test.class);
    private WebDriver driver;
    public WebDriverWait wait;
    public ExcelUtils excelHelper;
    public ValidateUIHelper validateUIHelper;
    public Product_Detail_Page product_detail_page;
    public SignIn_Helpers signIn_helpers;
    public Homepage_page homepage_page;
    public Search_Page search_page;

    @BeforeClass(groups = {"UI_Test", "Function","Function_UI"})
    public void setupDriver() throws Exception {
        //gọi hàm khởi tạo properties
        Properties_File.setPropertiesFile();
        // Lấy driver từ class cha BaseSetup
        boolean headless = Boolean.parseBoolean(Properties_File.getPropValue("headless"));
        driver = setupDriver(Properties_File.getPropValue("browser"), headless);
        excelHelper = new ExcelUtils();
        validateUIHelper = new ValidateUIHelper(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        signIn_helpers = new SignIn_Helpers(driver);
        homepage_page = signIn_helpers.SignIn(driver);
        search_page = homepage_page.openSearchPage();
        product_detail_page = new Product_Detail_Page(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @BeforeMethod(groups = {"Function", "UI_Test", "Function_UI"})
    public void SearchProduct1() {
        LogUtils.info("Thực hiện tìm kiếm sản phẩm 'iphone' và mở trang chi tiết");
        product_detail_page = search_page.openProductDetail("iphone");
        test.get().pass("Mở trang chi tiết sản phẩm thành công");
    }

    @Test(groups = "UI_Test",priority = 1, description = "Kiểm tra title của trang chi tiết sản phẩm")
    public void Search() {
        LogUtils.info("Kiểm tra hiển thị tiêu đề trang");
        Assert.assertTrue(product_detail_page.isTitleProductDisplayed(), "Tiêu đề sản phẩm không hiển thị");

        LogUtils.info("Lấy tiêu đề trang");
        String title = product_detail_page.getTitleProduct();
        test.get().info("Tiêu đề sản phẩm " + title);
        test.get().pass("Kiểm tra hiển thị tiêu đề trang thành công");
    }

    @Test (groups = "Function",priority = 1, description = "Kiểm tra các chức năng trên trang chi tiết sản phẩm")
    public void FavoriteProduct() {
        LogUtils.info("Kiểm tra hiển thị nút yêu thích");
        product_detail_page.isFavoriteProductDisplayed();

        LogUtils.info("Kiểm tra thêm sản phẩm vào mục yêu thích");
        product_detail_page.ClickFavoriteProduct();
        LogUtils.info("Kiểm tra hiển thị thông báo thêm vào mục yêu thích");
        By alertBox = By.xpath("//b[contains(text(),'Đã thêm vào danh sách yêu thích!')]");
        WebElement alertAdd = wait.until(ExpectedConditions.visibilityOfElementLocated(alertBox));
        Assert.assertTrue(alertAdd.isDisplayed(), "Thông báo thêm vào mục yêu thích không hiển thị");
        test.get().pass("Hiển thị thông báo thêm vào yêu thích");

        LogUtils.info("Kiểm tra mau sắc của thông báo thêm vào yêu thích");
        String backgroundColor = alertAdd.getCssValue("background");
        test.get().info("Màu nền của thông báo: " + backgroundColor);

        LogUtils.info("Kiểm tra hiển thị nút xóa mục yêu thích");
        product_detail_page.DeleteFavoriteProduct();
        LogUtils.info("Kiểm tra hiển thị thông báo xóa mục yêu thích");
        By alertBox1 = By.xpath("//div[@class='toasted toasted-primary default']");
        WebElement alertDetele = wait.until(ExpectedConditions.visibilityOfElementLocated(alertBox1));
        Assert.assertTrue(alertDetele.isDisplayed(), "Thông báo xóa mục yêu thích không hiển thị");
        test.get().pass("Hiển thị thông báo xóa mục yêu thích");

        LogUtils.info("Kiểm tra màu sắc của thông báo khi bỏ yêu thích");
        String backgroundColor1 = alertAdd.getCssValue("background");
        test.get().info("Màu nền của thông báo: " + backgroundColor1);
    }

    @Test (groups = "Function",priority = 2, description = "Kiểm tra phần trăm giảm giá của sản phẩm")
    public void verifyDiscountCalculation() {
        int actualDiscount = product_detail_page.calculateDiscountPercentage();
        // mong đợi khoảng 13%
        Assert.assertEquals(actualDiscount, 13, "Phần trăm giảm không đúng!");
        test.get().pass("Phần trăm giảm giá đúng");
    }


    @Test (groups = "UI_Test", priority = 2, description = "Kiểm tra hiển thị thông tin sản phẩm khi đổi giá trị")
    public void verifyChangeAfterSelectVersionProduct() {
        LogUtils.info("Lưu trạng thái ban đầu ");
        String TitleProductBefore = product_detail_page.getTitleProduct();
        String BoxRatingBefore = product_detail_page.getBoxRating();
        String BoxPriceBefore = product_detail_page.getProductPrice();
        String DiscountBefore = product_detail_page.getBasePrice();
        String PriceSaleBefore = product_detail_page.getSalePrice();
        String ColorPriceBefore = product_detail_page.getColorPrice();
        String PriceStickyBarBefore = product_detail_page.getPriceStickyBar();
        String TitleStickyBarBefore = product_detail_page.getTitleStickyBar();
        String CountStoreBefore = product_detail_page.getCountStore();
        test.get().pass("Lưu trạng thái ban đầu thành công");

        LogUtils.info("Chọn phiên bản");
        product_detail_page.selectVersionProduct("1TB");
        LogUtils.info("Chờ trang cập nhật lại");
        validateUIHelper.waitForPageLoaded();

        LogUtils.info("Lấy giá trị sau khi chọn phiên bản");
        String TitleProductAfter = product_detail_page.getTitleProduct();
        String BoxRatingAfter = product_detail_page.getBoxRating();
        String BoxPriceAfter = product_detail_page.getProductPrice();
        String DiscountAfter = product_detail_page.getBasePrice();
        String PriceSaleAfter = product_detail_page.getSalePrice();
        String ColorPriceAfter = product_detail_page.getColorPrice();
        String TitleStickyBarAfter = product_detail_page.getTitleStickyBar();
        String PriceStickyBarAfter = product_detail_page.getPriceStickyBar();
        String CountStoreAfter = product_detail_page.getCountStore();
        test.get().pass("Lấy giá trị sau khi chọn phiên bản thành công");

        LogUtils.info("Kiểm tra giá trị sau khi chọn phiên bản");
        LogUtils.info("Title sản pẩm: "+ TitleProductBefore);
        LogUtils.info("Title sản phẩm: " + TitleProductAfter);
        Assert.assertNotEquals(TitleProductBefore, TitleProductAfter,
                "TitleProduct không thay đổi sau khi chọn phiên bản");
        Assert.assertNotEquals(BoxRatingBefore, BoxRatingAfter,
                "BoxRating không thay đổi sau khi chọn phiên bản");
        Assert.assertNotEquals(BoxPriceBefore, BoxPriceAfter,
                "BoxPrice không thay đổi sau khi chọn phiên bản");
        Assert.assertNotEquals(DiscountBefore, DiscountAfter,
                "Giá gốc không thay đổi sau khi chọn phiên bản");
        Assert.assertNotEquals(PriceSaleBefore, PriceSaleAfter,
                "Giá sale không thay đổi sau khi chọn phiên bản");
        Assert.assertNotEquals(ColorPriceBefore, ColorPriceAfter,
                "Giá trong nu màu sắc không thay đổi sau khi chọn phiên bản");
        Assert.assertNotEquals(TitleStickyBarBefore, TitleStickyBarAfter,
                "TitleStickyBar không thay đổi sau khi chọn phiên bản");
        Assert.assertNotEquals(PriceStickyBarBefore, PriceStickyBarAfter,
                "PriceStickyBar không thay đổi sau khi chọn phiên bản");
        Assert.assertNotEquals(CountStoreBefore, CountStoreAfter,
                " Số cửa hàng còn hàng không thay đổi sau khi chọn phiên bản");
        test.get().pass("Tất cả giá trị được thay đổi sau khi chọn phiên bản");
    }

    @Test (groups = "UI_Test",priority = 3, description = "Kiểm tra hiển thị thông tin sản phẩm")
    public void verifyChangeAfterSelectColorProduct() {
        LogUtils.info("Chọn phiên bản");
        product_detail_page.selectVersionProduct("1TB");
        LogUtils.info("Chờ trang cập nhật lại");
        validateUIHelper.waitForPageLoaded();

        LogUtils.info("Lưu trạng thái ban đầu ");
        String DiscountBefore = product_detail_page.getBasePrice();
        String PriceSaleBefore = product_detail_page.getSalePrice();
        String ProductThumbnail = product_detail_page.getProductThumbnail();
        String PriceStickyBarBefore = product_detail_page.getPriceStickyBar();
        String CountStoreBefore = product_detail_page.getCountStore();
        LogUtils.info("Giá gốc: " + DiscountBefore);
        LogUtils.info("Giá sale: " + PriceSaleBefore);
        LogUtils.info("Hinh ảnh: " + ProductThumbnail);
        LogUtils.info("Giá trên sticky bar: " + PriceStickyBarBefore);
        LogUtils.info("Số cửa hàng còn hàng: " + CountStoreBefore);
        test.get().pass("Lưu trạng thái ban đầu thành công");

        LogUtils.info("Chọn màu sắc");
        product_detail_page.selectColorProduct("Titan Đen");
        test.get().pass("Chọn màu sắc thành công");

        LogUtils.info("Chờ trang cập nhật lại");
        validateUIHelper.waitForPageLoaded();

        LogUtils.info("Lấy giá trị sau khi chọn màu sắc");
        String DiscountAfter = product_detail_page.getBasePrice();
        String PriceSaleAfter = product_detail_page.getSalePrice();
        String ProductThumbnailAfter = product_detail_page.getProductThumbnail();
        String PriceStickyBarAfter = product_detail_page.getPriceStickyBar();
        String CountStoreAfter = product_detail_page.getCountStore();
        test.get().pass("Lấy giá trị sau khi chọn màu sắc thành công");

        LogUtils.info("Kiểm tra giá trị sau khi chọn màu sắc");
        Assert.assertEquals(DiscountAfter, DiscountBefore, "Giá gốc không thay đổi sau khi chọn màu sắc");
        Assert.assertNotEquals(PriceSaleBefore, PriceSaleAfter, "Giá sale không thay đổi sau khi chọn màu sắc");
        Assert.assertNotEquals(ProductThumbnail, ProductThumbnailAfter, "Ảnh sản phẩm không thay đổi sau khi chọn màu sắc");
        Assert.assertNotEquals(PriceStickyBarBefore, PriceStickyBarAfter, "PriceStickyBar không thay đổi sau khi chọn màu sắc");
        Assert.assertNotEquals(CountStoreBefore, CountStoreAfter, "Số cửa hàng còn hàng không thay đổi sau khi chọn màu sắc");

        test.get().pass("Tất cả giá trị được thay đổi sau khi chọn màu sắc");
    }

    //chọn 1 thành phố khác bất kì
    @Test (groups = "Function",priority = 3, description = "Kiểm tra popup chọn thành phố")
    public void verifyChangeAfterSelectCity() throws InterruptedException {
        product_detail_page.ScrollToElement();
        LogUtils.info("Lưu trạng thái ban đầu ");
        String CountStoreBefore = product_detail_page.getCountStore();
        String CityBefore = product_detail_page.getCityName();
        test.get().info("Số lượng cửa hàng trong thành phố " + CityBefore + " là " + CountStoreBefore);

        Thread.sleep(2000);
        LogUtils.info("Click button Thành phố");
        product_detail_page.ClickCity();

        LogUtils.info("Chọn Thành phố");
        product_detail_page.ClickSelectCity("Hồ Chí Minh");

        LogUtils.info("Chờ trang cập nhật lại");
        validateUIHelper.waitForPageLoaded();
        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='70%'");

        LogUtils.info("Lấy giá trị sau khi chọn Thành phố");
        String CountStoreAfter = product_detail_page.getCountStore();
        String CityAfter = product_detail_page.getCityName();
//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", CityAfter);
        test.get().pass("Lấy giá trị sau khi chọn Thành phố thành công");

        LogUtils.info("Kiểm tra giá trị sau khi chọn Thành phố");
        Assert.assertNotEquals(CityBefore, CityAfter, "Thành phố không thay đổi sau khi chọn Thành phố");
        if (CountStoreBefore.equals(CountStoreAfter)) {
            test.get().pass("Số cửa hàng của hai thành phố bằng nhau");
        } else {
            Assert.assertNotEquals(CountStoreBefore, CountStoreAfter, "Số cửa hàng còn hàng không thay đổi sau khi chọn Thành phố");
            test.get().fail("Số cửa hàng còn hàng thay đổi sau khi chọn Thành phố");
        }

        LogUtils.info("Kiểm tra box có chứa tên thành phố đã chọn");
        Assert.assertTrue(
                product_detail_page.isAddressBoxContainsCity(CityAfter),
                "Box không chứa tên thành phố đã chọn: " + CityAfter
        );

        test.get().info("Nội dung box địa chỉ: " + product_detail_page.getAddressText());
        test.get().info("Số lượng cửa hàng trong thành phố " + CityAfter + " là " + CountStoreAfter);

        test.get().pass("Tất cả giá trị được thay đổi sau khi chọn Thành phố");
    }

    //Mặc định chọn "HCM"
    @Test (groups = "Function",priority = 4, description = "Kiểm tra dropdown chọn quận")
    public void verifyChangeAfterSelectDistrict() {
        product_detail_page.ScrollToElement();
        LogUtils.info("Lưu trạng thái ban đầu ");
        String CountStoreBefore = product_detail_page.getCountStore();
        String DistrictBefore = product_detail_page.getDistrictName();
        test.get().info("Số lượng cửa hàng trong Quận " + DistrictBefore + " là " + CountStoreBefore);

        ((JavascriptExecutor) driver).executeScript("location.reload();");

        LogUtils.info("Chọn Quận");
        String districtName = product_detail_page.ClickSelectDistrict("Quận Thủ Đức");
        LogUtils.info("Chờ trang cập nhật lại");
        validateUIHelper.waitForPageLoaded();

        LogUtils.info("Lấy giá trị sau khi chọn Quận");
        String CityAfter = product_detail_page.getCityName();
        String CountStoreAfter = product_detail_page.getCountStore();
        String DistrictAfter = product_detail_page.getDistrictName();
        test.get().pass("Lấy giá trị sau khi chọn quận thành công");

//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", CityAfter);

        LogUtils.info("Kiểm tra giá trị sau khi chọn Quận");
        Assert.assertNotEquals(DistrictBefore, DistrictAfter, "Quận không thay đổi sau khi chọn");
        Assert.assertNotEquals(CountStoreBefore, CountStoreAfter, " Số cửa hàng còn hàng không thay đổi sau khi chọn Quận");
        test.get().pass("Kiểm tra giá trị sau khi chọn quận thành công");

        LogUtils.info("Kiểm tra box có chứa tên Quận đã chọn");
        product_detail_page.isAddressBoxContainsCityAndDistrict(CityAfter, DistrictAfter);
        test.get().pass("Box có chứa tên quận đã chọn");

        LogUtils.info("Nội dung box địa chỉ: " + product_detail_page.getAddressText2());

        test.get().info("Số lượng cửa hàng trong Quận " + districtName + " là " + CountStoreAfter);
        test.get().pass("Tất cả giá trị được thay đổi sau khi chọn Quận");
    }

    @Test(groups = "Function", priority = 5, description = "Kiểm tra chuyển màn hình sang giỏ hàng")
    public void BuyProduct() {
        LogUtils.info("Chọn phiên bản");
        try {
            product_detail_page.selectVersionProduct("1TB");
            test.get().pass("Đã chọn phiên bản 1TB.");
        } catch (Exception e) {
            test.get().fail("Không thể chọn phiên bản sản phẩm: " + e.getMessage());
            Assert.fail(e.getMessage());
        }

        LogUtils.info("Chờ trang cập nhật lại");
        try {
            validateUIHelper.waitForPageLoaded();
            test.get().pass("Trang đã cập nhật sau khi chọn phiên bản.");
        } catch (Exception e) {
            test.get().fail("Trang không cập nhật sau khi chọn phiên bản: " + e.getMessage());
            Assert.fail(e.getMessage());
        }

        LogUtils.info("Chọn màu sắc");
        try {
            product_detail_page.selectColorProduct("Titan Đen");
            test.get().pass("Đã chọn màu Titan Đen.");
        } catch (Exception e) {
            test.get().fail("Không thể chọn màu sắc sản phẩm: " + e.getMessage());
            Assert.fail(e.getMessage());
        }

        LogUtils.info("Chờ trang cập nhật lại");
        try {
            validateUIHelper.waitForPageLoaded();
            test.get().pass("Trang đã cập nhật sau khi chọn màu.");
        } catch (Exception e) {
            test.get().fail("Trang không cập nhật sau khi chọn màu: " + e.getMessage());
            Assert.fail(e.getMessage());
        }

        LogUtils.info("Click button Mua ngay");
        try {
            product_detail_page.ClickBuyNow();
            test.get().pass("Đã click nút 'Mua ngay'.");
        } catch (Exception e) {
            test.get().fail("Không thể click nút 'Mua ngay': " + e.getMessage());
            Assert.fail(e.getMessage());
        }

        LogUtils.info("Kiểm tra chuyển màn hình sang giỏ hàng");
        try {
            String expectedUrl = "https://cellphones.com.vn/cart/";
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.urlToBe(expectedUrl));

            String actualUrl = driver.getCurrentUrl();
            Assert.assertEquals(expectedUrl, actualUrl);
            test.get().pass("Chuyển hướng đến giỏ hàng thành công: " + actualUrl);
        } catch (Exception e) {
            test.get().fail("Không chuyển sang trang giỏ hàng như mong đợi: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }


    @Test(groups = "UI_Test", priority = 4, description = "Kiểm tra thông báo khi chuyển màn hình sang giỏ hàng")
    public void AddProductToCart() {
        try {
            LogUtils.info("Chọn phiên bản");
            product_detail_page.selectVersionProduct("1TB");
            test.get().pass("Đã chọn phiên bản 1TB.");

            LogUtils.info("Chờ trang cập nhật lại");
            validateUIHelper.waitForPageLoaded();
            test.get().pass("Trang đã cập nhật sau khi chọn phiên bản.");

            LogUtils.info("Chọn màu sắc");
            product_detail_page.selectColorProduct("Titan Đen");
            test.get().pass("Đã chọn màu Titan Đen.");

            LogUtils.info("Chờ trang cập nhật lại");
            validateUIHelper.waitForPageLoaded();
            test.get().pass("Trang đã cập nhật sau khi chọn màu sắc.");

            LogUtils.info("Click button Thêm vào giỏ hàng");
            product_detail_page.ClickAddToCart();
            test.get().pass("Đã click nút Thêm vào giỏ hàng.");

            LogUtils.info("Kiểm tra hiển thị thông báo sau khi thêm vào giỏ hàng");
            By successAddToCart = By.xpath("//div[@class='toasted toasted-primary success']");
            wait.until(ExpectedConditions.visibilityOfElementLocated(successAddToCart));
            WebElement toast = driver.findElement(successAddToCart);
            assertTrue(toast.isDisplayed(), "Không hiển thị thông báo sau khi thêm vào giỏ hàng");
            test.get().pass("Hiển thị thông báo thành công sau khi thêm vào giỏ hàng.");
        } catch (Exception e) {
            test.get().fail("Thêm sản phẩm vào giỏ hàng thất bại: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }


    @Test(groups = "Function", priority = 6, description = "Kiểm tra chuyển màn hình sang tab Trả góp")
    public void ClickInstallmentOption2() {
        LogUtils.info("Click button Thanh toán trả góp 0%");
        try {
            product_detail_page.ClickInstallmentOption();
            product_detail_page.ClickInstallmentOption2();
            test.get().pass("Đã click nút 'Thanh toán trả góp 0%'.");
        } catch (Exception e) {
            test.get().fail("Không thể click nút 'Thanh toán trả góp 0%': " + e.getMessage());
            Assert.fail(e.getMessage());
        }

        LogUtils.info("Kiểm tra thông báo khi click button Thanh toán trả góp 0%");
        try {
            Assert.assertTrue(product_detail_page.isToastMessageDisplayed(), "Không hiển thị toast message.");
            test.get().pass("Toast message hiển thị thành công sau khi chọn trả góp 0%.");
        } catch (Exception e) {
            test.get().fail("Không hiển thị toast message sau khi chọn trả góp 0%: " + e.getMessage());
            Assert.fail(e.getMessage());
        }

        LogUtils.info("Kiểm tra sản phẩm ở tab 'Trả góp'");
        try {
            String activeTab = product_detail_page.getActiveTabText();
            Assert.assertTrue(activeTab.contains("Trả góp"), "Tab không phải 'Trả góp'");
            test.get().pass("Chuyển sang tab 'Trả góp' thành công.");
        } catch (Exception e) {
            test.get().fail("Không chuyển sang tab 'Trả góp': " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }


    @Test(groups = "Function", priority = 7, description = "Kiểm tra chuyển màn hình sang tab Trả góp")
    public void ClickInstallmentOption3() {
        LogUtils.info("Click button Thanh toán trả góp qua thẻ");
        try {
            product_detail_page.ClickInstallmentOption();
            product_detail_page.ClickInstallmentOption3();
            test.get().pass("Đã click nút 'Thanh toán trả góp qua thẻ'.");
        } catch (Exception e) {
            test.get().fail("Không thể click nút 'Thanh toán trả góp qua thẻ': " + e.getMessage());
            Assert.fail(e.getMessage());
        }

        LogUtils.info("Kiểm tra thông báo khi click button Thanh toán trả góp qua thẻ");
        try {
            Assert.assertTrue(product_detail_page.isToastMessageDisplayed(), "Không hiển thị toast message.");
            test.get().pass("Toast message hiển thị thành công sau khi chọn trả góp qua thẻ.");
        } catch (Exception e) {
            test.get().fail("Không hiển thị toast message sau khi chọn trả góp qua thẻ: " + e.getMessage());
            Assert.fail(e.getMessage());
        }

        LogUtils.info("Kiểm tra sản phẩm ở tab 'Trả góp'");
        try {
            String activeTab = product_detail_page.getActiveTabText();
            Assert.assertTrue(activeTab.contains("Trả góp"), "Tab không phải 'Trả góp'");
            test.get().pass("Chuyển sang tab 'Trả góp' thành công.");
        } catch (Exception e) {
            test.get().fail("Không chuyển sang tab 'Trả góp': " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }


    @Test(groups = "Function", priority = 8)
    public void EvaluateProduct() {
        try {
            LogUtils.info("Lưu trạng thái ban đầu");
            String EvaluateBefore = product_detail_page.getCountEvaluateProduct();
            LogUtils.info("Số lượng đánh giá của sản phẩm là: " + EvaluateBefore);
            test.get().pass("Số lượng đánh giá ban đầu: " + EvaluateBefore);

            LogUtils.info("Thực hiện đánh giá sản phẩm");
            product_detail_page.ClickButtonEvaluate();
            product_detail_page.ClickEvaluateStar();
            product_detail_page.ClickEvaluatePerformance();
            product_detail_page.ClickEvaluateBatteryLife();
            product_detail_page.ClickEvaluateCamera();
            product_detail_page.InputEvaluateComment("Sản phẩm trên cả tuyệt vời luôn ấy");
            product_detail_page.ClickEvaluateImage("C:\\Users\\Admin\\Pictures\\kk.jpg");
            product_detail_page.ClickEvaluateButton();
            test.get().pass("Đã thực hiện đầy đủ các bước đánh giá sản phẩm.");

            LogUtils.info("Kiểm tra thông báo sau khi đánh giá");
            By successEvaluate = By.xpath("//b[contains(text(),'CellphoneS đã nhận được phản hồi của bạn')]");
            wait.until(ExpectedConditions.visibilityOfElementLocated(successEvaluate));
            WebElement toast = driver.findElement(successEvaluate);
            assertTrue(toast.isDisplayed(), "Không hiển thị thông báo sau khi đánh giá");
            test.get().pass("Hiển thị thông báo đánh giá thành công.");

            LogUtils.info("Lấy giá trị sau khi đánh giá sản phẩm");
            String EvaluateAfter = product_detail_page.getCountEvaluateProduct();
            LogUtils.info("Số lượng đánh giá sau: " + EvaluateAfter);
            test.get().pass("Số lượng đánh giá sau: " + EvaluateAfter);

            LogUtils.info("Kiểm tra số lượng đánh giá thay đổi");
            Assert.assertNotEquals(EvaluateBefore, EvaluateAfter, "Số lượng đánh giá không thay đổi");
            test.get().pass("Số lượng đánh giá đã thay đổi thành công.");
        } catch (Exception e) {
            test.get().fail("Đánh giá sản phẩm thất bại: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

}
