package org.vsredshift.customer;

public class ServiceException extends RuntimeException{
    public ServiceException(String message) {
        super(message);
    }
}
