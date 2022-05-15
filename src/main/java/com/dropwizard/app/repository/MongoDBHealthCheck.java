package com.dropwizard.app.repository;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

public class MongoDBHealthCheck extends HealthCheck {

    private static final Logger logger = LoggerFactory.getLogger(MongoDBHealthCheck.class);

    private MongoClient mongoClient;

    public MongoDBHealthCheck(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    protected Result check() throws Exception {
        List<String> dbs = new ArrayList<>();
        MongoCursor<String> dbsCursor = mongoClient.listDatabaseNames().iterator();
        while (dbsCursor.hasNext()) {
            dbs.add(dbsCursor.next());
        }
        if (dbs.size() > 0) {
            return Result.healthy("Database names in MongogDB are: " + dbs);
        }
        return Result.unhealthy("DropwizardMongoDB Service is down");
    }
}

