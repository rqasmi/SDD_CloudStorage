package com.udacity.jwdnd.course1.cloudstorage.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Implements the Error advice for file upload to intercept MaxSizeException
 */
@ControllerAdvice
public class FileUploadExceptionAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxFileSizeException(MaxUploadSizeExceededException e, Model model, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMsg", "An error occurred while uploading the file. Please try again.");
        return "redirect:/result";
    }
}
