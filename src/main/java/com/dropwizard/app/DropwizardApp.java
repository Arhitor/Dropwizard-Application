package com.dropwizard.app;

import com.dropwizard.app.controller.BookController;
import com.dropwizard.app.repository.MongoDBConfiguration;
import com.dropwizard.app.repository.MongoDBHealthCheck;
import com.dropwizard.app.repository.MongoDBManaged;
import com.dropwizard.app.service.BookService;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoClient;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DropwizardApp extends Application<MongoDBConfiguration> {

    private static final Logger logger = LoggerFactory.getLogger(DropwizardApp.class);

    public static void main(String[] args) throws Exception {
        new DropwizardApp().run("server", args[0]);
    }

    @Override
    public void initialize(Bootstrap<MongoDBConfiguration> b) {
    }

    @Override
    public void run(MongoDBConfiguration config, Environment env)
            throws Exception {
        MongoClient mongoClient = MongoClients.create();
        MongoDBManaged mongoDBManaged = new MongoDBManaged(mongoClient);
        env.lifecycle().manage(mongoDBManaged);
        MongoDatabase db = mongoClient.getDatabase(config.getMongoDB());
        MongoCollection<Document> collection = db.getCollection(config.getCollectionName());
        logger.info("Registering API");
        env.jersey().register(new BookController(collection, new BookService()));
        env.healthChecks().register("DropwizardMongoDBHealthCheck",
                new MongoDBHealthCheck(mongoClient));
    }
}