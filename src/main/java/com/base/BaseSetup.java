package com.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.ultilities.extentreports.ExtentManager;
import com.ultilities.extentreports.ExtentTestManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BaseSetup {

    @Getter
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    protected static ExtentReports extent;
    protected static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    // --- Setup WebDriver ---
    public WebDriver setupDriver(String browserType, boolean headless) throws Exception {
        WebDriver wd;
        switch (browserType.trim().toLowerCase()) {
            case "chrome":
                wd = initChromeDriver(headless);
                break;
            case "firefox":
                wd = initFirefoxDriver(headless);
                break;
            case "edge":
                wd = initEdgeDriver(headless);
                break;
            default:
                System.out.println("Browser: " + browserType + " is invalid, Launching Chrome as default...");
                wd = initChromeDriver(headless);
        }
        driver.set(wd);
        return wd;
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
            String uniqueProfile = System.getProperty("java.io.tmpdir") + "/chrome-profile-" + System.currentTimeMillis();
            options.addArguments("--user-data-dir=" + uniqueProfile);
        }
        options.addArguments("--window-size=1920,1080");

        WebDriver wd = new ChromeDriver(options);
        setupTimeouts(wd);
        return wd;
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

        WebDriver wd = new FirefoxDriver(options);
        setupTimeouts(wd);
        return wd;
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

        WebDriver wd = new EdgeDriver(options);
        setupTimeouts(wd);
        return wd;
    }

    private void setupTimeouts(WebDriver wd) {
        wd.manage().window().maximize();
        wd.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        wd.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    public void applyZoom(WebDriver driver, int percent) {
        String script = String.format("document.body.style.zoom='%d%%'", percent);
        ((JavascriptExecutor) driver).executeScript(script);
    }


    public static WebDriver getDriver() {
        return driver.get();
    }

    // --- ExtentReports Lifecycle ---

    @BeforeSuite(alwaysRun = true)
    public void initExtentReports() {
        extent = ExtentManager.getExtentReports();
    }

    @BeforeMethod(alwaysRun = true)
    public void startTest(Method method) {
        ExtentTest extentTest = extent.createTest(method.getName());
        test.set(extentTest);
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownReport() {
        extent.flush();
    }

    // --- Tear down WebDriver ---
    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        Thread.sleep(3000);
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
            System.out.println("Đã đóng trình duyệt.");
        }
    }
}
