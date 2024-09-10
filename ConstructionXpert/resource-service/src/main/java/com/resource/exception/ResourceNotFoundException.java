package com.resource.exception;

public class ResourceNotFoundException extends Exception{
    public ResourceNotFoundException(long id) {
        super("Resource with" + id + " not found");
    }
}
