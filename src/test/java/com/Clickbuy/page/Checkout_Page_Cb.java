package com.Clickbuy.page;

import com.helpers.ValidateUIHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Checkout_Page_Cb extends ValidateUIHelper{
    private static WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private ValidateUIHelper validateUIHelper;

    public By PopupModal = By.xpath("//div[@id='popup-modal']");
    public By TitlePopupModal = By.xpath("//h2[contains(text(),'Đặt hàng sản phẩm')]");

    public Checkout_Page_Cb(WebDriver driver) {
        super(driver);
        Checkout_Page_Cb.driver = driver;
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        validateUIHelper = new ValidateUIHelper(driver);
    }

    public boolean isPopupModalDisplayed() {
        return validateUIHelper.isElementDisplayed(PopupModal);
    }

    public boolean isTitlePopupModalDisplayed() {
        return validateUIHelper.isElementDisplayed(TitlePopupModal);
    }

    public String getTitlePopupModal() {
        return validateUIHelper.getText(TitlePopupModal);
    }

}
