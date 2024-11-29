package com.pat.inventory.infrastructure.shared.exceptions;

public class InfrastructureException extends Exception {

    public InfrastructureException(String message) {
        super(String.format("INFRASTRUCTURE:\n%s", message));
    }
}
