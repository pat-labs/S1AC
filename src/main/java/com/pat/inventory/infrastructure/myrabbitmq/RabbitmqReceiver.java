package com.pat.inventory.infrastructure.myrabbitmq;

import org.springframework.stereotype.Component;

@Component
public class RabbitmqReceiver {
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }
}
