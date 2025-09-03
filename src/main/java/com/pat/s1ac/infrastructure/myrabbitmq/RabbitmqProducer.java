package com.pat.s1ac.infrastructure.myrabbitmq;

import com.pat.s1ac.application.shared.BrokerProducer;
import com.pat.inventory.infrastructure.shared.error.InfrastructureException;
import com.pat.inventory.infrastructure.shared.error.InfrastructureExceptionCauses;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitmqProducer implements BrokerProducer {
    private final RabbitTemplate rabbitTemplate;

    public RabbitmqProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message, String routingKey) throws InfrastructureException {
        try {
            rabbitTemplate.convertAndSend(MyRabbitmq.EXCHANGE_NAME, routingKey, message);
            System.out.println("Message(" + message + ") has been produced and sent with routing key: " + routingKey);
        } catch (Exception e) {
            throw new InfrastructureException(InfrastructureExceptionCauses.failSendToBroker(routingKey, message));
        }
    }

    public void sendMessage(String message) throws InfrastructureException {
        String queue = "myRoutingKey.messages";
        try {
            sendMessage(message, queue);
        } catch (Exception e) {
            throw new InfrastructureException(InfrastructureExceptionCauses.failSendToBroker(queue, message));
        }
    }
}
