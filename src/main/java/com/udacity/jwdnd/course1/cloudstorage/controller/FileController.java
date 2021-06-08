package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class FileController {

    protected final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/home/file/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") int id) {
        File file = fileService.getFile(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFileName());
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ByteArrayResource resource = new ByteArrayResource(file.getFileData());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(Long.parseLong(file.getFileSize()))
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .body(resource);
    }


    @PostMapping("/home/file-upload")
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile fileUpload,
                                   Authentication authentication, Model model, RedirectAttributes redirectAttributes) throws IOException {

        String errorMsg = null;
        String successMsg = null;

        String username = authentication.getName();

        if (fileUpload.isEmpty()) {
            errorMsg = "Please select a file to upload.";
        }

        if(!fileService.isFileNameAvailable(fileUpload.getOriginalFilename())) {
            errorMsg = "A file with the given name already exists.";
        }

        if(errorMsg == null) {
            int rowsAdded = fileService.uploadFile(fileUpload, username);
            if(rowsAdded < 0) {
                errorMsg = "An error occurred while uploading the file. Please try again.";
            }
        }

        if(errorMsg == null) {
            model.addAttribute("files", fileService.getUserFiles(username));
            successMsg = "Successfully uploaded the file.";
            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("successMsg", successMsg);
        }
        else {
            redirectAttributes.addFlashAttribute("errorMsg", errorMsg);
        }
        return "redirect:/result";
    }

    @GetMapping("/home/delete/{id}")
    public String deleteFile(@PathVariable("id") int id, Authentication authentication,
                             Model model, RedirectAttributes redirectAttributes) {
        String errorMsg = null;
        String successMsg;

        File deletedFile = fileService.getFile(id);

        if(fileService.deleteFile(id) < 1) {
            errorMsg = "An error occurred while deleting file. Please try again.";
            redirectAttributes.addFlashAttribute("errorMsg", errorMsg);
        }
        else {
            model.addAttribute("files", fileService.getUserFiles(authentication.getName()));
            successMsg = "Successfully deleted file " + deletedFile.getFileName();
            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("successMsg", successMsg);
        }
        return "redirect:/result";
    }
}
