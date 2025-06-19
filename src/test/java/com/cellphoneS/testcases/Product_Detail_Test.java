package com.cellphoneS.testcases;

import com.cellphoneS.bases.BaseSetup;
import com.cellphoneS.bases.SignIn_Helpers;
import com.cellphoneS.pages.Homepage_page;
import com.cellphoneS.pages.Product_Detail_Page;
import com.cellphoneS.pages.Search_Page;
import com.helpers.CaptureHelpers;
import com.helpers.ValidateUIHelper;
import com.ultilities.ExcelUtils;
import com.ultilities.LogUtils;
import com.ultilities.Properties_File;
import org.openqa.selenium.*;
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
        boolean headless = Boolean.parseBoolean(Properties_File.getPropValue("headless"));
        driver = setupDriver(Properties_File.getPropValue("browser"), headless);
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
        Assert.assertTrue(Boolean.parseBoolean(PriceSaleAfter), "Giá sale thay đổi khi chọn màu sắc");
        Assert.assertNotEquals(ProductThumbnail, ProductThumbnailAfter, "Ảnh sản phẩm không thay đổi sau khi chọn màu sắc");
        Assert.assertNotEquals(PriceStickyBarBefore, PriceStickyBarAfter, "PriceStickyBar không thay đổi sau khi chọn màu sắc");
        Assert.assertNotEquals(CountStoreBefore, CountStoreAfter, "Số cửa hàng còn hàng không thay đổi sau khi chọn màu sắc");

        LogUtils.info("Tất cả giá trị được thay đổi sau khi chọn màu sắc");
    }

    @Test
    public void testClickTwoThumbnailsAndPrintMainImage() throws InterruptedException{
        LogUtils.info("Trước khi click");
        Product_Detail_Page.printMainImageSrc();

        LogUtils.info("Click thumbnail 1");
        Product_Detail_Page.clickThumbnail(Product_Detail_Page.ProductThumbnailSmall1);
        Thread.sleep(2000);
        Product_Detail_Page.printMainImageSrc();

        LogUtils.info("Click thumbnail 2");
        Product_Detail_Page.clickThumbnail(Product_Detail_Page.ProductThumbnailSmall2);
        Thread.sleep(2000);
        Product_Detail_Page.printMainImageSrc();
    }

    @Test
    public void testMainImageSyncWithSmallThumbnailOnSwipe() {
        // Ảnh trước khi swipe
        String beforeMainSrc = Product_Detail_Page.getMainImageSrc();
        String beforeThumbSrc = Product_Detail_Page.getActiveSmallThumbnailSrc();

        System.out.println("Trước swipe:");
        System.out.println("Main image: " + beforeMainSrc);
        System.out.println("Thumbnail small: " + beforeThumbSrc);

        // Click next và đợi ảnh lớn thay đổi
        Product_Detail_Page.clickSwiperNextAndWaitForMainImageChange(beforeMainSrc);

        // Lấy ảnh sau khi swipe
        String afterMainSrc = Product_Detail_Page.getMainImageSrc();
        String afterThumbSrc = Product_Detail_Page.getActiveSmallThumbnailSrc();

        System.out.println("Sau swipe:");
        System.out.println("Main image: " + afterMainSrc);
        System.out.println("Thumbnail small: " + afterThumbSrc);

        // So sánh tên file của ảnh chính và thumbnail nhỏ active
        String mainFile = Product_Detail_Page.getFileNameFromUrl(afterMainSrc);
        String thumbFile = Product_Detail_Page.getFileNameFromUrl(afterThumbSrc);

        System.out.println("So sánh file: main=" + mainFile + " | thumb=" + thumbFile);
        Assert.assertTrue(mainFile.contains(thumbFile) || thumbFile.contains(mainFile),
                "Ảnh thumbnail nhỏ KHÔNG đồng bộ với ảnh lớn!");
    }

    @Test
    public void verifyChangeAfterSelectCity() {
        LogUtils.info("Lưu trạng thái ban đầu ");
        String CountStoreBefore = product_detail_page.getCountStore();
        String CityBefore = product_detail_page.getCityName();
        LogUtils.info("Số lượng cửa hàng trong thành phố " + CityBefore + " là " + CountStoreBefore);


        LogUtils.info("Click button Thành phố");
        Product_Detail_Page.ClickCity();

        LogUtils.info("Chọn Thành phố");
        Product_Detail_Page.ClickSelectCity("Đà Nẵng");


        LogUtils.info("Chờ trang cập nhật lại");
        validateUIHelper.waitForPageLoaded();

        LogUtils.info("Lấy giá trị sau khi chọn Thành phố");
        String CountStoreAfter = product_detail_page.getCountStore();
        String CityAfter = product_detail_page.getCityName();
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", CityAfter);

        LogUtils.info("Kiểm tra giá trị sau khi chọn Thành phố");
        Assert.assertNotEquals(CityBefore, CityAfter, "Thành phố không thay đổi sau khi chọn Thành phố");
        Assert.assertNotEquals(CountStoreBefore, CountStoreAfter, " Số cửa hàng còn hàng không thay đổi sau khi chọn Thành phố");

        LogUtils.info("Kiểm tra box có chứa tên thành phố đã chọn");
        Assert.assertTrue(
                Product_Detail_Page.isAddressBoxContainsCity(CityAfter),
                "Box không chứa tên thành phố đã chọn: " + CityAfter
        );

        LogUtils.info("Nội dung box địa chỉ: " + Product_Detail_Page.getAddressText());

        LogUtils.info("Số lượng cửa hàng trong thành phố " + CityAfter + " là " + CountStoreAfter);
        LogUtils.info("Tất cả giá trị được thay đổi sau khi chọn Thành phố");
    }

    @Test
    public void verifyChangeAfterSelectDistrict() {
        LogUtils.info("Lưu trạng thái ban đầu ");
        String CountStoreBefore = product_detail_page.getCountStore();
        String DistrictBefore = product_detail_page.getDistrictName();

        LogUtils.info("Chọn Quận");
        Product_Detail_Page.ClickSelectDistrict();
        LogUtils.info("Chờ trang cập nhật lại");
        validateUIHelper.waitForPageLoaded();

        LogUtils.info("Lấy giá trị sau khi chọn Quận");
        String CityAfter = product_detail_page.getCityName();
        String CountStoreAfter = product_detail_page.getCountStore();
        String DistrictAfter = product_detail_page.getDistrictName();
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", CityAfter);

        LogUtils.info("Kiểm tra giá trị sau khi chọn Quận");
        Assert.assertTrue(CityAfter.contains("Đà Nẵng"), "Thành phố không thay đổi sau khi chọn Quận");
        Assert.assertNotEquals(DistrictBefore, DistrictAfter, "Quận không thay đổi sau khi chọn Quận");
        Assert.assertNotEquals(CountStoreBefore, CountStoreAfter, " Số cửa hàng còn hàng không thay đổi sau khi chọn Quận");

        LogUtils.info("Kiểm tra box có chứa tên Quận đã chọn");
        Product_Detail_Page.isAddressBoxContainsCityAndDistrict(CityAfter, DistrictAfter);

        LogUtils.info("Nội dung box địa chỉ: " + Product_Detail_Page.getAddressText2());

        LogUtils.info("Số lượng cửa hàng trong Quận " + DistrictAfter + " là " + CountStoreAfter);
        LogUtils.info("Tất cả giá trị được thay đổi sau khi chọn Quận");
    }
}
