package com.antra.evaluation.reporting_system.exception;

/**
 * This customized exception is trying to handle when user try to delete or download an non-exisiting file
 */
public class FileNotFoundException extends RuntimeException{
    public FileNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
