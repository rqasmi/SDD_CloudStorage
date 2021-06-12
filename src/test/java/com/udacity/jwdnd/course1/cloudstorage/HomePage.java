package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(css = "#logout-btn")
    private WebElement logoutBtn;

    @FindBy(css = "#nav-tab")
    private WebElement navTab;

    @FindBy(css = "#nav-files-tab")
    private WebElement navFilesTab;

    // Note elements

    @FindBy(css = "#nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id = "add-note")
    private WebElement addNoteBtn;

    @FindBy(id = "note-title")
    private WebElement noteTitleInput;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionInput;

    @FindBy(id = "note-modal-save-btn")
    private WebElement noteModalSaveBtn;

    @FindBy(id = "note-submit")
    private WebElement noteSubmit;

    @FindBy(id = "note-title-text")
    private WebElement noteTitleText;

    @FindBy(id = "note-description-text")
    private WebElement noteDescriptionText;

    @FindBy(id = "edit-note-btn")
    private WebElement editNoteBtn;

    @FindBy(id = "delete-note")
    private WebElement deleteNoteBtn;

    // Credentials elements

    @FindBy(css = "#nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(id = "add-credential")
    private WebElement addCredentialBtn;

    @FindBy(id = "credential-url")
    private WebElement credUrlInput;

    @FindBy(id = "credential-username")
    private WebElement credUsernameInput;

    @FindBy(id = "credential-password")
    private WebElement credPasswordInput;

    @FindBy(id = "cred-save-btn")
    private WebElement credSaveBtn;

    @FindBy(id = "cred-url")
    private WebElement credUrl;

    @FindBy(id = "cred-username")
    private WebElement credUsername;

    @FindBy(id = "cred-password")
    private WebElement credPassword;

    @FindBy(id = "edit-cred-btn")
    private WebElement editCredBtn;

    @FindBy(id = "delete-cred")
    private WebElement deleteCredBtn;


    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public WebElement getLogoutBtn() {
        return logoutBtn;
    }

    public WebElement getNavTab() {
        return navTab;
    }

    public WebElement getNavFilesTab() {
        return navFilesTab;
    }

    public WebElement getNavNotesTab() {
        return navNotesTab;
    }

    public WebElement getNavCredentialsTab() {
        return navCredentialsTab;
    }

    public void logout() {
        logoutBtn.click();
    }

    public void createNote(String noteTitle, String noteDescription) {
        try {
            navigateToNotes();
            Thread.sleep(2000);
            addNoteBtn.click();
            Thread.sleep(2000);
            noteTitleInput.sendKeys(noteTitle);
            noteDescriptionInput.sendKeys(noteDescription);
            noteModalSaveBtn.click();
        } catch (InterruptedException e) {}
    }

    public void editNote(String noteTitle, String noteDescription) {
        try {
            navigateToNotes();
            Thread.sleep(2000);
            editNoteBtn.click();
            Thread.sleep(2000);
            noteTitleInput.clear();
            noteTitleInput.sendKeys(noteTitle);
            noteDescriptionInput.clear();
            noteDescriptionInput.sendKeys(noteDescription);
            noteModalSaveBtn.click();
        } catch (InterruptedException e) {}
    }

    public void deleteNote() {
        try {
            navigateToNotes();
            Thread.sleep(2000);
            deleteNoteBtn.click();
        } catch (InterruptedException e) {}
    }

    public String getNoteTitleText() {
        return this.noteTitleText.getText();
    }

    public String getNoteDescriptionText() {
        return this.noteDescriptionText.getText();
    }

    public void navigateToNotes() {
        navNotesTab.click();
    }

    // Credentials helper methods  ___________________________________________________________________________________

    public void addCredential(String url, String username, String password) {
        try {
            navigateToCredentials();
            Thread.sleep(2000);
            addCredentialBtn.click();
            Thread.sleep(2000);
            credUrlInput.sendKeys(url);
            credUsernameInput.sendKeys(username);
            credPasswordInput.sendKeys(password);
            credSaveBtn.click();
        } catch (InterruptedException e) {}
    }

    public void viewEditCredentialModal() {
        try {
            navigateToCredentials();
            Thread.sleep(2000);
            editCredBtn.click();
            Thread.sleep(2000);
        } catch (InterruptedException e) {}
    }

    public void editCredential(String url, String username, String password) {
        credUrlInput.clear();
        credUrlInput.sendKeys(url);
        credUsernameInput.clear();
        credUsernameInput.sendKeys(username);
        credPasswordInput.clear();
        credPasswordInput.sendKeys(password);
        credSaveBtn.click();
    }

    public void navigateToCredentials() {
        navCredentialsTab.click();
    }

    public String getCredentialUrlText() {
        return this.credUrl.getText();
    }

    public String getCredentialUsernameText() {
        return this.credUsername.getText();
    }

    public String getCredentialPasswordText() {
        return this.credPassword.getText();
    }

    public String getCredentialPasswordInputText() {
        return this.credPasswordInput.getAttribute("value");
    }
}
