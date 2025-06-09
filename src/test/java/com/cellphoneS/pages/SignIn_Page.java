package com.cellphoneS.pages;

import com.helpers.ValidateUIHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class  SignIn_Page extends ValidateUIHelper {
    private static WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    // Định nghĩa các locator
    private final By popupCloseBtn = By.xpath("//button[@class='modal-close is-large']");
    private final By loginButton = By.xpath("//div[@class='header-item about-5 about-smember cta-smember button__login']");
    private final By loginButton2 = By.xpath("//div[@class='group-login-btn']//div[2]");
    private static final By inputPhoneNumber = By.xpath("//input[@placeholder='Nhập số điện thoại của bạn']");
    private static final By inputPassword = By.xpath("//input[@placeholder='Nhập mật khẩu của bạn']");
    private static final By btnSignIn = By.xpath("//button[contains(text(),'Đăng nhập')]");
    public final By linkRegister = By.xpath("//a[contains(text(),'Đăng ký ngay')]");
    public final By linkCellphoneS = By.xpath("//a[normalize-space()='cellphones.com.vn']");
    public final By linkDienThoaiVui = By.xpath("//a[normalize-space()='dienthoaivui.com.vn']");
    public final By linkChinhSachUuDai = By.xpath("//span[@class='text-primary-500 font-medium']");
    public final By buttonShowPassword = By.xpath("//button[contains(@aria-label,'Show password')]");
    public final By buttonHidePassword = By.xpath("//button[@aria-label='Hide password']//*[name()='svg']");
    public final By ButtonGoogle = By.xpath("//div[@class='w-full flex gap-medium tablet:gap-large justify-between items-center']//button[1]");
    public final By ButtonZalo = By.xpath("//button[2]//div[1]");
    public final By LinkForgotPassword = By.xpath("//a[contains(text(),'Quên mật khẩu?')]");
    public final By TitleSignIn = By.xpath("//h1[contains(text(),'Đăng nhập SMEMBER')]");
    public final By LabelPhoneNumber = By.xpath("//label[contains(text(),'Số điện thoại')]");
    public final By LabelPassword = By.xpath("//label[contains(text(),'Mật khẩu')]");
    private final By BoxText = By.xpath("//div[@class='w-full text-small p-1x-small border-");
    private final By BannerSignIn = By.xpath("//div[@class='flex-5 min-h-screen bg-neutral-50 flex justify-center items-center px-1x-small py-large tablet:px-large tablet:py-4x-large']");
    private final By ErrorMessage = By.xpath("//ol[@class='toaster group']//li");


    public SignIn_Page(WebDriver driver) {
        super(driver);
        SignIn_Page.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void closePopupIfVisible() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement popup = shortWait.until(ExpectedConditions.visibilityOfElementLocated(popupCloseBtn));
            shortWait.until(ExpectedConditions.elementToBeClickable(popup)).click();
            wait.until(ExpectedConditions.invisibilityOf(popup));
            System.out.println("Popup quảng cáo đã được đóng.");
        } catch (TimeoutException e) {
            System.out.println("Không có popup xuất hiện.");
        }

        clickElement(loginButton);
        clickElement(loginButton2);
    }

    public void SignIn() {
        clickElement(loginButton);
        clickElement(loginButton2);
    }


    public Homepage_page InputSignIn(String phoneNumber, String password) {
        sendKeys(inputPhoneNumber, phoneNumber);
        sendKeys(inputPassword, password);
        clickElement(btnSignIn);
        return new Homepage_page(driver);
    }

    public WebElement getInputPassword() {
        return driver.findElement(inputPassword);
    }

    public void inputPhoneNumber(String phoneNumber) {
        sendKeys(inputPhoneNumber, phoneNumber);
    }

    public static WebElement getPhoneNumberField() {
        return driver.findElement(inputPhoneNumber);
    }

    public void inputPassword(String password) {
        sendKeys(inputPassword, password);
    }

    public static WebElement getPasswordField() {
        return driver.findElement(inputPassword);
    }
    public void InputSignIn2_noclick(String phoneNumber, String password) {
        sendKeys(inputPhoneNumber, phoneNumber);
        sendKeys(inputPassword, password);
    }

    public static void ClickSignIn() {
        clickElement(btnSignIn);
    }

    public void clearPhoneNumber(String phoneNumber, String password) throws InterruptedException {
        sendKeys(inputPhoneNumber, phoneNumber);
        sendKeys(inputPassword, password);
        // Dùng tổ hợp phím để chắc chắn xóa hết dữ liệu SĐT
        sendKeys(inputPhoneNumber, Keys.CONTROL + "a");
        sendKeys(inputPhoneNumber, String.valueOf(Keys.BACK_SPACE));
        Thread.sleep(3000);
        clickElement(btnSignIn);
    }

    public void ClickLinkRegister() {
        clickElement(linkRegister);
    }

    public void ClickLinkCellphoneS() throws InterruptedException {
        WebElement clickElement = wait.until(ExpectedConditions.elementToBeClickable(linkCellphoneS));
        try {
            clickElement.click();
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].scrollIntoView(true);", clickElement);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clickElement);
        }
    }

    public void ClickLinkDienThoaiVui() {
        WebElement clickElement = wait.until(ExpectedConditions.elementToBeClickable(linkDienThoaiVui));
        try {
            clickElement.click();
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].scrollIntoView(true);", clickElement);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clickElement);
        }
    }

    public void ClickLinkChinhSachUuDai() {
        clickElement(linkChinhSachUuDai);
    }

    public void ClickButtonHidePassword() {
        clickElement(buttonHidePassword);
    }

    public boolean isButtonHidePasswordClickable() {
        return isElementEnabled(buttonHidePassword);
    }

    public void ClickButtonShowPassword() {
        clickElement(buttonShowPassword);
    }

    public boolean isButtonShowPasswordClickable() {
        return isElementEnabled(buttonShowPassword);
    }

    public void ClickButtonGoogle() {
        clickElement(ButtonGoogle);
    }

    public void ClickButtonZalo() {
        clickElement(ButtonZalo);
    }
    public void ClickLinkForgotPassword() {
        clickElement(LinkForgotPassword);
    }

    public boolean isTitleSignInDisplayed() {
        return isElementDisplayed(TitleSignIn);
    }

    public boolean isLabelPhoneNumberDisplayed() {
        return isElementDisplayed(LabelPhoneNumber);
    }

    public boolean isLabelPasswordDisplayed() {
        return isElementDisplayed(LabelPassword);
    }

    public boolean isBoxTextDisplayed() {
        return isElementDisplayed(BoxText);
    }

    public boolean isBannerSignInDisplayed() {
        return isElementDisplayed(BannerSignIn);
    }

    public boolean inputPhoneNumberEnabled() {
        return isElementEnabled(inputPhoneNumber);
    }

    public boolean inputPasswordEnabled() {
        return isElementEnabled(inputPassword);
    }

    public String getErrorMessage1Displayed() {
        try {
            return driver.findElement(ErrorMessage).getText().trim();
        } catch (Exception e) {
            return "No error message found";
        }
    }

}

