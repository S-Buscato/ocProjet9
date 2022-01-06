package com.noteapp.exception;

import com.mediscreen.constant.Messages;

public class NoteNotFoundException extends Exception {
    public NoteNotFoundException(){
        super(Messages.PATIENT_NOT_FOUND);
    }
}
