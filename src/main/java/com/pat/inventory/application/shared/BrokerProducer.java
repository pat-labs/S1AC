package com.pat.inventory.application.shared;

import com.pat.inventory.infrastructure.shared.error.InfrastructureException;

public interface BrokerProducer {
    void sendMessage(String message) throws InfrastructureException;
}
