package com.CellphoneS.test;

import com.base.BaseSetup;
import com.CellphoneS.pages.Homepage_page;
import com.CellphoneS.pages.Search_Page;
import com.ultilities.ExcelUtils;
import com.helpers.ValidateUIHelper;
import com.CellphoneS.pages.SignIn_Page;
import com.ultilities.listeners.ReportListener;
import com.ultilities.logs.LogUtils;
import com.ultilities.Properties_File;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v137.network.Network;
import org.openqa.selenium.devtools.v137.network.model.ConnectionType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

@Listeners(ReportListener.class)
public class SignIn_Test extends BaseSetup{
    private static final Logger log = LoggerFactory.getLogger(SignIn_Test.class);
    private WebDriver driver;
    public SignIn_Page signIn_page;
    public WebDriverWait wait;
    public ExcelUtils excelHelper;
    public ValidateUIHelper validateUIHelper;
    public Search_Page search_Page;
    public Homepage_page homepage_page;

    @BeforeClass(groups = {"SignIn_Success", "Function", "Validate_SĐT", "Validate_MK"})
    public void setUp() throws Exception {
        //gọi hàm khởi tạo properties
        Properties_File.setPropertiesFile();
        boolean headless = Boolean.parseBoolean(Properties_File.getPropValue("headless"));
        driver = setupDriver(Properties_File.getPropValue("browser"), headless);
        signIn_page = new SignIn_Page(driver);
        excelHelper = new ExcelUtils();
        validateUIHelper = new ValidateUIHelper(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));

    }

    @BeforeMethod (groups = {"SignIn_Success", "Function", "Validate_SĐT", "Validate_MK"})
    public void setUpMethod() {
        driver.get("https://cellphones.com.vn/");
        validateUIHelper.waitForPageLoaded();
//        signIn_page.closePopupIfVisible();
    }

    @Test(groups = {"SignIn_Success"}, priority = 1)
    public void login_cellphoneS_Success() throws Exception {
        signIn_page.SignIn();
        homepage_page = signIn_page.InputSignIn(Properties_File.getPropValue("phonenumber"),
                Properties_File.getPropValue("password"));
        LogUtils.info("Đăng nhập thành công");

        LogUtils.info("Kiểm tra thông báo");
        By successToast = By.xpath("//*[contains(text(),'Đăng nhập')]");
        WebElement successToastElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(successToast));
        assertTrue(successToastElement.isDisplayed(),
                "Không hiển thị thông báo 'Đăng nhập thành công'!");

        LogUtils.info("Kiểm tra chuyển sang");
        boolean isOnHomepage = wait.until(ExpectedConditions.urlToBe("https://cellphones.com.vn/"));
        assertTrue(isOnHomepage, "Không tự động chuyển về trang chủ sau đăng nhập!");
    }

    @Test(groups = "Function", priority = 1)
    public void login_cellphoneS_17() throws Exception {
        signIn_page.SignIn();
        LogUtils.info("Đăng nhập thất bại khi nhập SĐT sai");
        excelHelper.setExcelFile("src/test/resources/SignIn.xlsx", "login");
        signIn_page.InputSignIn(excelHelper.getCellData("phonenumber", 2),
                excelHelper.getCellData("password", 2));

        LogUtils.info("Kiểm tra Alert lỗi trường Mật khẩu");
        By alertBox = By.xpath("//li[contains(.,'Số điện thoại hoặc mật khẩu không hợp lệ')]");
        WebElement alertElement = wait.until(ExpectedConditions.visibilityOfElementLocated(alertBox));
        assertTrue(alertElement.isDisplayed(), "Không hiển thị thông báo lỗi khi nhập sai SĐT!");

        LogUtils.info("Kiểm tra inline lỗi trường SĐT");
        By phoneInlineError = By.xpath("//*[contains(text(),'Số điện thoại không hợp lệ')]");
        WebElement inlineError = wait.until(ExpectedConditions.visibilityOfElementLocated(phoneInlineError));
        assertTrue(inlineError.isDisplayed(), "Không hiển thị inline lỗi tại trường SĐT!");

    }


    @Test(groups = "Function", priority = 2)
    public void login_cellphoneS_18() throws Exception {
        LogUtils.info("Đăng nhập thất bại khi nhập Mât khẩu sai");
        excelHelper.setExcelFile("src/test/resources/SignIn.xlsx", "login");
        signIn_page.SignIn();
        signIn_page.InputSignIn(excelHelper.getCellData("phonenumber", 3),
                excelHelper.getCellData("password", 3));

        LogUtils.info("Kiểm tra Alert lỗi");
        By alertBox = By.xpath("//ol[@class='toaster group']//li");
        WebElement alertElement = wait.until(ExpectedConditions.visibilityOfElementLocated(alertBox));

        String actualMessage = alertElement.getText();
        String expectedMessage = "Thông tin đăng nhập không đúng. Vui lòng kiểm tra và thử lại.";
        assertEquals(actualMessage, expectedMessage, "Thông báo lỗi không đúng khi nhập Mật Khẩu sai");
    }

    @Test(groups = "Function", priority = 3)
    public void login_cellphoneS_19() throws Exception {
        LogUtils.info("Bỏ trống SĐT và Mật khẩu");
        excelHelper.setExcelFile("src/test/resources/SignIn.xlsx", "login");
        signIn_page.SignIn();
        signIn_page.InputSignIn(excelHelper.getCellData("phonenumber", 4),
                excelHelper.getCellData("password", 4));

        LogUtils.info("Kiểm tra Alert lỗi");
        By alertBox = By.xpath("//ol[@class='toaster group']//li");
        WebElement alertElement = wait.until(ExpectedConditions.visibilityOfElementLocated(alertBox));
        String alertText = alertElement.getText();
        LogUtils.info(alertText);

        String actualMessage = alertElement.getText();
        String expectedMessage = "Vui lòng nhập email và mật khẩu để tiếp tục.";
        Assert.assertEquals(actualMessage, expectedMessage, "Thông báo lỗi không đúng khi bỏ trống SĐT và mật khẩu!");
        LogUtils.info("End testcase: login_cellphoneS_19");
    }

    @Test(groups = "Function", priority = 4)
    public void login_cellphoneS_20_46() throws Exception {
        LogUtils.info("Bỏ trống Mật khẩu");
        excelHelper.setExcelFile("src/test/resources/SignIn.xlsx", "login");
        signIn_page.SignIn();
        signIn_page.InputSignIn(excelHelper.getCellData("phonenumber", 5), excelHelper.getCellData("password", 5));

        LogUtils.info("Kiểm tra Alert lỗi");
        By alertBox = By.xpath("//li[contains(.,'Mật khẩu không được bỏ trống')]");
        boolean alertElement = wait.until(ExpectedConditions.visibilityOfElementLocated(alertBox)).isDisplayed();
        assertTrue(alertElement, "Hiển thị sai thông báo lỗi khi bỏ trống mật khẩu!");


    }

    @Test(groups = "Function", priority = 5)
    public void login_cellphoneS_21() throws Exception {
        LogUtils.info("Bỏ trống SĐT");
        excelHelper.setExcelFile("src/test/resources/SignIn.xlsx", "login");
        signIn_page.SignIn();
        signIn_page.InputSignIn(excelHelper.getCellData("phonenumber", 6),
                excelHelper.getCellData("password", 6));

        LogUtils.info("Kiểm tra Alert lỗi");
        By alertBox = By.xpath("//li[contains(.,'Số điện thoại không được bỏ trống')]");
        boolean alertElement = wait.until
                (ExpectedConditions.visibilityOfElementLocated(alertBox)).isDisplayed();
        assertTrue(alertElement, "Hiển thị sai thông báo lỗi khi bỏ trống SĐT!");
    }

    @Test(groups = "Function", priority = 6)
    public void login_cellphoneS_24() throws Exception {
        LogUtils.info("Đăng nhập thất bại khi mất mạng");
        signIn_page.SignIn();
        signIn_page.InputSignIn2_noclick(Properties_File.getPropValue("phonenumber"), Properties_File.getPropValue("password"));

        // Bắt đầu phiên DevTools để mô phỏng mất mạng
        DevTools devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        devTools.send(Network.emulateNetworkConditions(
                true, // offline
                100,
                0,
                0,
                Optional.of(ConnectionType.CELLULAR3G),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        ));
        LogUtils.info("Đã giả lập mất mạng");

        signIn_page.ClickSignIn();

        By alertBox = By.xpath("//li[contains(.,'Đăng nhập thất bại')]");
        boolean alertElement = wait.until(ExpectedConditions.visibilityOfElementLocated(alertBox)).isDisplayed();
        assertTrue(alertElement, "Hiển thị sai thông báo lỗi khi bỏ trống SĐT!");

        devTools.send(Network.emulateNetworkConditions(
                false, // 🔄 Bật lại kết nối mạng (offline = false)
                100,   // latency
                50000, // downloadThroughput (giả lập khoảng 50kb/s)
                50000, // uploadThroughput
                Optional.of(ConnectionType.ETHERNET), // connectionType
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        ));
        LogUtils.info("Đã bật lại kết nối mạng");
        signIn_page.ClickSignIn();

        By alertBox2 = By.xpath("//li[contains(.,'Đăng nhập thành công')]");
        WebElement alertElement2 = wait.until(ExpectedConditions.visibilityOfElementLocated(alertBox2));
        assertTrue(alertElement2.isDisplayed(), "Không hiển thị thông báo lỗi khi Đăng nhập thành công!");
    }

    @Test(groups = "Validate_SĐT", priority = 1)
    public void login_cellphoneS_25() throws Exception {
        LogUtils.info("Đăng nhập thất bại _ Validate SĐT");
        signIn_page.SignIn();
        excelHelper.setExcelFile("src/test/resources/TestData.xlsx", "phone");
        List<String[]> data = excelHelper.readExcelData(1); // bỏ dòng tiêu đề (bắt đầu từ dòng 1)

        for (String[] row : data) {
            String phonenumber = row[0];
            String password = row[1];
            String expectedError = row[2];

            signIn_page.InputSignIn(phonenumber, password);
            String actualError = String.valueOf(signIn_page.getErrorMessage1Displayed());

            LogUtils.info("Kiểm tra Alert lỗi");
            if (actualError.equals(expectedError)) {
                test.get().pass("PASS: " + phonenumber + " -> " + actualError);
            } else {
                test.get().fail("FAIL: " + phonenumber + " | Expected: " + expectedError + " | Got: " + actualError);
            }
            driver.navigate().refresh(); // reset lại form
        }
    }

    @Test(groups = "Validate_MK", priority = 1)
    public void login_cellphoneS_66() throws Exception {
        LogUtils.info("Đăng nhập thất bại _ Validate Mật khẩu");
        signIn_page.SignIn();
        excelHelper.setExcelFile("src/test/resources/TestData.xlsx", "pass");

        List<String[]> data = excelHelper.readExcelData(1); // bỏ dòng tiêu đề (bắt đầu từ dòng 1)

        for (String[] row : data) {
            String phonenumber = row[0];
            String password = row[1];
            String expectedError = row[2];

            signIn_page.InputSignIn(phonenumber, password);
            String actualError = String.valueOf(signIn_page.getErrorMessage1Displayed());

            LogUtils.info("Kiểm tra Alert lỗi");
            if (actualError.equals(expectedError)) {
                test.get().pass("PASS: " + password + " -> " + actualError);
            } else {
                test.get().fail("FAIL: " + password + " | Expected: " + expectedError + " | Got: " + actualError);
            }
            driver.navigate().refresh(); // reset lại form
        }
    }

    @Test(groups = "Validate_MK", priority = 2)
    public void login_cellphoneS_62() throws Exception {
        LogUtils.info("Mã hoá mật khẩu");
        signIn_page.SignIn();
        signIn_page.InputSignIn2_noclick(Properties_File.getPropValue("phonenumber"),
                Properties_File.getPropValue("password"));

        LogUtils.info("Kiểm tra mật khẩu đã được mã hoá");
        String typeAfterShowPassword = signIn_page.getInputPassword().getAttribute("type");
        if (typeAfterShowPassword.equals("password")) {
            test.get().pass("Mật khẩu được mã hoá dưới dạng dấu chấm (type='password')");
        } else {
            test.get().fail("Mật khẩu không được mã hoá đúng. Kiểu hiển thị: " + typeAfterShowPassword);
        }
    }

    @Test(groups = "Validate_MK", priority = 3)
    public void login_cellphoneS_48() throws Exception {
        signIn_page.SignIn();
        LogUtils.info("Đăng nhập thành công _ Paste SĐT");
        String phonenumber = "0332019523";
        String password = "Huong2004";
        signIn_page.inputPhoneNumber(phonenumber);
        WebElement passwordField = SignIn_Page.getPasswordField();
        passwordField.click();

        LogUtils.info("Kiểm tra Paste dữ liệu vào trường mật khẩu");
        ValidateUIHelper.copyToClipboard(password);
        ValidateUIHelper.pasteWithRobot();
        Thread.sleep(1000);
        signIn_page.ClickSignIn();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("cellphones.com.vn"));

        LogUtils.info("Kiểm tra Paste dữ liệu vào trường mật khẩu");
        String currentURL = driver.getCurrentUrl();
        if (currentURL.contains("cellphones.com.vn") || driver.getPageSource().contains("Đăng nhập thành công")) {
            test.get().pass("Đăng nhập thành công sau khi paste mật khẩu từ clipboard.");
        } else {
            test.get().fail("Đăng nhập thất bại sau khi paste mật khẩu.");
        }
    }

    @Test(groups = "Validate_SĐT", priority = 2)
    public void login_cellphoneS_43() throws Exception {
        signIn_page.SignIn();
        LogUtils.info("Đăng nhập thành công _ Paste Mật khẩu");
        String phonenumber = "0332019523";
        String password = "Huong2004";
        signIn_page.inputPassword(password);
        WebElement phoneNumberField = SignIn_Page.getPhoneNumberField();
        phoneNumberField.click();

        LogUtils.info("Kiểm tra paste dữ liệu vào trường SĐT");
        ValidateUIHelper.copyToClipboard(phonenumber);
        ValidateUIHelper.pasteWithRobot();
        Thread.sleep(1000);
        signIn_page.ClickSignIn();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("cellphones.com.vn"));

        LogUtils.info("Kiểm tra Paste dữ liệu vào trường SĐT");
        String currentURL = driver.getCurrentUrl();
        if (currentURL.contains("https://cellphones.com.vn/") || driver.getPageSource().contains("Đăng nhập thành công")) {
            test.get().pass("PASS: Đăng nhập thành công sau khi paste Số điện thoại");
        } else {
            test.get().fail("FAIL: Đăng nhập thất bại sau khi paste Số điện thoại");
        }
    }

    @Test(groups = "Link", priority = 1)
    public void login_cellphoneS_67() throws Exception {
        signIn_page.SignIn();
        LogUtils.info("Đăng nhập thành công _ Google");
        signIn_page.ClickButtonGoogle();
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("https://accounts.google.com/v3/signin/"), "Không chuyển sang trang đăng nhập Google!");
        LogUtils.info("Đã chuyển đúng sang trang đăng nhập Google OAuth");

    }

    @Test(groups = "Link", priority = 2)
    public void login_cellphoneS_68() throws Exception {
        signIn_page.SignIn();
        LogUtils.info("Đăng nhập thành công _ Zalo");
        signIn_page.ClickButtonZalo();
        String currentUrl = driver.getCurrentUrl();
        boolean isZaloLoginPage = currentUrl.contains("oauth.zaloapp.com")
                || currentUrl.contains("id.zalo.me");
        assertTrue(isZaloLoginPage, "Không chuyển sang trang đăng nhập Zalo!");
        LogUtils.info("Đã chuyển đúng sang trang đăng nhập Zalo OAuth");
    }

    @Test(groups = "Link", priority = 3)
    public void login_cellphoneS_70() throws Exception {
        driver.get("https://cellphones.com.vn/");
        validateUIHelper.waitForPageLoaded();
//        signIn_page.closePopupIfVisible();

        signIn_page.SignIn();
        LogUtils.info("LinkQuen mat khau");
        signIn_page.ClickLinkForgotPassword();
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("https://smember.com.vn/restore-password"), "Không chuyển sang trang quen mat khau!");
        LogUtils.info("Đã chuyển đúng sang trang Quen mat khau");
    }

    @Test(groups = "Link", priority = 4)
    public void login_cellphoneS_71() throws Exception {
        signIn_page.SignIn();
        LogUtils.info("Link Đăng ký");
        signIn_page.ClickLinkRegister();
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("https://smember.com.vn/register"), "Không chuyển sang trang Đăng ký!");
        LogUtils.info("Đã chuyển đúng sang trang Đăng ký");
    }

    @Test(groups = "Link", priority = 5)
    public void login_cellphoneS_72() throws Exception {
        signIn_page.SignIn();
        LogUtils.info("Link CellphoneS");
        signIn_page.ClickLinkCellphoneS();
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("https://cellphones.com.vn/"),
                "Không chuyển sang trang cellphoneS!");

        LogUtils.info("Đã chuyển đúng sang trang cellphoneS");
    }

    @Test(groups = "Link", priority = 6)
    public void login_cellphoneS_73() throws Exception {
        signIn_page.SignIn();
        LogUtils.info("Link DienThoaiVui");
        signIn_page.ClickLinkDienThoaiVui();
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("https://dienthoaivui.com.vn/"), "Không chuyển sang trang dienthoaivui!");

        LogUtils.info("Đã chuyển đúng sang trang dienthoaivui");
    }

    @Test(groups = "Validate_MK", priority = 4)
    public void login_cellphoneS_74() throws Exception {
        signIn_page.SignIn();
        LogUtils.info("Con mắt ẩn và hiện Mật khẩu");
        signIn_page.InputSignIn2_noclick(Properties_File.getPropValue("phonenumber"), Properties_File.getPropValue("password"));

        LogUtils.info("Kiểm tra click hiện mật khẩu");
        if (signIn_page.isButtonShowPasswordClickable()) {
            test.get().pass("Hiện mật khẩu cho phép click");

            signIn_page.ClickButtonShowPassword();
            Thread.sleep(3000);

            String typeAfterShowPassword = signIn_page.getInputPassword().getAttribute("type");
            assertEquals(typeAfterShowPassword, "text", "Mật khẩu không hiển thị khi click con mắt lần 1!");
        }else {
            test.get().fail("Hiện mật khẩu không cho phép click");
        }

        LogUtils.info("Kiểm tra click ẩn mật khẩu");
        if (signIn_page.isButtonHidePasswordClickable()) {
            test.get().pass("Ẩn mật khẩu cho phép click");

            signIn_page.ClickButtonHidePassword();
            Thread.sleep(3000);

            String typeAfterShowPassword2 = signIn_page.getInputPassword().getAttribute("type");
            assertEquals(typeAfterShowPassword2, "password", "Mật khẩu không hiển thị khi click con mắt lần 2!");
        }else {
            test.get().fail("Ẩn mật khẩu không cho phép click");
        }
    }

    @Test(groups = "Link", priority = 7)
    public void login_cellphoneS_75() throws Exception {
        signIn_page.SignIn();
        LogUtils.info("Link Chính sách ưu đãi");
        signIn_page.ClickLinkChinhSachUuDai();
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("https://cellphones.com.vn/uu-dai-smember"), "Không chuyển sang trang chính sách ưu đãi!");

        LogUtils.info("Đã chuyển đúng sang trang chính sách ưu đãi");
    }
}

