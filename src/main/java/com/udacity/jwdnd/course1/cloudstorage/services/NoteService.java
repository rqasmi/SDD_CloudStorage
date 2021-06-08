package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;
    private UserService userService;

    public NoteService(NoteMapper noteMapper, UserService userService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
    }

    public List<Note> getUserNotes(String username) {
        Integer userId = userService.getUser(username).getUserId();
        return noteMapper.getUserNotes(userId);
    }

    public int postNote(NoteForm noteForm, String username) {
        Integer userId = userService.getUser(username).getUserId();
        return noteMapper.createNote(new Note(null, noteForm.getNoteTitle(), noteForm.getNoteDescription(), userId));
    }

    public Note getNoteWithId(Integer id) {
        return noteMapper.getNoteWithId(id);
    }

    public int deleteNote(Integer id) {
        return noteMapper.deleteNote(id);
    }

    public int updateNote(NoteForm noteForm) {
        return noteMapper.updateNote(new Note(noteForm.getNoteId(), noteForm.getNoteTitle(), noteForm.getNoteDescription(), null));
    }
}
