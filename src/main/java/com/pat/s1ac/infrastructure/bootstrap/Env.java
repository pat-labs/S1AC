package com.pat.s1ac.infrastructure.bootstrap;


import com.pat.s1ac.infrastructure.mymongo.MongoConfig;
import com.pat.s1ac.infrastructure.myrabbitmq.RabbitMQConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Env {
    public static Bootstrap fromEnv() {
        Bootstrap.App app = new Bootstrap.App(
                System.getenv("APP_HOST"),
                System.getenv("APP_VERSION"),
                System.getenv("APP_PORT")
        );

        var mongoClient = new MongoConfig(
                System.getenv("MONGO_HOST"),
                System.getenv("MONGO_PORT"),
                System.getenv("MONGO_USER"),
                System.getenv("MONGO_PASSWORD"),
                System.getenv("MONGO_DB")
        );

        var rabbitMQClient = new RabbitMQConfig(
                System.getenv("RABBITMQ_HOST"),
                System.getenv("RABBITMQ_PORT"),
                System.getenv("RABBITMQ_USER"),
                System.getenv("RABBITMQ_PASSWORD")
        );

        return new Bootstrap(app, mongoClient, rabbitMQClient);
    }

    public static Bootstrap fromFile(String filePath) throws IOException {
        Map<String, String> envVars = new HashMap<>();
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.forEach(line -> {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    envVars.put(parts[0], parts[1]);
                }
            });
        }

        Bootstrap.App app = new Bootstrap.App(
                envVars.get("APP_HOST"),
                envVars.get("APP_VERSION"),
                envVars.get("APP_PORT")
        );

        var mongoClient = new MongoConfig(
                envVars.get("MONGO_HOST"),
                envVars.get("MONGO_PORT"),
                envVars.get("MONGO_USER"),
                envVars.get("MONGO_PASSWORD"),
                envVars.get("MONGO_DB")
        );

        var rabbitMQClient = new RabbitMQConfig(
                envVars.get("RABBITMQ_HOST"),
                envVars.get("RABBITMQ_PORT"),
                envVars.get("RABBITMQ_USER"),
                envVars.get("RABBITMQ_PASSWORD")
        );

        return new Bootstrap(app, mongoClient, rabbitMQClient);
    }
}
