package com.mediscreen.exception;

import com.mediscreen.constant.Messages;

public class PatientNotFoundException extends Exception {
    public PatientNotFoundException(){
        super(Messages.PATIENT_NOT_FOUND);
    }
}
