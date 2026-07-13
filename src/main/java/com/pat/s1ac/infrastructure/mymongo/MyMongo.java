package com.pat.s1ac.infrastructure.mymongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.connection.ConnectionPoolSettings;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyMongo {
    private static final Logger LOGGER = Logger.getLogger(MyMongo.class.getName());
    private final MongoClient mongoClient;
    private final MongoDatabase database;

    public MyMongo(MongoConfig config) {
        String uri = createMongoUri(config);
        LOGGER.log(Level.INFO, "Connecting to MongoDB with URI: {0}", uri);

        ConnectionString connectionString = new ConnectionString(uri);

        ConnectionPoolSettings poolSettings = ConnectionPoolSettings.builder()
                .maxSize(50) // max pooled connections
                .minSize(5)  // keep warm connections
                .maxWaitTime(2, TimeUnit.SECONDS) // wait for a connection
                .maxConnectionIdleTime(60, TimeUnit.SECONDS) // idle timeout
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .applyToConnectionPoolSettings(builder -> builder.applySettings(poolSettings))
                .applyToSocketSettings(builder -> builder
                        .connectTimeout(3, TimeUnit.SECONDS)
                        .readTimeout(5, TimeUnit.SECONDS))
                .build();

        this.mongoClient = MongoClients.create(settings);
        this.database = mongoClient.getDatabase(config.database());
    }

    private String createMongoUri(MongoConfig config) {
        return String.format(
                "mongodb://%s:%s@%s:%s/%s?authSource=admin",
                config.user(),
                config.password(),
                config.host(),
                config.port(),
                config.database()
        );
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void close() {
        if (mongoClient != null) {
            LOGGER.info("Closing MongoDB connection...");
            mongoClient.close();
        }
    }
}
