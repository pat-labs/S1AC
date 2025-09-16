package com.pat.s1ac.infrastructure.myrabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyRabbitmq {
    private static final Logger LOGGER = Logger.getLogger(MyRabbitmq.class.getName());
    private final Connection connection;
    private final Channel channel;

    public MyRabbitmq(RabbitMQConfig rabbitMQConfig) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitMQConfig.host());
        factory.setPort(Integer.parseInt(rabbitMQConfig.port()));
        factory.setUsername(rabbitMQConfig.user());
        factory.setPassword(rabbitMQConfig.password());

        try {
            LOGGER.log(Level.INFO, "Connecting to RabbitMQ at {0}:{1}", new Object[]{rabbitMQConfig.host(), rabbitMQConfig.port()});
            this.connection = factory.newConnection();
            this.channel = connection.createChannel();
            LOGGER.log(Level.INFO, "Successfully connected to RabbitMQ.");
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException("Failed to set up RabbitMQ connection", e);
        }
    }

    public void sendMessage(String queueName, String message) {
        try {
            LOGGER.log(Level.INFO, "Sending message to queue: {0}", queueName);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.basicPublish("", queueName, null, message.getBytes());
            LOGGER.log(Level.INFO, "Message sent successfully to queue: {0}", queueName);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to send message to queue: " + queueName, e);
            throw new RuntimeException("Failed to send RabbitMQ message", e);
        }
    }

    public void close() {
        try {
            if (channel != null && channel.isOpen()) {
                channel.close();
            }
            if (connection != null && connection.isOpen()) {
                connection.close();
            }
            LOGGER.log(Level.INFO, "RabbitMQ connection closed.");
        } catch (IOException | TimeoutException e) {
            LOGGER.log(Level.WARNING, "Failed to close RabbitMQ resources cleanly", e);
            throw new RuntimeException("Failed to close RabbitMQ resources", e);
        }
    }
}
