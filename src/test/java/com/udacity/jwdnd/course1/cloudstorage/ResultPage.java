package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {

    @FindBy(css = "#success-msg span")
    private WebElement successMsg;

    @FindBy(css = "#error-msg span")
    private WebElement errorMsg;

    @FindBy(css = "#return-msg a")
    private WebElement returnMsg;

    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public String getSuccessMessage() {
        return this.successMsg.getText();
    }

    public String getErrorMessage() {
        return this.errorMsg.getText();
    }

    public void navigateToHomePage() {
        this.returnMsg.click();
    }


}
