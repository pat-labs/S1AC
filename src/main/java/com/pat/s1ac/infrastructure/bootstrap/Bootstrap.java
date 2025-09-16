package com.pat.s1ac.infrastructure.bootstrap;

import com.pat.s1ac.infrastructure.mymongo.MongoConfig;
import com.pat.s1ac.infrastructure.myrabbitmq.RabbitMQConfig;

public record Bootstrap(
        App app,
        MongoConfig mongoConfig,
        RabbitMQConfig rabbitMQConfig
) {
    public record App(String host, String version, String port) {
    }
}
