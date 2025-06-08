package com.cellphoneS.bases;

import com.helpers.RecordVideo;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterClass;

import java.time.Duration;

public class BaseSetup {

    private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriver setupDriver(String browserType) throws Exception {
        switch (browserType.trim().toLowerCase()) {
            case "chrome":
                driver = initChromeDriver();
                break;
            case "firefox":
                driver = initFirefoxDriver();
                break;
            case "edge":
                driver = initEdgeDriver();
                break;
            default:
                System.out.println("Browser: " + browserType + " is invalid, Launching Chrome as browser of choice...");
                driver = initChromeDriver();
        }
        return driver;
    }

    private WebDriver initChromeDriver() throws Exception {
        System.out.println("Launching Chrome browser...");
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // ✅ Dành cho môi trường CI/CD không có giao diện
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");

        // ✅ Tránh lỗi "user-data-dir already in use"
        String uniqueUserDataDir = "/tmp/chrome-profile-" + System.currentTimeMillis();
        options.addArguments("--user-data-dir=" + uniqueUserDataDir);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        return driver;
    }

    private WebDriver initEdgeDriver() {
        System.out.println("Launching Edge browser...");
        WebDriverManager.edgedriver().setup();

        EdgeOptions options = new EdgeOptions();

        // Áp dụng giống như Chrome (Edge dựa trên Chromium)
        String userDataDir = System.getProperty("java.io.tmpdir") + "/edge-profile-" + System.currentTimeMillis();
        options.addArguments("--user-data-dir=" + userDataDir);
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        // options.addArguments("--headless=new"); // Nếu bạn muốn Edge headless

        WebDriver driver = new EdgeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        return driver;
    }


    private WebDriver initFirefoxDriver() {
        System.out.println("Launching Firefox browser...");
        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions options = new FirefoxOptions();

        // Dành cho môi trường CI/CD
        options.addArguments("--headless"); // Bắt buộc nếu không có GUI
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        WebDriver driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        return driver;
    }


    @AfterClass
    public void tearDown() throws Exception {
        Thread.sleep(3000);
        if (driver != null) {
            driver.quit();
            System.out.println("Đã đóng trình duyệt.");
            try {
                RecordVideo.stopRecord();
            } catch (Exception e) {
                System.err.println("Lỗi khi dừng quay video: " + e.getMessage());
            }
        }
    }
}