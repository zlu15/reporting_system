package com.antra.evaluation.reporting_system.exception;

public class FileNotFoundException extends RuntimeException{

    public FileNotFoundException(String errorMessage){
        super(errorMessage);
    }

}
