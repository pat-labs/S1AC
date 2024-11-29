package com.pat.inventory.application.shared.exceptions;

public class ApplicationException extends Exception {

    public ApplicationException(String message) {
        super(String.format("APPLICATION:\n%s", message));
    }
}
