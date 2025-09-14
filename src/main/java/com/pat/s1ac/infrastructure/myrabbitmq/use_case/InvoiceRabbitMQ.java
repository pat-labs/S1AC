package com.pat.s1ac.infrastructure.myrabbitmq.use_case;

import com.pat.s1ac.domain.broker.IBrokerProducer;
import com.pat.s1ac.infrastructure.myrabbitmq.MyRabbitmq;
import com.pat.s1ac.infrastructure.myrabbitmq.error.RabbitmqExceptionCauses;

import java.io.IOException;

public class InvoiceRabbitMQ implements IBrokerProducer {
    private static final String queue = "invoice";
    private final MyRabbitmq myRabbitmq;

    public InvoiceRabbitMQ(MyRabbitmq myRabbitmq) {
        this.myRabbitmq = myRabbitmq;
    }

    @Override
    public void sendMessage(String message) {
        try {
            myRabbitmq.sendMessage(queue, message);
        } catch (IOException e) {
            throw new RuntimeException(RabbitmqExceptionCauses.sendError(), e);
        }
    }

}
