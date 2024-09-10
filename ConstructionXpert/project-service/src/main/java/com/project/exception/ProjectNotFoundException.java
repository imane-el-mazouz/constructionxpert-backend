package com.project.exception;

public class ProjectNotFoundException extends Exception{
    public ProjectNotFoundException(long id) {
        super("Project with" + id + " not found");
    }
}
