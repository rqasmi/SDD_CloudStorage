package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.stereotype.Service;

/**
 * Implements a message service to render messages for the controller
 */
@Service
public class MessageService {

    private final String existingUsernameError = "The username already exists.";
    private final String signupError = "There was an error signing you up. Please try again.";

    private final String emptyFileError = "Please select a file to upload.";
    private final String existingFileNameError = "A file with the given name already exists.";
    private final String exceededFileSizeError = "File size exceeded allowable 10MB. Try uploading a smaller file.";
    private final String fileUploadError = "An error occurred while uploading the file. Please try again.";
    private final String fileDeleteError = "An error occurred while deleting file. Please try again.";
    private final String fileUploadSuccess = "Successfully uploaded the file.";
    private final String fileDeletionSuccess = "Successfully deleted file";

    private final String notePostError = "An error occurred while posting note. Please try again.";
    private final String noteUpdateError = "An error occurred while updating note. Please try again.";
    private final String noteDeletionError = "An error occurred while deleting note. Please try again.";
    private final String notePostSuccess = "Successfully posted note.";
    private final String noteUpdateSuccess = "Successfully updated note.";
    private final String noteDeletionSuccess = "Successfully deleted note";

    private final String credentialAddError = "An error occurred while adding credential. Please try again.";
    private final String credentialUpdateError = "An error occurred while updating credential. Please try again.";
    private final String credentialDeleteError = "An error occurred while deleting credential. Please try again.";
    private final String credentialAddSuccess = "Successfully added credential.";
    private final String credentialUpdateSuccess = "Successfully updated credential.";
    private final String credentialDeletionSuccess = "Successfully deleted credential";

    public String getExistingUsernameError() {
        return existingUsernameError;
    }

    public String getSignupError() {
        return signupError;
    }

    public String getEmptyFileError() {
        return emptyFileError;
    }

    public String getExistingFileNameError() {
        return existingFileNameError;
    }

    public String getExceededFileSizeError() {
        return exceededFileSizeError;
    }

    public String getFileUploadError() {
        return fileUploadError;
    }

    public String getFileDeleteError() {
        return fileDeleteError;
    }

    public String getFileUploadSuccess() {
        return fileUploadSuccess;
    }

    public String getFileDeletionSuccess() {
        return fileDeletionSuccess;
    }

    public String getNotePostError() {
        return notePostError;
    }

    public String getNoteUpdateError() {
        return noteUpdateError;
    }

    public String getNoteDeletionError() {
        return noteDeletionError;
    }

    public String getNotePostSuccess() {
        return notePostSuccess;
    }

    public String getNoteUpdateSuccess() {
        return noteUpdateSuccess;
    }

    public String getNoteDeletionSuccess(String noteTitle) {
        return noteDeletionSuccess + ": " + noteTitle;
    }

    public String getCredentialAddError() {
        return credentialAddError;
    }

    public String getCredentialUpdateError() {
        return credentialUpdateError;
    }

    public String getCredentialDeleteError() {
        return credentialDeleteError;
    }

    public String getCredentialAddSuccess() {
        return credentialAddSuccess;
    }

    public String getCredentialUpdateSuccess() {
        return credentialUpdateSuccess;
    }

    public String getCredentialDeletionSuccess(String url, String username) {
        return credentialDeletionSuccess + " for " + url + " and username " + username;
    }
}
