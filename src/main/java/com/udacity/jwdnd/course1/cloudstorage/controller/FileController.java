package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.MessageService;
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

/**
 * Implements a controller for the file API.
 */
@Controller
public class FileController {

    private FileService fileService;
    private MessageService messageService;

    public FileController(FileService fileService, MessageService messageService) {
        this.fileService = fileService;
        this.messageService = messageService;
    }

    /**
     * Downloads the file with given id.
     * @param id The file id.
     * @return Response Entity containing file contents and metadata
     */
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

    /**
     * Posts information to create a new file in the database.
     * @param fileUpload The file to upload to the server.
     * @param model The homepage model.
     * @param authentication Authentication object.
     * @param redirectAttributes  Attributes to pass on to redirect page.
     * @throws IOException if the request fails to upload the file
     * @return string corresponding to html page
     */
    @PostMapping("/home/file-upload")
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile fileUpload,
                                   Authentication authentication, Model model, RedirectAttributes redirectAttributes) throws IOException {

        String errorMsg = null;
        String successMsg = null;

        String username = authentication.getName();

        if (fileUpload.isEmpty()) {
            errorMsg = messageService.getEmptyFileError();
        }

        if(!fileService.isFileNameAvailable(fileUpload.getOriginalFilename())) {
            errorMsg = messageService.getExistingFileNameError();
        }

        if(fileUpload.getSize() > 10485760) {
            errorMsg = messageService.getExceededFileSizeError();
        }

        if(errorMsg == null) {
            int rowsAdded = fileService.uploadFile(fileUpload, username);
            if(rowsAdded < 0) {
                errorMsg = messageService.getFileUploadError();
            }
        }

        if(errorMsg == null) {
            model.addAttribute("files", fileService.getUserFiles(username));
            successMsg = messageService.getFileUploadSuccess();
            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("successMsg", successMsg);
        }
        else {
            redirectAttributes.addFlashAttribute("errorMsg", errorMsg);
        }
        return "redirect:/result";
    }

    /**
     * Gets the delete endpoint to delete a file from the database.
     * @param id The file id.
     * @param authentication The Authentication object.
     * @param model The homepage model.
     * @param redirectAttributes Attributes to pass on to the redirect page.
     * @return string corresponding to the result html page
     */
    @GetMapping("/home/delete/{id}")
    public String deleteFile(@PathVariable("id") int id, Authentication authentication,
                             Model model, RedirectAttributes redirectAttributes) {
        String errorMsg = null;
        String successMsg;

        File deletedFile = fileService.getFile(id);

        if(fileService.deleteFile(id) < 1) {
            errorMsg = messageService.getFileDeleteError();
            redirectAttributes.addFlashAttribute("errorMsg", errorMsg);
        }
        else {
            model.addAttribute("files", fileService.getUserFiles(authentication.getName()));
            successMsg = messageService.getFileDeletionSuccess() + " " + deletedFile.getFileName();
            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("successMsg", successMsg);
        }
        return "redirect:/result";
    }
}
