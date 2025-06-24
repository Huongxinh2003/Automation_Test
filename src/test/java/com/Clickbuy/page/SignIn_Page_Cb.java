package com.Clickbuy.page;

import com.helpers.ValidateUIHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class SignIn_Page_Cb extends ValidateUIHelper {
    private static WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public By popupModal = By.xpath("//span[@class='modal__button'][contains(text(),'Miền Bắc')]");
    public By ButtonSignIn = By.xpath("//a[@id='loginBtn']");
    public By popupSignIn = By.xpath("//div[@id='popup-modal']");
    public By InputPhoneNumber = By.xpath("//input[@placeholder='Nhập số điện thoại/email *']");
    public By InputPassword = By.xpath("//form[@id='loginForm']//input[@placeholder='Nhập mật khẩu *']");
    public By SuccessToast = By.xpath("//div[@class='jq-toast-single jq-has-icon jq-icon-success']");
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
    }

    public void ClickButtonSignIn() {
        clickElement(popupModal);
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

    public void SignInNotClick(String phoneNumber, String password) {
        WebElement phonenumber = wait.until(ExpectedConditions.visibilityOfElementLocated(InputPhoneNumber));
        phonenumber.clear();
        WebElement password1 = wait.until(ExpectedConditions.visibilityOfElementLocated(InputPassword));
        password1.clear();
        sendKeys(InputPhoneNumber, phoneNumber);
        sendKeys(InputPassword, password);
    }

    public String getSuccessToast() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(SuccessToast));
        return getText(SuccessToast);
    }

    public void verifySuccessToast() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(SuccessToast));
        String expectedText = "\n Thành công \n Đăng nhập thành công";
        String actualText = getText(SuccessToast);
        Assert.assertEquals(actualText, expectedText, "Success toast hiển thị không hợp lệ");
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
        String expectedTitle = "Clickbuy - Hệ thống bán lẻ";
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
        clickElement(LinkSignUp);
        return false;
    }

    public boolean isLoginWithZaloDisplayed() {
        return isElementDisplayed(LoginWithZalo);
    }
    public boolean ClickLoginWithZalo() {
        clickElement(LinkSignUp);
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
