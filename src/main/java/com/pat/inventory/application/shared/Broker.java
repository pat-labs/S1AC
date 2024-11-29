package com.pat.inventory.application.shared;

import com.pat.inventory.infrastructure.shared.exceptions.InfrastructureException;

public interface Broker {
    void sendMessage(String message) throws InfrastructureException;
}
