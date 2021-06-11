package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    @FindBy(css = "#logout-btn")
    private WebElement logoutBtn;

    @FindBy(css = "#nav-tab")
    private WebElement navTab;

    @FindBy(css = "#nav-files-tab")
    private WebElement navFilesTab;

    @FindBy(css = "#nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(css = "#nav-credentials-tab")
    private WebElement navCredentialsTab;

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
        navigateToNotes();
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        addNoteBtn.click();
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        noteTitleInput.sendKeys(noteTitle);
        noteDescriptionInput.sendKeys(noteDescription);
        noteModalSaveBtn.click();
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
}
