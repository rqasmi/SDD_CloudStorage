package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.MessageService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private UserService userService;
    private MessageService messageService;

    public SignupController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @GetMapping()
    public String getSignupPage() {
        return "signup";
    }

    @PostMapping()
    public String createUser(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {
        String signupError = null;

        if(!userService.isUsernameAvailable(user.getUsername())) {
            signupError = messageService.getExistingUsernameError();
        }

        if(signupError == null) {
            int rowsAdded = userService.createUser(user);
            if(rowsAdded < 0) {
                signupError = messageService.getSignupError();
            }
        }

        if(signupError == null) {
            redirectAttributes.addFlashAttribute("successfulSignup", true);
            return "redirect:/login";
        }
        else {
            model.addAttribute("signupError", signupError);
        }

        return "signup";
    }
}
