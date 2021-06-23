package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.MessageService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Implements a controller for the note API.
 */
@Controller
public class NoteController {

    private NoteService noteService;
    private MessageService messageService;

    public NoteController(NoteService noteService, MessageService messageService) {
        this.noteService = noteService;
        this.messageService = messageService;
    }

    /**
     * Posts information to create a new note/update an existing note in the database.
     * @param noteForm The note form.
     * @param model The homepage model.
     * @param authentication Authentication object.
     * @param redirectAttributes  Attributes to pass on to redirect page.
     * @return string corresponding to html page
     */
    @PostMapping("/home/note/post")
    public String postNote(@ModelAttribute("noteForm") NoteForm noteForm, Authentication authentication,
                           Model model, RedirectAttributes redirectAttributes) {
        String errorMsg = null;
        String successMsg = null;

        Note noteInDb = noteService.getNoteWithId(noteForm.getNoteId());

        if(noteInDb != null) {
            int rowsUpdated = noteService.updateNote(noteForm);
            errorMsg = rowsUpdated < 1 ? messageService.getNoteUpdateError() : null;
            successMsg = rowsUpdated >= 1 ? messageService.getNoteUpdateSuccess() : null;
        }
        else {
            int noteId = noteService.postNote(noteForm, authentication.getName());
            errorMsg = noteId < 1 ? messageService.getNotePostError() : null;
            successMsg = noteId >= 1 ? messageService.getNotePostSuccess() : null;
        }

        if(errorMsg == null) {
            model.addAttribute("notes", noteService.getUserNotes(authentication.getName()));
            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("successMsg", successMsg);
        }
        else {
            redirectAttributes.addFlashAttribute("errorMsg", errorMsg);
        }
        return "redirect:/result";
    }

    /**
     * Gets the delete endpoint to delete a note from the database.
     * @param id The note id.
     * @param authentication The Authentication object.
     * @param model The homepage model.
     * @param redirectAttributes Attributes to pass on to the redirect page.
     * @return string corresponding to the html page
     */
    @GetMapping("/home/note/delete/{id}")
    public String deleteNote(@PathVariable("id") int id, Authentication authentication,
                             Model model, RedirectAttributes redirectAttributes) {
        String errorMsg = null;
        String successMsg;

        Note deletedNote = noteService.getNoteWithId(id);

        if(noteService.deleteNote(id) < 1) {
            errorMsg = messageService.getNoteDeletionError();
            redirectAttributes.addFlashAttribute("errorMsg", errorMsg);
        }
        else {
            model.addAttribute("notes", noteService.getUserNotes(authentication.getName()));
            successMsg = messageService.getNoteDeletionSuccess(deletedNote.getNoteTitle());
            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("successMsg", successMsg);
        }
        return "redirect:/result";
    }
}
