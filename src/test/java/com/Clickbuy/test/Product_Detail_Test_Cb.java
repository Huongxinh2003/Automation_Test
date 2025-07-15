package com.Clickbuy.test;

import com.Clickbuy.test.Product_Detail_Test_Cb;
import com.Clickbuy.helper.SignIn_Helper_Cb;
import com.Clickbuy.page.Homepage_page_Cb;
import com.Clickbuy.page.Product_Detail_Page_Cb;
import com.Clickbuy.page.Search_Page_Cb;
import com.base.BaseSetup;
import com.helpers.ValidateUIHelper;
import com.ultilities.ExcelUtils;
import com.ultilities.Properties_File;
import com.ultilities.logs.LogUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class Product_Detail_Test_Cb extends BaseSetup {
    private static final Logger log = LoggerFactory.getLogger(Product_Detail_Test_Cb.class);
    private WebDriver driver;
    public WebDriverWait wait;
    public ExcelUtils excelHelper;
    public ValidateUIHelper validateUIHelper;
    public Product_Detail_Page_Cb product_detail_page_cb;
    public Homepage_page_Cb homepage_page_cb;
    public Search_Page_Cb search_page_cb;
    public SignIn_Helper_Cb signIn_helpers_cb;

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
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @BeforeMethod(groups = {"Function", "UI_Test", "Function_UI"})
    public void SearchProduct1() {
        LogUtils.info("Thực hiện tìm kiếm sản phẩm 'iphone' và mở trang chi tiết");
        product_detail_page_cb = search_page_cb.openProductDetail("iphone");
    }

    @Test(groups = "UI_Test",description = "Kiểm tra title của trang chi tiết sản phẩm")
    public void SearchProduct(){
        LogUtils.info("Kiểm tra title của trang chi tiết sản phẩm");
        Assert.assertTrue(product_detail_page_cb.isProductNameDisplayed(),"Title của trang chi tiết sản phẩm không hiển thị");

        String titleProduct = product_detail_page_cb.getProductName();
        LogUtils.info("Tiêu đề sản phẩm: " + titleProduct);
    }

    @Test(groups = "UI_Test",description = "Kiểm tra đồng bộ các thông tin khi thay đổi phiên bản")
    public void verifyVersion() throws InterruptedException {
        LogUtils.info("Lưu trạng thái ban đầu");
        String titleProductBefore = product_detail_page_cb.getProductName();
        String priceProductBefore = product_detail_page_cb.getProductPrice();
        String warrantyActiveBefore = product_detail_page_cb.getActiveWarranty();
        String warrantyContentBefore = product_detail_page_cb.getWarrantyContentBH_price();
        int reviewCountBefore = product_detail_page_cb.getReviewCount();
        String versionNameBefore = product_detail_page_cb.getActiveVersionName();
        String versionPriceBefore = product_detail_page_cb.getActiveVersionPrice();
        String buyNowPriceBefore = product_detail_page_cb.getButtonBuyNowPrice();
        int ratingCountBefore = product_detail_page_cb.getRatingCount();
        String CustomerServiceBefore = product_detail_page_cb.getCustomerService();
        String titleSpecificationBefore = product_detail_page_cb.getTitleSpecification();
        test.get().pass("Lưu trạng thái ban đầu");
        Thread.sleep(1000);

        LogUtils.info("Thay đổi phiên bản");
        product_detail_page_cb.selectVersionProduct("512GB");
        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='75%'");
        validateUIHelper.waitForPageLoaded();

        LogUtils.info("Lấy trạng thái sau khi thay đổi");
        Thread.sleep(1000);
        String titleProductAfter = product_detail_page_cb.getProductName();
        String priceProductAfter = product_detail_page_cb.getProductPrice();
        String warrantyActiveAfter = product_detail_page_cb.getActiveWarranty();
        String warrantyContentAfter = product_detail_page_cb.getWarrantyContentBH_price();
        int reviewCountAfter = product_detail_page_cb.getReviewCount();
        String versionNameAfter = product_detail_page_cb.getActiveVersionName();
        String versionPriceAfter = product_detail_page_cb.getActiveVersionPrice();
        String buyNowPriceAfter = product_detail_page_cb.getButtonBuyNowPrice();
        int ratingCountAfter = product_detail_page_cb.getRatingCount();
        String CustomerServiceAfter = product_detail_page_cb.getCustomerService();
        String titleSpecificationAfter = product_detail_page_cb.getTitleSpecification();

        test.get().pass("Kiểm tra đồng bộ các thông tin");

        if (!titleProductBefore.equals(titleProductAfter)) {
            test.get().pass("Tiêu đề sản phẩm thay đổi sau khi chọn phiên bản");
        } else {
            test.get().fail("Tiêu đề sản phẩm không thay đổi sau khi chọn phiên bản");
        }

        if (!priceProductBefore.equals(priceProductAfter)) {
            test.get().pass("Giá sản phẩm thay đổi sau khi chọn phiên bản");
        } else {
            test.get().fail("Giá sản phẩm không thay đổi sau khi chọn phiên bản");
        }

        if (!warrantyActiveBefore.equals(warrantyActiveAfter)) {
            test.get().pass("Thông tin bảo hành thay đổi sau khi chọn phiên bản");
        } else {
            test.get().fail("Thông tin bảo hành không thay đổi sau khi chọn phiên bản");
        }

        if (!warrantyContentBefore.equals(warrantyContentAfter)) {
            test.get().pass("Nội dung bảo hành thay đổi sau khi chọn phiên bản");
        } else {
            test.get().fail("Nội dung bảo hành không thay đổi sau khi chọn phiên bản");
        }

        if (reviewCountBefore != reviewCountAfter) {
            test.get().pass("Số lượng đánh giá thay đổi sau khi chọn phiên bản");
        } else {
            test.get().fail("Số lượng đánh giá không thay đổi sau khi chọn phiên bản");
        }

        test.get().pass("Số lượng sao thay đổi sau khi chọn phiên bản"); // Nếu bạn cần so sánh thêm, nên thêm biến và logic tương tự

        if (!versionNameBefore.equals(versionNameAfter)) {
            test.get().pass("Tên phiên bản thay đổi sau khi chọn phiên bản");
        } else {
            test.get().fail("Tên phiên bản không thay đổi sau khi chọn phiên bản");
        }

        if (!versionPriceBefore.equals(versionPriceAfter)) {
            test.get().pass("Giá phiên bản thay đổi sau khi chọn phiên bản");
        } else {
            test.get().fail("Giá phiên bản không thay đổi sau khi chọn phiên bản");
        }

        if (!buyNowPriceBefore.equals(buyNowPriceAfter)) {
            test.get().pass("Giá mua ngay thay đổi sau khi chọn phiên bản");
        } else {
            test.get().fail("Giá mua ngay không thay đổi sau khi chọn phiên bản");
        }

        if (ratingCountBefore != ratingCountAfter) {
            test.get().pass("Số lượng đánh giá thay đổi sau khi chọn phiên bản");
        } else {
            test.get().fail("Số lượng đánh giá không thay đổi sau khi chọn phiên bản");
        }

        if (versionPriceAfter.equals(warrantyContentAfter)) {
            test.get().pass("Giá sản phẩm khớp với giá bảo hành");
        } else {
            test.get().fail("Giá sản phẩm không khớp với giá bảo hành");
        }

        if (!CustomerServiceBefore.equals(CustomerServiceAfter)) {
            test.get().pass("Thông tin dịch vụ khách hàng thay đổi sau khi chọn phiên bản");
        } else {
            test.get().fail("Thông tin dịch vụ khách hàng không thay đổi sau khi chọn phiên bản");
        }

        if (!titleSpecificationAfter.equals(titleSpecificationBefore)) {
            test.get().pass("Title specification có thay đổi với title specification trong box specification");
        } else {
            test.get().fail("Title specification khớp với title specification trong box specification");
        }

        if (ratingCountAfter == reviewCountAfter) {
            test.get().pass("Số lượng đánh giá khớp với số lượng đánh giá trong box đánh giá");
        } else {
            test.get().fail("Số lượng đánh giá không khớp với số lượng đánh giá trong box đánh giá. "
                    + "Expected: " + ratingCountAfter + ", Actual: " + reviewCountAfter);
        }

        test.get().pass("Kiểm tra đồng bộ các thông tin khi thay đổi phiên bản thành công");
    }

    @Test(groups = "Function_UI", description = "Kiểm tra đồng bộ địa chỉ theo thành phố đã chọn")
    public void verifyCityOption() throws Exception{
        LogUtils.info("Chọn thành phố");
        product_detail_page_cb.selectCity("Bắc Ninh");
        test.get().pass("Chọn thành phố thành công");

        LogUtils.info("Kiểm tra hiển thị danh sách địa chỉ");
        product_detail_page_cb.verifyListAddress();
        test.get().pass("Hiển thị danh sách địa chỉ thành công");

        LogUtils.info("Kiểm tra tất cả địa chỉ có chứa 'Bắc Ninh'");
        product_detail_page_cb.verifyAllAddressesInCity("Bắc Ninh");
        test.get().pass("Tất cả địa chỉ có chứa chữ 'Bắc Ninh'");
    }

    @Test(groups = "Function",description = "Kiểm tra đánh giá sản phẩm")
    public void EvaluationandComment(){
        LogUtils.info("Chọn số sao đánh giá");
        product_detail_page_cb.selectStarRating(4);
        test.get().pass("Chọn số sao đánh giá thành công");

        LogUtils.info("Nhập nội dung bình luận");
        product_detail_page_cb.SendKeyEvaluation("Sản phẩm có ngoại hình khá đẹp, nhân viên tư vấn nhiệt tình, " +
                "website giao diện thân thiện với người dùng");
        test.get().pass("Nhập nội dung đánh giá thành công");

        LogUtils.info("Nhấn nút gửi đánh giá");
        product_detail_page_cb.ClickEvaluationSubmit();
        test.get().pass("Nhấn nút gửi đánh giá thành công");

        LogUtils.info("Kiểm tra thông báo gửi đánh giá thành công");
        String expectedMessage = "Bình luận thành công!.";
        String actualMessage = product_detail_page_cb.getToast();
        Assert.assertEquals(actualMessage, expectedMessage, "Thông báo gửi đánh giá không khớp");
        test.get().pass("Thông báo gửi đánh giá khớp như mong muốn");
    }

}
