package com.pat.s1ac.infrastructure.mymongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyMongo {
    private static final Logger LOGGER = Logger.getLogger(MyMongo.class.getName());
    private final MongoClient mongoClient;
    private final MongoDatabase database;

    public MyMongo(MongoConfig config) {
        String uri = createMongoUri(config);
        LOGGER.log(Level.INFO, "Connecting to MongoDB with URI: {0}", uri);
        this.mongoClient = MongoClients.create(uri);
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

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
