package com.antra.evaluation.reporting_system.exception;

import com.antra.evaluation.reporting_system.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler  {
    /**
     * dealing exception for the test case. A demo for using AOP idea to handling exception with a globle exception
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Throwable.class)
    public ErrorResponse handleThrowable(Throwable e, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(400);
        error.setMessage("Exception due to service level");
        return error;
    }
}