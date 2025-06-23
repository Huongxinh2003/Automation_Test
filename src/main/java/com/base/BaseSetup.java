package com.base;

import com.helpers.RecordVideo;
import com.ultilities.Properties_File;
import com.ultilities.logs.LogUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.time.Duration;

public class BaseSetup {

    @Getter
    private static WebDriver driver;

    // Thêm tham số headless để tùy chọn
    public WebDriver setupDriver(String browserType, boolean headless) throws Exception {
        switch (browserType.trim().toLowerCase()) {
            case "chrome":
                driver = initChromeDriver(headless);
                break;
            case "firefox":
                driver = initFirefoxDriver(headless);
                break;
            case "edge":
                driver = initEdgeDriver(headless);
                break;
            default:
                System.out.println("Browser: " + browserType + " is invalid, Launching Chrome as browser of choice...");
                driver = initChromeDriver(headless);
        }
        return driver;
    }

    private WebDriver initChromeDriver(boolean headless) throws Exception {
        System.out.println("Launching Chrome browser...");
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        }

        options.addArguments("--window-size=1920,1080");

        // Để tránh lỗi user-data-dir khi chạy headless nhiều lần
        if (headless) {
            String uniqueProfile = System.getProperty("java.io.tmpdir") + "/chrome-profile-" + System.currentTimeMillis();
            options.addArguments("--user-data-dir=" + uniqueProfile);
        }

        driver = new ChromeDriver(options);
        setupTimeouts(driver);
        return driver;
    }

    private WebDriver initFirefoxDriver(boolean headless) {
        System.out.println("Launching Firefox browser...");
        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions options = new FirefoxOptions();

        if (headless) {
            options.addArguments("-headless");
            options.addArguments("--width=1920");
            options.addArguments("--height=1080");
        }

        driver = new FirefoxDriver(options);
        setupTimeouts(driver);
        return driver;
    }

    private WebDriver initEdgeDriver(boolean headless) {
        System.out.println("Launching Edge browser...");
        WebDriverManager.edgedriver().setup();

        EdgeOptions options = new EdgeOptions();

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");

            String uniqueProfile = System.getProperty("java.io.tmpdir") + "/edge-profile-" + System.currentTimeMillis();
            options.addArguments("--user-data-dir=" + uniqueProfile);
        }

        options.addArguments("--window-size=1920,1080");

        driver = new EdgeDriver(options);
        setupTimeouts(driver);
        return driver;
    }

    private void setupTimeouts(WebDriver driver) {
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @AfterClass
    public void tearDown() throws Exception {
        Thread.sleep(3000);
        if (driver != null) {
            driver.quit();
            System.out.println("Đã đóng trình duyệt.");
        }
    }
}