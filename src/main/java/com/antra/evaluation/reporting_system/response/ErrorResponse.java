package com.antra.evaluation.reporting_system.response;

/**
 * This object will return the HTTP Status code and error message as HTTP Response
 */
public class ErrorResponse {
    private int errorCode;
    private String message;
    public int getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}