package com.mediscreen.exception;

import com.mediscreen.constant.Messages;

public class RequiredInputException extends Exception {
    public RequiredInputException(){
        super(Messages.REQUIRED_INPUT);
    }
}
