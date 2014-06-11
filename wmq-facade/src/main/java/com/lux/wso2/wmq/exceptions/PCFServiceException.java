package com.lux.wso2.wmq.exceptions;

/**
 * Created by Igor on 23.05.2014.
 */
public class PCFServiceException extends Exception {

    public PCFServiceException() {
    }

    public PCFServiceException(String message) {
        super(message);
    }

    public PCFServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
