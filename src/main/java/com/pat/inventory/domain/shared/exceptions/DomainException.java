package com.pat.inventory.domain.shared.exceptions;

public class DomainException extends Exception {

    public DomainException(String message) {
        super(String.format("DOMAIN:\n%s", message));
    }
}
