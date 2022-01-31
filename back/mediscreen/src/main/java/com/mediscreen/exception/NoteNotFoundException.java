package com.mediscreen.exception;


import com.mediscreen.constant.Messages;

public class NoteNotFoundException extends Exception {
    public NoteNotFoundException(){
        super(Messages.NOTE_NOT_FOUND);
    }
}
