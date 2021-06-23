package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.MessageService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Implements a controller for the credential API.
 */
@Controller
public class CredentialController {

    private CredentialService credentialService;
    private MessageService messageService;

    public CredentialController(CredentialService credentialService, MessageService messageService) {
        this.credentialService = credentialService;
        this.messageService = messageService;
    }

    /**
     * Posts information to create a new credential/update an existing credential in the database.
     * @param credential The credential form.
     * @param model The home page model.
     * @param authentication Authentication object.
     * @param redirectAttributes  Attributes to pass on to redirect page.
     * @return string corresponding to html page
     */
    @PostMapping("/home/credential")
    public String addCredential(@ModelAttribute("credential") Credential credential, Model model,
                                   Authentication authentication, RedirectAttributes redirectAttributes) {
        String errorMsg;
        String successMsg;

        Credential credentialInDb = credentialService.getCredentialWithId(credential.getCredentialId());

        if(credentialInDb != null) {
            int rowsUpdated = credentialService.updateCredential(credential);
            errorMsg = rowsUpdated < 1 ? messageService.getCredentialUpdateError() : null;
            successMsg = rowsUpdated >= 1 ? messageService.getCredentialUpdateSuccess() : null;
        }
        else {
            int credentialId = credentialService.createCredential(credential, authentication.getName());
            errorMsg = credentialId  < 1 ? messageService.getCredentialAddError() : null;
            successMsg = credentialId >= 1 ? messageService.getCredentialAddSuccess() : null;
        }

        if(errorMsg == null) {
            model.addAttribute("userCredentials", credentialService.getUserCredentials(authentication.getName()));
            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("successMsg", successMsg);
        }
        else {
            redirectAttributes.addFlashAttribute("errorMsg", errorMsg);
        }
        return "redirect:/result";
    }

    /**
     * Gets the delete endpoint to delete a credential from the database.
     * @param id The credential id.
     * @param authentication The Authentication object.
     * @param model The homepage model.
     * @param redirectAttributes Attributes to pass on to the redirect page.
     * @return string corresponding to the html page
     */
    @GetMapping("/home/delete/credential/{id}")
    public String deleteCredential(@PathVariable("id") int id, Authentication authentication,
                                   Model model, RedirectAttributes redirectAttributes) {
        String errorMsg = null;
        String successMsg;

        Credential deletedCredential = credentialService.getCredentialWithId(id);

        if(credentialService.deleteCredential(id) < 1) {
            errorMsg = messageService.getCredentialDeleteError();
            redirectAttributes.addFlashAttribute("errorMsg", errorMsg);
        }
        else {
            model.addAttribute("credentials", credentialService.getUserCredentials(authentication.getName()));
            successMsg = messageService.getCredentialDeletionSuccess(deletedCredential.getUrl(), deletedCredential.getUsername());
            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("successMsg", successMsg);
        }
        return "redirect:/result";
    }
}
