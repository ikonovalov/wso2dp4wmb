package com.lux.wso2.exceptions;

/**
 * Created by Igor on 07.04.2014.
 */
public class StreamException extends Exception {

    public StreamException() {
    }

    public StreamException(String message) {
        super(message);
    }

    public StreamException(String message, Throwable cause) {
        super(message, cause);
    }

    public StreamException(Throwable cause) {
        super(cause);
    }
}
