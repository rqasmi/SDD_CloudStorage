package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CredentialController {

    private CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping("/home/credential")
    public String addCredential(@ModelAttribute("credential") Credential credential, Model model,
                                   Authentication authentication, RedirectAttributes redirectAttributes) {
        String errorMsg;
        String successMsg;

        Credential credentialInDb = credentialService.getCredentialWithId(credential.getCredentialId());

        if(credentialInDb != null) {
            int rowsUpdated = credentialService.updateCredential(credential);
            errorMsg = rowsUpdated < 1 ? "An error occurred while updating credential. Please try again." : null;
            successMsg = rowsUpdated >= 1 ? "Successfully updated credential." : null;
        }
        else {
            int credentialId = credentialService.createCredential(credential, authentication.getName());
            errorMsg = credentialId  < 1 ? "An error occurred while adding credential. Please try again." : null;
            successMsg = credentialId >= 1 ? "Successfully added credential." : null;
        }

        if(errorMsg == null) {
            model.addAttribute("userCredentials", credentialService.getUserCredentials(authentication.getName()));
//            successMsg = "Successfully created credential.";
            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("successMsg", successMsg);
        }
        else {
            redirectAttributes.addFlashAttribute("errorMsg", errorMsg);
        }

//        Note noteInDb = noteService.getNoteWithId(noteForm.getNoteId());
//
//        if(noteInDb != null) {
//            int rowsUpdated = noteService.updateNote(noteForm);
//            errorMsg = rowsUpdated < 1 ? "An error occurred while updating note. Please try again." : null;
//            successMsg = rowsUpdated >= 1 ? "Successfully updated note." : null;
//        }
//        else {
//            int noteId = noteService.postNote(noteForm, authentication.getName());
//            errorMsg = noteId < 1 ? "An error occurred while posting note. Please try again." : null;
//            successMsg = noteId >= 1 ? "Successfully posted note." : null;
//        }
//
//        if(errorMsg == null) {
//            model.addAttribute("notes", noteService.getUserNotes(authentication.getName()));
//            redirectAttributes.addFlashAttribute("success", true);
//            redirectAttributes.addFlashAttribute("successMsg", successMsg);
//        }
//        else {
//            redirectAttributes.addFlashAttribute("errorMsg", errorMsg);
//        }
        return "redirect:/result";
    }

    @GetMapping("/home/delete/credential/{id}")
    public String deleteCredential(@PathVariable("id") int id, Authentication authentication,
                                   Model model, RedirectAttributes redirectAttributes) {
        String errorMsg = null;
        String successMsg;

        Credential deletedCredential = credentialService.getCredentialWithId(id);

        if(credentialService.deleteCredential(id) < 1) {
            errorMsg = "An error occurred while deleting credential. Please try again.";
            redirectAttributes.addFlashAttribute("errorMsg", errorMsg);
        }
        else {
            model.addAttribute("credentials", credentialService.getUserCredentials(authentication.getName()));
            successMsg = "Successfully deleted credential for " + deletedCredential.getUrl() + " and username " + deletedCredential.getUsername();
            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("successMsg", successMsg);
        }
        return "redirect:/result";
    }
}
