package com.mediscreen.exception;

import com.mediscreen.constant.Messages;

public class PatientAllreadyExists extends Exception {
    public PatientAllreadyExists(){
        super(Messages.USER_ALREADY_EXISTS);
    }
}
