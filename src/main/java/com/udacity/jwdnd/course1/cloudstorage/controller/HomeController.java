package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

@Controller
//@RequestMapping("/home")
public class HomeController {

    protected final FileService fileService;

    public HomeController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/home")
    public String getHomePage(Authentication authentication, Model model) {
        String username = authentication.getName();
        model.addAttribute("files", fileService.getUserFiles(username));
        return "home";
    }

}
