package com.dropwizard.app.repository;

import com.mongodb.client.MongoClient;
import io.dropwizard.lifecycle.Managed;

public class MongoDBManaged implements Managed {

    private final com.mongodb.client.MongoClient mongoClient;

    public MongoDBManaged(final MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public void start() throws Exception {
    }

    @Override
    public void stop() throws Exception {
        mongoClient.close();
    }
}