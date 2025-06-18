package com.cellphoneS.bases;

import com.helpers.RecordVideo;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
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
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        // Khắc phục lỗi "user-data-dir is already in use"
        String uniqueProfile = System.getProperty("java.io.tmpdir") + "/chrome-profile-" + System.currentTimeMillis();
        options.addArguments("--user-data-dir=" + uniqueProfile);

        driver = new ChromeDriver(options);
        setupTimeouts(driver);
        return driver;
    }

    private WebDriver initFirefoxDriver() {
        System.out.println("Launching Firefox browser...");
        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-headless"); // Chạy headless mode an toàn
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");

        driver = new FirefoxDriver(options);
        setupTimeouts(driver);
        return driver;
    }

    private WebDriver initEdgeDriver() {
        System.out.println("Launching Edge browser...");
        WebDriverManager.edgedriver().setup();

        EdgeOptions options = new EdgeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        // Giống Chrome, cần user-data-dir riêng để tránh lỗi session
        String uniqueProfile = System.getProperty("java.io.tmpdir") + "/edge-profile-" + System.currentTimeMillis();
        options.addArguments("--user-data-dir=" + uniqueProfile);

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
            try {
                RecordVideo.stopRecord();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
