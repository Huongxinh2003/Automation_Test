package com.Clickbuy.page;

import com.helpers.ValidateUIHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class SignIn_Page_Cb extends ValidateUIHelper {
    private static WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private ValidateUIHelper validateUIHelper;

    public By popupModal = By.xpath("//div[@id='popup-modal']");
    public By ClickpopupModal = By.xpath("//span[@class='modal__button'][contains(text(),'Miền Bắc')]");
    public By ButtonSignIn = By.xpath("//a[@id='loginBtn']");
    public By popupSignIn = By.xpath("//div[@id='popup-modal']");
    public By InputPhoneNumber = By.xpath("//input[@placeholder='Nhập số điện thoại/email *']");
    public By InputPassword = By.xpath("//form[@id='loginForm']//input[@placeholder='Nhập mật khẩu *']");
    public By SuccessToast= By.xpath("//div[@class='jq-toast-single jq-has-icon jq-icon-success']");
    public By FailToast = By.xpath("//div[@class='jq-toast-single jq-has-icon jq-icon-error']");
    public By BtnSignIn = By.xpath("//input[@id='loginHandler']");
    public By Logo = By.xpath("//form[@id='loginForm']//img[@alt='logo clickbuy']");
    public By TitleSignIn = By.xpath("//form[@id='loginForm']//div[1]");
    public By LoginWithGoogle = By.xpath("//form[@id='loginForm']//a[@class='social-btn btn-google'][normalize-space()='Google']");
    public By LoginWithZalo = By.xpath("//form[@id='loginForm']//a[@class='social-btn btn-facebook'][normalize-space()='Zalo']");
    public By TextSuggestSignUp = By.xpath("//form[@id='loginForm']//div[2]");
    public By LinkSignUp = By.xpath("//a[@id='switchToRegister']");

    public SignIn_Page_Cb(WebDriver driver) {
        super(driver);
        SignIn_Page_Cb.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        validateUIHelper = new ValidateUIHelper(driver);
    }

    public void ClickButtonSignIn1() {
        validateUIHelper.waitForPageLoaded();
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement clickpopup = shortWait.until(ExpectedConditions.elementToBeClickable(ClickpopupModal));
            clickpopup.click();

            wait.until(ExpectedConditions.invisibilityOfElementLocated(popupModal));
            System.out.println("Popup chọn vùng miền đã được đóng.");
        } catch (TimeoutException e) {
            System.out.println("Không có popup xuất hiện.");
        }

        // Chờ cho trang ổn định
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete"));

        try {
            WebElement loginBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(ButtonSignIn));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", loginBtn);
            wait.until(ExpectedConditions.elementToBeClickable(loginBtn));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginBtn); // dùng JS click
            System.out.println("Đã click vào nút Đăng nhập.");
        } catch (Exception e) {
            System.out.println("Không thể click nút Đăng nhập: " + e.getMessage());
        }
    }


    public void ClickButtonSignIn() {
        clickElement(ClickpopupModal);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        clickElement(ButtonSignIn);
    }

    public boolean isPopupSignInDisplayed() {
        return isElementDisplayed(popupSignIn);
    }

    public Homepage_page_Cb InputSignIn(String phoneNumber, String password) {
        sendKeys(InputPhoneNumber, phoneNumber);
        sendKeys(InputPassword, password);
        clickElement(BtnSignIn);
        return new Homepage_page_Cb(driver);
    }

    public void InputSignIn1(String phoneNumber, String password) {
        sendKeys(InputPhoneNumber, phoneNumber);
        sendKeys(InputPassword, password);
    }

    public void SignInNotClick(String phoneNumber, String password) {
        WebElement phonenumber = wait.until(ExpectedConditions.visibilityOfElementLocated(InputPhoneNumber));
        phonenumber.clear();
        WebElement password1 = wait.until(ExpectedConditions.visibilityOfElementLocated(InputPassword));
        password1.clear();
        sendKeys(InputPhoneNumber, phoneNumber);
        sendKeys(InputPassword, password);
    }
    public String getFailToast() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement toastElement = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.className("jq-toast-single")));
        // Cách 1: Dùng JavaScript để lấy đúng phần text sau dấu "×" và "Lỗi"
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String errorMessage = (String) js.executeScript(
                "return arguments[0].childNodes[3].nodeValue.trim();", toastElement);
        return errorMessage;
    }

    public String getSuccessToast() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(SuccessToast));
        return getText(SuccessToast);
    }

    public void verifySuccessToast() {
        WebElement toastElement = wait.until(ExpectedConditions.visibilityOfElementLocated(SuccessToast));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String errorMessage = (String) js.executeScript(
                "return arguments[0].childNodes[3].nodeValue.trim();", toastElement);
        System.out.println("Actual message: " + errorMessage);
        String expectedMessage = "Đăng nhập thành công!.";
        Assert.assertEquals(expectedMessage, errorMessage, "Success toast hiển thị không hợp lệ");
    }

    public String getInputPhoneNumber() {
        return getText(InputPhoneNumber);
    }

    public String getInputPassword() {
        return getText(InputPassword);
    }

    public static String getTitle() {
        return driver.getTitle();
    }

    public void verifyCartPageTitle() {
        String expectedTitle = "Clickbuy.com.vn - Hệ thống bán lẻ điện thoại, máy tính bảng, " +
                "laptop, phụ kiện chính hãng";
        Assert.assertEquals(getTitle(), expectedTitle,"Title không hợp lệ");
    }

    public boolean isLogoDisplayed() {
        return isElementDisplayed(Logo);
    }

    public boolean isTitleSignInDisplayed() {
        return isElementDisplayed(TitleSignIn);
    }

    public boolean isLoginWithGoogleDisplayed() {
        return isElementDisplayed(LoginWithGoogle);
    }
    public boolean ClickLoginWithGoogle() {
        clickElement(LoginWithGoogle);
        return false;
    }

    public boolean isLoginWithZaloDisplayed() {
        return isElementDisplayed(LoginWithZalo);
    }
    public boolean ClickLoginWithZalo() {
        clickElement(LoginWithZalo);
        return false;
    }

    public boolean isTextSuggestSignUpDisplayed() {
        return isElementDisplayed(TextSuggestSignUp);
    }

    public boolean isLinkSignUpDisplayed() {
        return isElementDisplayed(LinkSignUp);
    }

    public void ClickLinkSignUp() {
        clickElement(LinkSignUp);
    }

}
