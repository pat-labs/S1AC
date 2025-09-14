package com.pat.s1ac.infrastructure.mymongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MyMongo {
    private final MongoClient mongoClient;
    private final MongoDatabase database;

    public MyMongo(MongoConfig config) {
        String uri = createMongoUri(config);
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
