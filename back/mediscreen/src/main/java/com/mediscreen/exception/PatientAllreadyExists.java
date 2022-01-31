package com.mediscreen.exception;

import com.mediscreen.constant.Messages;

public class PatientAllreadyExists extends Exception {
    public PatientAllreadyExists(){
        super(Messages.PATIENT_ALREADY_EXISTS);
    }
}
