package com.Clickbuy.test;

import com.Clickbuy.page.Homepage_page_Cb;
import com.base.BaseSetup;
import com.Clickbuy.page.SignIn_Page_Cb;
import com.helpers.ValidateUIHelper;
import com.ultilities.ExcelUtils;
import com.ultilities.Properties_File;
import com.ultilities.listeners.ReportListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

@Listeners(ReportListener.class)
public class SignIn_Test_Cb extends BaseSetup {
    SignIn_Page_Cb signIn_page_cb;
    ExcelUtils excelHelper;
    WebDriver driver;
    ValidateUIHelper validateUIHelper;
    WebDriverWait wait;
    Homepage_page_Cb homepage_page;
    ExcelUtils ExcelHelper;

    @BeforeClass(groups = {"SignIn_Success", "Function", "Validate_SĐT", "Validate_MK", "UI_Test"})
    public void setUp() throws Exception {
        //gọi hàm khởi tạo properties
        Properties_File.setPropertiesFile();
        boolean headless = Boolean.parseBoolean(Properties_File.getPropValue("headless"));
        driver = setupDriver(Properties_File.getPropValue("browser"), headless);
        signIn_page_cb = new SignIn_Page_Cb(driver);
        ExcelHelper = new ExcelUtils();
        validateUIHelper = new ValidateUIHelper(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        homepage_page = new Homepage_page_Cb(driver);
    }

    @BeforeMethod (groups = {"SignIn_Success", "Function", "Validate_SĐT", "Validate_MK", "UI_Test"})
    public void beforeMethod() {
        driver.get("https://clickbuy.com.vn/");
        test.get().info("CLick button để mở popup đăng nhập");
    }

    @Test(groups = "SignIn_Success")
    public void SignIn() {
        signIn_page_cb.ClickButtonSignIn1();
        test.get().info("Nhâp thông tin đăng nhập");
        homepage_page = signIn_page_cb.InputSignIn(Properties_File.getPropValue("phonenumber2"), Properties_File.getPropValue("password2"));
    }

    @Test (groups = "SignIn_Success")
    public void verifySignInSuccess() {
        signIn_page_cb.ClickButtonSignIn1();
        test.get().info("Nhâp thông tin đăng nhập");
        homepage_page = signIn_page_cb.InputSignIn(Properties_File.getPropValue("phonenumber2"), Properties_File.getPropValue("password2"));

        test.get().info("Kiểm tra hiển thị thông báo khi đăng nhập thành công");
        signIn_page_cb.verifySuccessToast();
    }

    @Test (groups = "Function", priority = 1, description = "Kiểm tra thông báo khi bỏ trống trường password")
    public void enterNoPassword() throws Exception {
        signIn_page_cb.ClickButtonSignIn1();
        test.get().info("Nhâp thông tin đăng nhập");
        ExcelHelper.setExcelFile("src/test/resources/SignIn_clickbuy.xlsx", "signin_SC");
        test.get().info("Bỏ trống trường password");
        signIn_page_cb.InputSignIn(ExcelHelper.getCellData("phonenumber", 1), ExcelHelper.getCellData("password", 1));

        test.get().info("Kiểm tra hiển thị thông báo lỗi");
        String expectedError = "× \n Lỗi \n Mật khẩu không được bỏ trống";
        String actualError = signIn_page_cb.getFailToast();
        Assert.assertEquals(actualError, expectedError, "Thông báo lỗi hiển thị không đúng");
    }

    @Test (groups = "Function", priority = 1, description = "Kiểm tra thông báo khi bỏ trống trường phoneNumber")
    public void enterNoPhoneNumber() throws Exception {
        signIn_page_cb.ClickButtonSignIn1();
        test.get().info("Nhâp thông tin đăng nhập");
        ExcelHelper.setExcelFile("src/test/resources/SignIn_clickbuy.xlsx", "signin_SC");
        test.get().info("Bỏ trống trường phoneNumber");
        signIn_page_cb.InputSignIn(ExcelHelper.getCellData("phonenumber", 2), ExcelHelper.getCellData("password", 2));
        test.get().info("Kiểm tra hiển thị thông báo lỗi");
        String expectedError = "× \n Lỗi \n Số điện thoại không được bỏ trống";
        String actualError = signIn_page_cb.getFailToast();
        Assert.assertEquals(actualError, expectedError, "Thông báo lỗi hiển thị không đúng");
    }

    @Test (groups = "Function", priority = 3, description = "Kiểm tra thông báo khi bỏ trống cả trường phoneNumber và password")
    public void enterNoData() throws Exception {
        signIn_page_cb.ClickButtonSignIn1();
        test.get().info("Nhâp thông tin đăng nhập");
        ExcelHelper.setExcelFile("src/test/resources/SignIn_clickbuy.xlsx", "signin_SC");
        test.get().info("Bỏ trống trường phoneNumber và password");
        signIn_page_cb.InputSignIn(ExcelHelper.getCellData("phonenumber", 3), ExcelHelper.getCellData("password", 3));
        test.get().info("Kiểm tra hiển thị thông báo lỗi");
        String expectedError = "× \n Lỗi \n Bạn chưa nhập thông tin đăng nhập";
        String actualError = signIn_page_cb.getFailToast();
        Assert.assertEquals(actualError, expectedError, "Thông báo lỗi hiển thị không đúng");
    }

    @Test (groups = "Function", priority = 4, description = "Kiểm tra thông báo khi nhập thông tin không tồn tại")
    public void enterDataNoExists() throws Exception {
        signIn_page_cb.ClickButtonSignIn1();
        test.get().info("Nhâp thông tin đăng nhập");
        ExcelHelper.setExcelFile("src/test/resources/SignIn_clickbuy.xlsx", "signin_SC");
        test.get().info("Nhập thông tin không tồn tại");
        signIn_page_cb.InputSignIn(ExcelHelper.getCellData("phonenumber", 4), ExcelHelper.getCellData("password", 4));
        test.get().info("Kiểm tra hiển thị thông báo lỗi");
        String expectedError = "× \n Lỗi \n Số điện thoại hoặc mật khẩu không đúng";
        String actualError = signIn_page_cb.getFailToast();
        Assert.assertEquals(actualError, expectedError, "Thông báo lỗi hiển thị không đúng");
    }

    @Test(groups = "Validate_SĐT", priority = 1, description = "Kiểm tra nhập số điện thoại hợp lệ")
    public void verifySignInFailPhoneNumber() throws Exception {
        signIn_page_cb.ClickButtonSignIn1();
        ExcelHelper.setExcelFile("src/test/resources/SignIn_clickbuy.xlsx", "phonenumber");
        List<String[]> data = ExcelHelper.readExcelData(1); // bỏ dòng tiêu đề (bắt đầu từ dòng 1)

        for (String[] row : data) {
            String phonenumber = row[0];
            String password = row[1];
            String expectedError = row[2];

            signIn_page_cb.InputSignIn(phonenumber, password);
            String actualError = String.valueOf(signIn_page_cb.getFailToast());

            if (actualError.equals(expectedError)) {
                test.get().pass("PASS: " + phonenumber + " -> " + actualError);
            } else {
                test.get().fail("FAIL: " + phonenumber + " | Expected: " + expectedError + " | Got: " + actualError);
            }
            driver.navigate().refresh();
            signIn_page_cb.ClickButtonSignIn1();
        }
    }

    @Test(groups = "Validate_MK", priority = 2, description = "Kiểm tra nhập mật khẩu hợp lệ")
    public void verifySignInFailPassword() throws Exception {
        signIn_page_cb.ClickButtonSignIn1();
        ExcelHelper.setExcelFile("src/test/resources/SignIn_clickbuy.xlsx", "password");
        List<String[]> data = ExcelHelper.readExcelData(1); // bỏ dòng tiêu đề (bắt đầu từ dòng 1)

        for (String[] row : data) {
            String phonenumber = row[0];
            String password = row[1];
            String expectedError = row[2];

            signIn_page_cb.InputSignIn(phonenumber, password);
            String actualError = String.valueOf(signIn_page_cb.getFailToast());

            if (actualError.equals(expectedError)) {
                test.get().pass("PASS: " + password + " -> " + actualError);
            } else {
                test.get().fail("FAIL: " + password + " | Expected: " + expectedError + " | Got: " + actualError);
            }
            driver.navigate().refresh();
            signIn_page_cb.ClickButtonSignIn1();
        }
    }

    @Test (groups = "UI_Test", description = "Kiểm tra UI của popup đăng nhập")
    public void verifyPopupSignIn() {
        signIn_page_cb.ClickButtonSignIn1();
        test.get().info("Kiểm tra tiêu đề trang");
        signIn_page_cb.verifyCartPageTitle();

        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        test.get().info("Kiểm tra popup đăng nhập hiển thị");
        signIn_page_cb.isPopupSignInDisplayed();
        Assert.assertTrue(signIn_page_cb.isPopupSignInDisplayed(), "Popup đăng nhập không hiển thị");

        test.get().info("Kiểm tra hiển thị logo ClickBuy");
        Assert.assertTrue(signIn_page_cb.isLogoDisplayed(), "Không hiển thị logo ClickBuy");

        test.get().info("Kiểm tra hiển thị Title đăng nhập");
        Assert.assertTrue(signIn_page_cb.isTitleSignInDisplayed(), "Không hiển thị Title đăng nhập");

        test.get().info("Nhâp thông tin đăng nhập");
        homepage_page = signIn_page_cb.InputSignIn(Properties_File.getPropValue("phonenumber2"), Properties_File.getPropValue("password2"));
        String expectedPhoneNumber = "0332019523";
        String expectedPassword = "Huong2003";
        if(signIn_page_cb.getInputPhoneNumber().equals(expectedPhoneNumber) && signIn_page_cb.getInputPassword().equals(expectedPassword)) {
            test.get().info("Thông tin đăng nhập đúng");
        }else {
            test.get().info("Nhâp thông tin đăng nhập");
            signIn_page_cb.SignInNotClick(expectedPhoneNumber, expectedPassword);
        }

        test.get().info("Kiểm tra hiển thị của các liên kết đăng nhập");
        if(signIn_page_cb.isLoginWithGoogleDisplayed()) {
            test.get().pass("Hiển thị liên kết đăng nhập với google");
        }else {
            test.get().fail("Không hiển thị liên kết đăng nhập với google");
        }

        if(signIn_page_cb.isLoginWithZaloDisplayed()) {
            test.get().pass("Hiển thị liên kết đăng nhập với zalo");
        }else {
            test.get().fail("Không hiển thị liên kết đăng nhập với zalo");
        }

        test.get().info("Kiểm tra hiển thị text gợi ý đăng ký");
        signIn_page_cb.isTextSuggestSignUpDisplayed();
        Assert.assertTrue(signIn_page_cb.isTextSuggestSignUpDisplayed(), "Text gợi ý đăng ký không hiển thị");

        test.get().info("Kiểm tra link Đăng ký");
        if (signIn_page_cb.isLinkSignUpDisplayed()) {
            signIn_page_cb.ClickLinkSignUp();
        }else {
            test.get().fail("Link Đăng ký không hiển thị");
        }


    }

    @Test (groups = "Function", priority = 5, description = "Kiểm tra click đăng nhập bằng Google và Zalo")
    public void LinkSignInWithSocial() {
        signIn_page_cb.ClickButtonSignIn1();

        test.get().info("Kiểm tra click đăng nhập bằng Google");
        if (signIn_page_cb.ClickLoginWithGoogle()) {
            test.get().pass("Click đăng nhập bằng Google hợp lệ");
        } else {
            test.get().fail("Click đăng nhập bằng Google không hợp lệ");
        }

        test.get().info("Kiểm tra URL liên kết Google");
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.equals("https://accounts.google.com/v3/signin/")) {
            test.get().pass("URL liên kết Google hợp lệ");
        } else {
            test.get().fail("URL liên kết Google không hợp lệ");
        }

        test.get().info("Kiểm tra click đăng nhập bằng Zalo");
        if (signIn_page_cb.ClickLoginWithZalo()) {
            test.get().pass("Click đăng nhập bằng Zalo hợp lệ");
        } else {
            test.get().fail("Click đăng nhập bằng Zalo không hợp lệ");
        }
        test.get().info("Kiểm tra URL liên kết Zalo");
        String currentUrl2 = driver.getCurrentUrl();
        if (currentUrl2.equals("https://oauth.zaloapp.com/oauth2/authorize")) {
            test.get().pass("URL liên kết Zalo hợp lệ");
        } else {
            test.get().fail("URL liên kết Zalo không hợp lệ");
        }
    }
}
