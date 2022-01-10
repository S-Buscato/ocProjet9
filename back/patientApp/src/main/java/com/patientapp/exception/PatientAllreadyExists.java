package com.patientapp.exception;

import com.patientapp.constant.Messages;

public class PatientAllreadyExists extends Exception {
    public PatientAllreadyExists(){
        super(Messages.PATIENT_ALREADY_EXISTS);
    }
}
