package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

//    @PostMapping("/home/post-note")
//    public String postNote(@ModelAttribute("note") Note note, Authentication authentication, Model model) {
//        String errorMsg = null;
//        String successMsg;
//
//        if(noteService.postNote(note, authentication.getName()) < 0) {
//            errorMsg = "An error occurred while posting note. Please try again.";
//            model.addAttribute("errorMsg", errorMsg);
//        }
//        else {
//            model.addAttribute("notes", noteService.getUserNotes(authentication.getName()));
//            successMsg = "Successfully posted note.";
//            model.addAttribute("success", true);
//            model.addAttribute("successMsg", successMsg);
//        }
//
//        return "home";
//    }
}
