package com.CellphoneS.testcases;

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
import org.testng.annotations.*;

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

    @BeforeClass (groups = {"UI_Test", "Function","Function_UI"})
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

    @BeforeMethod (groups = {"Function", "UI_Test", "Function_UI"})
    public void SearchProduct1() {
        LogUtils.info("Thực hiện tìm kiếm sản phẩm 'iphone' và mở trang chi tiết");
        product_detail_page = search_page.openProductDetail("iphone");
    }

    @Test (groups = "UI_Test",priority = 1, description = "Kiểm tra title của trang chi tiết sản phẩm")
    public void Search() {
        LogUtils.info("Kiểm tra hiển thị tiêu đề sản phẩm");
        Assert.assertTrue(product_detail_page.isTitleProductDisplayed(), "Tiêu đề sản phẩm không hiển thị");

        LogUtils.info("Lấy tiêu đề trang");
        String title = product_detail_page.getTitleProduct();
        LogUtils.info("Tiêu đề sản phẩm " + title);
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

    @Test (groups = "Function",priority = 2, description = "Kiểm tra phần trang giảm giá của sản phẩm")
    public void verifyDiscountCalculation() {
        int actualDiscount = product_detail_page.calculateDiscountPercentage();
        // mong đợi khoảng 13%
        Assert.assertEquals(actualDiscount, 13, "Phần trăm giảm không đúng!");
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

        LogUtils.info("Kiểm tra giá trị sau khi chọn phiên bản");
        LogUtils.info("Title sản pẩm: "+ TitleProductBefore);
        LogUtils.info("Title sản phẩm: " + TitleProductAfter);
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
//        LogUtils.info("Giá sale: "+ DiscountAfter);
        Assert.assertEquals(DiscountAfter, DiscountBefore, "Giá gốc không thay đổi sau khi chọn màu sắc");
        Assert.assertEquals(PriceSaleBefore, PriceSaleAfter, "Giá sale không thay đổi sau khi chọn màu sắc");
//        Assert.assertFalse(Boolean.parseBoolean(DiscountAfter), "Giá gốc thay đổi sau khi chọn màu sắc");
//        Assert.assertTrue(Boolean.parseBoolean(PriceSaleAfter), "Giá sale thay đổi khi chọn màu sắc");
        Assert.assertNotEquals(ProductThumbnail, ProductThumbnailAfter, "Ảnh sản phẩm không thay đổi sau khi chọn màu sắc");
        Assert.assertNotEquals(PriceStickyBarBefore, PriceStickyBarAfter, "PriceStickyBar không thay đổi sau khi chọn màu sắc");
        Assert.assertNotEquals(CountStoreBefore, CountStoreAfter, "Số cửa hàng còn hàng không thay đổi sau khi chọn màu sắc");

        LogUtils.info("Tất cả giá trị được thay đổi sau khi chọn màu sắc");
    }

    //Kiểm tra lại
    @Test (groups = "Test_Fail")
    public void testClickTwoThumbnailsAndPrintMainImage() throws InterruptedException{
        LogUtils.info("Trước khi click");
        product_detail_page.printMainImageSrc();

        LogUtils.info("Click thumbnail 1");
        product_detail_page.clickThumbnail(product_detail_page.ProductThumbnailSmall1);
        Thread.sleep(2000);
        product_detail_page.printMainImageSrc();

        LogUtils.info("Click thumbnail 2");
        product_detail_page.clickThumbnail(product_detail_page.ProductThumbnailSmall2);
        Thread.sleep(2000);
        product_detail_page.printMainImageSrc();
    }

    //Kiểm tra lại
    @Test (groups = "Test_Fail")
    public void testMainImageSyncWithSmallThumbnailOnSwipe() {
        // Ảnh trước khi swipe
        String beforeMainSrc = product_detail_page.getMainImageSrc();
        String beforeThumbSrc = product_detail_page.getActiveSmallThumbnailSrc();

        System.out.println("Trước swipe:");
        System.out.println("Main image: " + beforeMainSrc);
        System.out.println("Thumbnail small: " + beforeThumbSrc);

        // Click next và đợi ảnh lớn thay đổi
        product_detail_page.clickSwiperNextAndWaitForMainImageChange(beforeMainSrc);

        // Lấy ảnh sau khi swipe
        String afterMainSrc = product_detail_page.getMainImageSrc();
        String afterThumbSrc = product_detail_page.getActiveSmallThumbnailSrc();

        System.out.println("Sau swipe:");
        System.out.println("Main image: " + afterMainSrc);
        System.out.println("Thumbnail small: " + afterThumbSrc);

        // So sánh tên file của ảnh chính và thumbnail nhỏ active
        String mainFile = product_detail_page.getFileNameFromUrl(afterMainSrc);
        String thumbFile = product_detail_page.getFileNameFromUrl(afterThumbSrc);

        System.out.println("So sánh file: main=" + mainFile + " | thumb=" + thumbFile);
        Assert.assertTrue(mainFile.contains(thumbFile) || thumbFile.contains(mainFile),
                "Ảnh thumbnail nhỏ KHÔNG đồng bộ với ảnh lớn!");
    }

    @Test (groups = "Function",priority = 3, description = "Kiểm tra popup chọn thành phố")
    public void verifyChangeAfterSelectCity() {
        product_detail_page.ScrollToElement();
        LogUtils.info("Lưu trạng thái ban đầu ");
        String CountStoreBefore = product_detail_page.getCountStore();
        String CityBefore = product_detail_page.getCityName();
        LogUtils.info("Số lượng cửa hàng trong thành phố " + CityBefore + " là " + CountStoreBefore);

        ((JavascriptExecutor) driver).executeScript("location.reload();");

        LogUtils.info("Click button Thành phố");
        product_detail_page.ClickCity();

        LogUtils.info("Chọn Thành phố");
        product_detail_page.ClickSelectCity("Hồ Chí Minh");


        LogUtils.info("Chờ trang cập nhật lại");
        validateUIHelper.waitForPageLoaded();

        LogUtils.info("Lấy giá trị sau khi chọn Thành phố");
        String CountStoreAfter = product_detail_page.getCountStore();
        String CityAfter = product_detail_page.getCityName();
//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", CityAfter);

        LogUtils.info("Kiểm tra giá trị sau khi chọn Thành phố");
        Assert.assertNotEquals(CityBefore, CityAfter, "Thành phố không thay đổi sau khi chọn Thành phố");

        if (CountStoreBefore == CountStoreAfter) {
            LogUtils.info("Số của hàng của hai thành phố bằng nhau");
        }else {
            Assert.assertNotEquals(CountStoreBefore, CountStoreAfter, "Số cửa hàng còn hàng không thay đổi sau khi chọn Thành phố");
            test.get().info("Số cửa hàng còn hàng thay đổi sau khi chọn Thành phố");
        }

        LogUtils.info("Kiểm tra box có chứa tên thành phố đã chọn");
        Assert.assertTrue(
                product_detail_page.isAddressBoxContainsCity(CityAfter),
                "Box không chứa tên thành phố đã chọn: " + CityAfter
        );

        LogUtils.info("Nội dung box địa chỉ: " + product_detail_page.getAddressText());

        LogUtils.info("Số lượng cửa hàng trong thành phố " + CityAfter + " là " + CountStoreAfter);
        LogUtils.info("Tất cả giá trị được thay đổi sau khi chọn Thành phố");
    }

    //Mặc định chọn "HCM"
    @Test (groups = "Function",priority = 4, description = "Kiểm tra dropdown chọn quận")
    public void verifyChangeAfterSelectDistrict() {
        product_detail_page.ScrollToElement();
        LogUtils.info("Lưu trạng thái ban đầu ");
        String CountStoreBefore = product_detail_page.getCountStore();
        String DistrictBefore = product_detail_page.getDistrictName();

        ((JavascriptExecutor) driver).executeScript("location.reload();");

        LogUtils.info("Chọn Quận");
        String districtName = product_detail_page.ClickSelectDistrict("Quận Thủ Đức");
        LogUtils.info("Chờ trang cập nhật lại");
        validateUIHelper.waitForPageLoaded();

        LogUtils.info("Lấy giá trị sau khi chọn Quận");
        String CityAfter = product_detail_page.getCityName();
        String CountStoreAfter = product_detail_page.getCountStore();
        String DistrictAfter = product_detail_page.getDistrictName();

//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", CityAfter);

        LogUtils.info("Kiểm tra giá trị sau khi chọn Quận");
        Assert.assertNotEquals(DistrictBefore, DistrictAfter, "Quận không thay đổi sau khi chọn");
        Assert.assertNotEquals(CountStoreBefore, CountStoreAfter, " Số cửa hàng còn hàng không thay đổi sau khi chọn Quận");

        LogUtils.info("Kiểm tra box có chứa tên Quận đã chọn");
        product_detail_page.isAddressBoxContainsCityAndDistrict(CityAfter, DistrictAfter);

        LogUtils.info("Nội dung box địa chỉ: " + product_detail_page.getAddressText2());

        LogUtils.info("Số lượng cửa hàng trong Quận " + districtName + " là " + CountStoreAfter);
        LogUtils.info("Tất cả giá trị được thay đổi sau khi chọn Quận");
    }

    @Test (groups = "Function",priority = 5, description = "Kiểm tra chuyển màn hình sang giỏ hàng")
    public void BuyProduct() {
        LogUtils.info("Chọn phiên bản");
        product_detail_page.selectVersionProduct("1TB");
        LogUtils.info("Chờ trang cập nhật lại");
        validateUIHelper.waitForPageLoaded();
        LogUtils.info("Chọn màu sắc");
        product_detail_page.selectColorProduct("Titan Đen");
        LogUtils.info("Chờ trang cập nhật lại");
        validateUIHelper.waitForPageLoaded();

        LogUtils.info("Click button Mua ngay");
        product_detail_page.ClickBuyNow();

        LogUtils.info("Kiểm tra chuyển màn hình sang giỏ hàng");
        String expectedUrl = "https://cellphones.com.vn/cart/";
        String actualUrl = driver.getCurrentUrl();

        Assert.assertEquals("URL không đúng sau khi click 'Mua ngay'", expectedUrl, actualUrl);
    }

    @Test (groups = "UI_Test",priority = 4, description = "Kiểm tra thông báo khi chuyển màn hình sang giỏ hàng")
    public void AddProductToCart() {
        LogUtils.info("Chọn phiên bản");
        product_detail_page.selectVersionProduct("1TB");
        LogUtils.info("Chờ trang cập nhật lại");
        validateUIHelper.waitForPageLoaded();
        LogUtils.info("Chọn màu sắc");
        product_detail_page.selectColorProduct("Titan Đen");
        LogUtils.info("Chờ trang cập nhật lại");
        validateUIHelper.waitForPageLoaded();

        LogUtils.info("Click button Thêm vào giỏ hàng");
        product_detail_page.ClickAddToCart();
        LogUtils.info("Kiểm tra chuyển màn hình sang giỏ hàng");

        By successAddToCart = By.xpath("//div[@class='toasted toasted-primary success']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(successAddToCart));
        WebElement toast = driver.findElement(successAddToCart);
        assertTrue(toast.isDisplayed(), "Không hiển thị thông báo khi click button Thêm vào giỏ hàng");
    }

    @Test (groups = "Function",priority = 6, description = "Kiểm tra chuyển màn hình sang tab Trả góp")
    public void ClickInstallmentOption2() {
        LogUtils.info("Click button Thanh toán trả góp 0%");
        product_detail_page.ClickInstallmentOption();
        product_detail_page.ClickInstallmentOption2();

        LogUtils.info("Kiểm tra thông báo khi click button Thanh toán trả góp 0%");
        product_detail_page.isToastMessageDisplayed();

        LogUtils.info("Kiểm tra sản phẩm ở tab 'Trả góp'");
        String activeTab = product_detail_page.getActiveTabText();
        Assert.assertTrue(activeTab.contains("Trả góp"), "Tab không phải 'Trả góp'");

    }

    @Test (groups = "Function",priority = 7, description = "Kiểm tra chuyển màn hình sang tab Trả góp")
    public void ClickInstallmentOption3() {
        LogUtils.info("Click button Thanh toán trả góp qua thẻ");
        product_detail_page.ClickInstallmentOption();
        product_detail_page.ClickInstallmentOption3();

        LogUtils.info("Kiểm tra thông báo khi click button Thanh toán trả góp qua thẻ");
        product_detail_page.isToastMessageDisplayed();

        LogUtils.info("Kiểm tra sản phẩm ở tab 'Trả góp'");
        String activeTab = product_detail_page.getActiveTabText();
        Assert.assertTrue(activeTab.contains("Trả góp"), "Tab không phải 'Trả góp'");


    }

    @Test (groups = "Function",priority = 8)
    public void EvaluateProduct() {
        LogUtils.info("Lưu trạng thái ban đầu ");
        String EvaluateBefore = product_detail_page.getCountEvaluateProduct();
        LogUtils.info("Số lượng đánh giá của sản phẩm là " + EvaluateBefore);

        LogUtils.info("Click button Đánh giá sản phẩm");
        product_detail_page.ClickButtonEvaluate();
        product_detail_page.ClickEvaluateStar();
        product_detail_page.ClickEvaluatePerformance();
        product_detail_page.ClickEvaluateBatteryLife();
        product_detail_page.ClickEvaluateCamera();
        product_detail_page.InputEvaluateComment("Sản phẩm trên cả tuyệt vời luôn ấy");
        product_detail_page.ClickEvaluateImage("C:\\Users\\Admin\\Pictures\\kk.jpg");
        product_detail_page.ClickEvaluateButton();

        LogUtils.info("Kiểm tra thông báo khi đánh giá sản phẩm");
        By successEvaluate = By.xpath("//b[contains(text(),'CellphoneS đã nhận được phản hồi của bạn')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(successEvaluate));
        WebElement toast = driver.findElement(successEvaluate);
        assertTrue(toast.isDisplayed(), "Không hiển thị thông báo khi đánh giá sản phẩm");

        LogUtils.info("Lấy giá trị sau khi đánh giá sản phẩm");
        String EvaluateAfter = product_detail_page.getCountEvaluateProduct();
        LogUtils.info("Số lượng đánh giá của sản phẩm là " + EvaluateAfter);

        LogUtils.info("Kiểm tra số lượng đánh giá của sản phẩm");
        Assert.assertNotEquals(EvaluateBefore, EvaluateAfter, "Số lượng đánh giá của sản phẩm thay đổi");
    }

}
