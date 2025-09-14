package com.pat.s1ac.infrastructure.myrabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MyRabbitmq {

    private Connection connection;
    private Channel channel;

    public MyRabbitmq(RabbitMQConfig rabbitMQConfig) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitMQConfig.host());
        factory.setPort(Integer.parseInt(rabbitMQConfig.port()));
        factory.setUsername(rabbitMQConfig.user());
        factory.setPassword(rabbitMQConfig.password());

        this.connection = factory.newConnection();
        this.channel = connection.createChannel();
    }

    public void sendMessage(String queueName, String message) throws IOException {
        // Declare the queue to be idempotent. This is good practice in case the
        // consumer hasn't started up yet.
        channel.queueDeclare(queueName, true, false, false, null);
        channel.basicPublish("", queueName, null, message.getBytes());
    }

    public void close() throws IOException, TimeoutException {
        if (channel != null && channel.isOpen()) {
            channel.close();
        }
        if (connection != null && connection.isOpen()) {
            connection.close();
        }
    }
}
