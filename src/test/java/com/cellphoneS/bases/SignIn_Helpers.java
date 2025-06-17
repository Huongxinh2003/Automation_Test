package com.cellphoneS.bases;

import com.cellphoneS.pages.Homepage_page;
import com.cellphoneS.pages.Product_Detail_Page;
import com.cellphoneS.pages.Search_Page;
import com.cellphoneS.pages.SignIn_Page;
import com.helpers.ValidateUIHelper;
import com.ultilities.LogUtils;
import com.ultilities.Properties_File;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;

import java.time.Duration;

public class SignIn_Helpers extends BaseSetup {
    private WebDriver driver;
    public WebDriverWait wait;
    public ValidateUIHelper validateUIHelper;
    public SignIn_Page signIn_page;
    public Homepage_page homepage_page;
    public Search_Page search_Page;

    public SignIn_Helpers(WebDriver driver) {
        this.driver = driver;
        this.validateUIHelper = new ValidateUIHelper(driver);
        this.signIn_page = new SignIn_Page(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @BeforeClass
    public void setupDriver() throws Exception {
        Properties_File.setPropertiesFile();
        driver = setupDriver(Properties_File.getPropValue("browser"));
        LogUtils.info("Khởi tạo driver thành công");
    }

    public Homepage_page SignIn(WebDriver driver) {
        driver.get("https://cellphones.com.vn/");
        validateUIHelper.waitForPageLoaded();
//        signIn_page.closePopupIfVisible();
        signIn_page.SignIn();
        LogUtils.info("Bắt đầu test case");
        homepage_page = signIn_page.InputSignIn(Properties_File.getPropValue("phonenumber"), Properties_File.getPropValue("password"));
        LogUtils.info("Đăng nhập thành công");
        search_Page = homepage_page.openSearchPage();
        return new Homepage_page(driver);
        
    }
}
