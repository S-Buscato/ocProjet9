package com.noteapp.exception;


import com.noteapp.constant.Messages;

public class NoteNotFoundException extends Exception {
    public NoteNotFoundException(){
        super(Messages.NOTE_NOT_FOUND);
    }
}
