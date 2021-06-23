package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Implements a controller for the home endpoint.
 */
@Controller
public class HomeController {

    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;

    public HomeController(FileService fileService, NoteService noteService, CredentialService credentialService, EncryptionService encryptionService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    /**
     * Returns the homepage with bound elements.
     * @param noteForm The note form.
     * @param credential The credential form.
     * @param authentication An object containing the currently logged in user.
     * @param model The homepage model.
     * @return string corresponding to home.html page
     */
    @GetMapping("/home")
    public String getHomePage(@ModelAttribute("noteForm") NoteForm noteForm, @ModelAttribute("credential") Credential credential,
                              Authentication authentication, Model model) {
        String username = authentication.getName();
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("files", fileService.getUserFiles(username));
        model.addAttribute("notes", noteService.getUserNotes(username));
        model.addAttribute("userCredentials", credentialService.getUserCredentials(username));
        return "home";
    }

}
