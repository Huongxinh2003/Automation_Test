package com.cellphoneS.testcases;

import com.cellphoneS.bases.BaseSetup;
import com.cellphoneS.bases.SignIn_Helpers;
import com.cellphoneS.pages.Homepage_page;
import com.cellphoneS.pages.Search_Page;
import com.cellphoneS.pages.SignIn_Page;
import com.helpers.CaptureHelpers;
import com.helpers.RecordVideo;
import com.helpers.ValidateUIHelper;
import com.ultilities.ExcelUtils;
import com.ultilities.LogUtils;
import com.ultilities.Properties_File;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Search_Test extends BaseSetup {
    private static final Logger log = LoggerFactory.getLogger(SignIn_Test.class);
    private WebDriver driver;
    public Search_Page search_page;
    public WebDriverWait wait;
    public ExcelUtils excelHelper;
    public ValidateUIHelper validateUIHelper;
    public SignIn_Helpers signIn_helpers;
    public Homepage_page homepage_page;


    @BeforeClass(groups = "Function")
    public void setUp() throws Exception {
        //gọi hàm khởi tạo properties
        Properties_File.setPropertiesFile();
        // Gọi lại hàm startRecord
        try {
            RecordVideo.startRecord("RecordVideo");
        }catch (Exception e){
            e.printStackTrace();
        }
        // Lấy driver từ class cha BaseSetup
        driver = setupDriver(Properties_File.getPropValue("browser"));
        search_page = new Search_Page(driver);
        excelHelper = new ExcelUtils();
        validateUIHelper = new ValidateUIHelper(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        signIn_helpers = new SignIn_Helpers(driver);
        homepage_page = signIn_helpers.SignIn(driver);
        search_page = homepage_page.openSearchPage();
        log.info("Đã mở trang tìm kiếm");

    }

    // Nó sẽ thực thi sau mỗi lần thực thi testcase (@Test)
    @AfterMethod
    public void takeScreenshot(ITestResult result) throws InterruptedException {
        Thread.sleep(1000);
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                CaptureHelpers.captureScreenshot(driver, result.getName());
            } catch (Exception e) {
                System.out.println("Exception while taking screenshot " + e.getMessage());
            }
        }
    }

    @Test
    public void Search_Success() {
        LogUtils.info("Tìm kiếm với Iphone");
        search_page.inputSearch("Iphone");
    }

}
