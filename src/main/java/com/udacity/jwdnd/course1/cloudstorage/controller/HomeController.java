package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/file")
    public String getUserFiles(Authentication authentication, Model model) {
        String username = authentication.getName();
        model.addAttribute("files", fileService.getUserFiles(username));
        return "home";
    }

    @PostMapping("/file-upload")
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile fileUpload,
                                   Authentication authentication, Model model) throws IOException, SQLException {

        String errorMsg = null;

        if (fileUpload.isEmpty()) {
            errorMsg = "Please select a file to upload.";
        }

        if(!fileService.isFileNameAvailable(fileUpload.getOriginalFilename())) {
            errorMsg = "A file with the given name already exists.";
        }

        if(errorMsg == null) {
            int rowsAdded = fileService.uploadFile(fileUpload, authentication.getName());
            if(rowsAdded < 0) {
                errorMsg = "An error occurred while uploading the file. Please try again.";
            }
        }

        if(errorMsg == null) {
            model.addAttribute("success", true);
        }
        else {
            model.addAttribute("errorMsg", errorMsg);
        }

        return "result";
    }
}
