package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement submitButton;


    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    public void login(String username, String password) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);

        submitButton.click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e ){}


    }
}
