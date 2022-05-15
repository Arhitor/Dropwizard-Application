package com.dropwizard.app.service;

import com.dropwizard.app.models.Book;
import com.mongodb.client.MongoCollection;
import org.apache.commons.lang3.RandomStringUtils;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class BookService {

    public List<Document> findAll(MongoCollection<Document> collection) {
        return collection.find().into(new ArrayList<>());
    }

    public void add(MongoCollection<Document> collection, Book book) {
        collection.insertOne(new Document("id", RandomStringUtils.randomAlphabetic(20))
                .append("author", book.getAuthor())
                .append("status", String.valueOf(book.getStatus()))
                .append("rating", String.valueOf(book.getRating()))
                .append("addedAt", String.valueOf(LocalDateTime.now()))
                .append("finishedAt", String.valueOf(book.getFinishedAt())));
    }


    public void update(MongoCollection<Document> collection, String id, Book book) {
        collection.updateOne(new Document("id", id),
                new Document("$set", new Document("status", String.valueOf(book.getStatus()))
                        .append("rating", String.valueOf(book.getRating()))
                        .append("finishedAt", String.valueOf(book.getFinishedAt()))));
    }

    public void delete(MongoCollection<Document> collection, String value) {
        collection.deleteOne(eq("id", value));
    }

    public List<Document> getById(MongoCollection<Document> collection, String id) {
        return collection.find(eq("id", id)).into(new ArrayList<>());
    }
}