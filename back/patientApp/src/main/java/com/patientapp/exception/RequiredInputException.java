package com.patientapp.exception;

import com.patientapp.constant.Messages;

public class RequiredInputException extends Exception {
    public RequiredInputException(){
        super(Messages.REQUIRED_INPUT);
    }
}
