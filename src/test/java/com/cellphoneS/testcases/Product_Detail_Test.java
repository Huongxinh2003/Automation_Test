package com.cellphoneS.testcases;

import com.cellphoneS.bases.BaseSetup;
import com.cellphoneS.bases.SignIn_Helpers;
import com.cellphoneS.pages.Homepage_page;
import com.cellphoneS.pages.Product_Detail_Page;
import com.cellphoneS.pages.Search_Page;
import com.helpers.CaptureHelpers;
import com.helpers.RecordVideo;
import com.helpers.ValidateUIHelper;
import com.ultilities.ExcelUtils;
import com.ultilities.LogUtils;
import com.ultilities.Properties_File;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class Product_Detail_Test extends BaseSetup {

    private static final Logger log = LoggerFactory.getLogger(Search_Test.class);
    private WebDriver driver;
    public WebDriverWait wait;
    public ExcelUtils excelHelper;
    public ValidateUIHelper validateUIHelper;
    public Product_Detail_Page product_detail_page;
    public SignIn_Helpers signIn_helpers;
    public Homepage_page homepage_page;
    public Search_Page search_page;

    @BeforeClass
    public void setupDriver() throws Exception {
        //gọi hàm khởi tạo properties
        Properties_File.setPropertiesFile();
//        // Gọi lại hàm startRecord
//        try {
//            RecordVideo.startRecord("RecordVideo");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        // Lấy driver từ class cha BaseSetup
        driver = setupDriver(Properties_File.getPropValue("browser"));
        excelHelper = new ExcelUtils();
        validateUIHelper = new ValidateUIHelper(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        signIn_helpers = new SignIn_Helpers(driver);
        homepage_page = signIn_helpers.SignIn(driver);
        search_page = homepage_page.openSearchPage();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterMethod
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

    @BeforeMethod
    public void SearchProduct() {
        LogUtils.info("Thực hiện tìm kiếm sản phẩm 'iphone' và mở trang chi tiết");
        product_detail_page = search_page.openProductDetail("iphone");
    }

    @Test
    public void Search() {
        LogUtils.info("Kiểm tra hiển thị tiêu đề sản phẩm");
        product_detail_page.isTitleProductDisplayed();
        String title = product_detail_page.getTitleProduct();
        LogUtils.info("Tiêu đề sản phẩm " + title);
        Assert.assertTrue(product_detail_page.isTitleProductDisplayed(), "Tiêu đề sản phẩm không hiển thị");
    }

    @Test
    public void FavoriteProduct() {
        LogUtils.info("Kiểm tra hiển thị nút yêu thích");
        product_detail_page.isFavoriteProductDisplayed();

        LogUtils.info("Kiểm tra thêm sản phẩm vào mục yêu thích");
        product_detail_page.ClickFavoriteProduct();
        LogUtils.info("Kiểm tra hiển thị thông báo thêm vào mục yêu thích");
        By alertBox = By.xpath("//b[contains(text(),'Đã thêm vào danh sách yêu thích!')]");
        WebElement alertAdd = wait.until(ExpectedConditions.visibilityOfElementLocated(alertBox));
        Assert.assertTrue(alertAdd.isDisplayed(), "Thông báo thêm vào mục yêu thích không hiển thị");

        LogUtils.info("Kiểm tra mau sắc của thông báo thêm vào yêu thích");
        String backgroundColor = alertAdd.getCssValue("background");
        LogUtils.info("Màu nền của thông báo: " + backgroundColor);

        LogUtils.info("Kiểm tra hiển thị nút xóa mục yêu thích");
        product_detail_page.DeleteFavoriteProduct();
        LogUtils.info("Kiểm tra hiển thị thông báo xóa mục yêu thích");
        By alertBox1 = By.xpath("//div[@class='toasted toasted-primary default']");
        WebElement alertDetele = wait.until(ExpectedConditions.visibilityOfElementLocated(alertBox1));
        Assert.assertTrue(alertDetele.isDisplayed(), "Thông báo xóa mục yêu thích không hiển thị");

        LogUtils.info("Kiểm tra mau sắc của thông báo khi bỏ yêu thích");
        String backgroundColor1 = alertAdd.getCssValue("background");
        LogUtils.info("Màu nền của thông báo: " + backgroundColor1);
    }

    @Test
    public void verifyDiscountCalculation() {
        int actualDiscount = product_detail_page.calculateDiscountPercentage();
        // mong đợi khoảng 13%
        Assert.assertEquals(actualDiscount, 13, "Phần trăm giảm không đúng!");
    }


    @Test
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

        LogUtils.info("Chọn phiên bản");
        product_detail_page.selectVersionProduct("512GB");

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

        LogUtils.info("Kiểm tra giá trị sau khi chọn phiên bản");
        Assert.assertNotEquals(TitleProductBefore, TitleProductAfter, "TitleProduct không thay đổi sau khi chọn phiên bản");
        Assert.assertNotEquals(BoxRatingBefore, BoxRatingAfter, "BoxRating không thay đổi sau khi chọn phiên bản");
        Assert.assertNotEquals(BoxPriceBefore, BoxPriceAfter, "BoxPrice không thay đổi sau khi chọn phiên bản");
        Assert.assertNotEquals(DiscountBefore, DiscountAfter, "Giá gốc không thay đổi sau khi chọn phiên bản");
        Assert.assertNotEquals(PriceSaleBefore, PriceSaleAfter, "Giá sale không thay đổi sau khi chọn phiên bản");
        Assert.assertNotEquals(ColorPriceBefore, ColorPriceAfter, "Giá trong nu màu sắc không thay đổi sau khi chọn phiên bản");
        Assert.assertNotEquals(TitleStickyBarBefore, TitleStickyBarAfter, "TitleStickyBar không thay đổi sau khi chọn phiên bản");
        Assert.assertNotEquals(PriceStickyBarBefore, PriceStickyBarAfter, "PriceStickyBar không thay đổi sau khi chọn phiên bản");
        Assert.assertNotEquals(CountStoreBefore, CountStoreAfter, " Số cửa hàng còn hàng không thay đổi sau khi chọn phiên bản");

        LogUtils.info("Tất cả giá trị được thay đổi sau khi chọn phiên bản");
    }

    @Test
    public void verifyChangeAfterSelectColorProduct() {
        LogUtils.info("Chọn phiên bản");
        product_detail_page.selectVersionProduct("512GB");
        LogUtils.info("Chờ trang cập nhật lại");
        validateUIHelper.waitForPageLoaded();

        LogUtils.info("Lưu trạng thái ban đầu ");
        String DiscountBefore = product_detail_page.getBasePrice();
        String PriceSaleBefore = product_detail_page.getSalePrice();
        String ProductThumbnail = product_detail_page.getProductThumbnail();
        String PriceStickyBarBefore = product_detail_page.getPriceStickyBar();
        String CountStoreBefore = product_detail_page.getCountStore();

        LogUtils.info("Chọn màu sắc");
        product_detail_page.selectColorProduct("Titan Đen");

        LogUtils.info("Chờ trang cập nhật lại");
        validateUIHelper.waitForPageLoaded();

        LogUtils.info("Lấy giá trị sau khi chọn màu sắc");
        String DiscountAfter = product_detail_page.getBasePrice();
        String PriceSaleAfter = product_detail_page.getSalePrice();
        String ProductThumbnailAfter = product_detail_page.getProductThumbnail();
        String PriceStickyBarAfter = product_detail_page.getPriceStickyBar();
        String CountStoreAfter = product_detail_page.getCountStore();

        LogUtils.info("Kiểm tra giá trị sau khi chọn màu sắc");
        Assert.assertFalse(Boolean.parseBoolean(DiscountAfter), "Giá gốc thay đổi sau khi chọn màu sắc");
        Assert.assertNotEquals(PriceSaleBefore, PriceSaleAfter, "Giá sale không thay đổi sau khi chọn màu sắc");
        Assert.assertNotEquals(ProductThumbnail, ProductThumbnailAfter, "Ảnh sản phẩm không thay đổi sau khi chọn màu sắc");
        Assert.assertNotEquals(PriceStickyBarBefore, PriceStickyBarAfter, "PriceStickyBar không thay đổi sau khi chọn màu sắc");
        Assert.assertNotEquals(CountStoreBefore, CountStoreAfter, "Số cửa hàng còn hàng không thay đổi sau khi chọn màu sắc");

        LogUtils.info("Tất cả giá trị được thay đổi sau khi chọn màu sắc");
    }
}
