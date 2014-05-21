package com.lux.wso2.exceptions;

/**
 * Created by Igor on 07.04.2014.
 */
public class InfrastructureException extends Exception {

    public InfrastructureException() {
        super();
    }

    public InfrastructureException(String message) {
        super(message);
    }

    public InfrastructureException(String message, Throwable cause) {
        super(message, cause);
    }

    public InfrastructureException(Throwable cause) {
        super(cause);
    }
}
