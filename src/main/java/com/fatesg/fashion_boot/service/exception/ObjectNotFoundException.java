package com.fatesg.fashion_boot.service.exception;

public class ObjectNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
