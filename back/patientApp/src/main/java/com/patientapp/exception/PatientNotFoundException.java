package com.patientapp.exception;

import com.patientapp.constant.Messages;

public class PatientNotFoundException extends Exception {
    public PatientNotFoundException(){
        super(Messages.PATIENT_NOT_FOUND);
    }
}
