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
        Assert.assertNotEquals(titleProductBefore, titleProductAfter, "Tiêu đề sản phẩm không thay đổi sau khi chọn phiên bản");
        test.get().pass(" Tiêu đề sản phẩm thay đổi sau khi chọn phiên bản");
        Assert.assertNotEquals(priceProductBefore, priceProductAfter, "Giá sản phẩm không thay đổi sau khi chọn phiên bản");
        test.get().pass("Giá sản phẩm thay đổi sau khi chọn phiên bản");
        Assert.assertNotEquals(warrantyActiveBefore, warrantyActiveAfter, "Thông tin bảo hành không thay đổi sau khi chọn phiên bản");
        test.get().pass("Thống tin bảo hành thay đổi sau khi chọn phiên bản");
        Assert.assertNotEquals(warrantyContentBefore, warrantyContentAfter, "Nội dung bảo hành không thay đổi sau khi chọn phiên bản");
        test.get().pass("Nội dung bảo hành thay đổi sau khi chọn phiên bản");
        Assert.assertNotEquals(reviewCountBefore, reviewCountAfter, "Số lượng đánh giá không thay đổi sau khi chọn phiên bản");
        test.get().pass("Số lượng đánh giá thay đổi sau khi chọn phiên bản");
        test.get().pass("Số lượng sao thay đổi sau khi chọn phiên bản");
        Assert.assertNotEquals(versionNameBefore, versionNameAfter, "Tên phiên bản không thay đổi sau khi chọn phiên bản");
        test.get().pass("Ten phiên bản thay đổi sau khi chọn phiên bản");
        Assert.assertNotEquals(versionPriceBefore, versionPriceAfter, "Giá phiên bản không thay đổi sau khi chọn phiên bản");
        test.get().pass("Giá phiên bản thay đổi sau khi chọn phiên bản");
        Assert.assertNotEquals(buyNowPriceBefore, buyNowPriceAfter, "Giá mua ngay không thay đổi sau khi chọn phiên bản");
        test.get().pass("Giá mua ngay thay đổi sau khi chọn phiên bản");
        Assert.assertNotEquals(ratingCountBefore, ratingCountAfter, "Số lượng đánh giá không thay đổi sau khi chọn phiên bản");
        test.get().pass("Số lượng đánh giá thay đổi sau khi chọn phiên bản");
        Assert.assertEquals(versionPriceAfter,warrantyContentAfter,"Giá sản phẩm không khớp với giá bảo hành");
        test.get().pass("Giá sản phẩm khớp với giá bảo hành");
        Assert.assertNotEquals(CustomerServiceBefore, CustomerServiceAfter,"Thông tin dịch vụ khách hàng không thay đổi sau khi chọn phiên bản");
        test.get().pass("Thống tin dịch vụ khách hàng thay đổi sau khi chọn phiên bản");
        Assert.assertNotEquals(titleSpecificationAfter, titleSpecificationBefore,"Title specification khớp với title specification trong box specification");
        test.get().pass("Title specification có thay đổi với title specification trong box specification");
        Assert.assertEquals(ratingCountAfter, reviewCountAfter,"Số lượng đánh giá không khớp với số lượng đánh giá trong box đánh giá");
        test.get().pass("Số lượng đánh giá khớp với số lượng đánh giá trong box đan giá");

        test.get().pass("Kiểm tra đồng bộ các thông tin khi thay đổi phiên bản thành công");
    }

    @Test(groups = "Function", description = "Kiểm tra đồng bộ địa chỉ theo thành phố đã chọn")
    public void verifyCityOption() {
        LogUtils.info("Chọn thành phố");
        product_detail_page_cb.selectCity("Bắc Ninh");
        test.get().pass("Chọn thành phố thành công");

        LogUtils.info("Kiểm tra hiển thị danh sách địa chỉ");
        product_detail_page_cb.verifyListAddress();
        test.get().pass("Hiển thị danh sách địa chỉ thành công");

        LogUtils.info("Kiểm tra tất cả địa chỉ có chứa 'Bắc Ninh'");
        product_detail_page_cb.verifyAllAddressesInCity("Bắc Ninh");
        test.get().pass("Kiểm tra tất cả địa chỉ có chúa 'Bắc Ninh'");
    }

//    @Test(groups = "Function",description = "Kiểm tra hiển thị thông tin khi nhập tư vấn qua điện thoại")
//    public void PhoneContact(){
//
//    }
//
//    @Test(groups = "Function",description = "Kiểm tra đánh giá sản phẩm")
//    public void Evaluation(){
//
//
//    }
//
//    @Test(groups = "Function",description = "Kiểm tra hiển thị thông tin khi nhập tư vấn qua email")
//    public void verifyProductDetail(){
//
//    }
}
