package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implements the note service create, read, update or delete
 * information about notes
 */
@Service
public class NoteService {

    private NoteMapper noteMapper;
    private UserService userService;

    public NoteService(NoteMapper noteMapper, UserService userService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
    }

    /**
     * Gathers a list of all notes of a given user
     * @param username The username of the user to get the notes for
     * @return a list of all notes for the given user
     */
    public List<Note> getUserNotes(String username) {
        Integer userId = userService.getUser(username).getUserId();
        return noteMapper.getUserNotes(userId);
    }

    /**
     * Creates a note for the given user
     * @param noteForm The note form to create a new note object from
     * @param username A username to get the corresponding user for
     * @return id of the new note
     */
    public int postNote(NoteForm noteForm, String username) {
        Integer userId = userService.getUser(username).getUserId();
        return noteMapper.createNote(new Note(null, noteForm.getNoteTitle(), noteForm.getNoteDescription(), userId));
    }

    /**
     * Gets note by its id
     * @param id the id of the note
     * @return the requested note
     */
    public Note getNoteWithId(Integer id) {
        return noteMapper.getNoteWithId(id);
    }

    /**
     * Deletes a given note by id
     * @param id the id of the note to delete
     * @return the number of deleted rows
     */
    public int deleteNote(Integer id) {
        return noteMapper.deleteNote(id);
    }

    /**
     * Updates a note
     * @param noteForm A noteForm object, which contains updated information of the note
     * @return the number of rows updated in the NOTE table
     */
    public int updateNote(NoteForm noteForm) {
        return noteMapper.updateNote(new Note(noteForm.getNoteId(), noteForm.getNoteTitle(), noteForm.getNoteDescription(), null));
    }
}
