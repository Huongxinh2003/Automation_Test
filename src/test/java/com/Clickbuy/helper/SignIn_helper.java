package com.Clickbuy.helper;

import com.Clickbuy.page.Homepage_page_Cb;
import com.Clickbuy.page.Search_Page_Cb;
import com.Clickbuy.page.SignIn_Page_Cb;
import com.base.BaseSetup;
import com.helpers.ValidateUIHelper;
import com.ultilities.Properties_File;
import com.ultilities.logs.LogUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;

import java.time.Duration;

public class SignIn_helper extends BaseSetup {
    private WebDriver driver;
    public WebDriverWait wait;
    public ValidateUIHelper validateUIHelper;
    public SignIn_Page_Cb signIn_page_cb;
    public Homepage_page_Cb homepage_page_cb;
    public Search_Page_Cb search_page_cb;


    public SignIn_helper(WebDriver driver) {
        this.driver = driver;
        this.validateUIHelper = new ValidateUIHelper(driver);
        this.signIn_page_cb = new SignIn_Page_Cb(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @BeforeClass
    public void setupDriver() throws Exception {
        Properties_File.setPropertiesFile();
        boolean headless = Boolean.parseBoolean(Properties_File.getPropValue("headless"));
        driver = setupDriver(Properties_File.getPropValue("browser"), headless);

        LogUtils.info("Khởi tạo driver thành công");
    }

    public Homepage_page_Cb SignIn(WebDriver driver) {
        driver.get("https://clickbuy.com.vn/");
        test.get().info("CLick button để mở popup đăng nhập");
        signIn_page_cb.ClickButtonSignIn();

        test.get().info("Nhâp thông tin đăng nhập");
        homepage_page_cb = signIn_page_cb.InputSignIn(Properties_File.getPropValue("phonenumber2"), Properties_File.getPropValue("password2"));
        return new Homepage_page_Cb(driver);
    }
}
