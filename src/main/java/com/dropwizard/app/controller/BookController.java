package com.dropwizard.app.controller;

import com.codahale.metrics.annotation.Timed;
import com.dropwizard.app.models.Book;
import com.dropwizard.app.service.BookService;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class BookController {

    private MongoCollection<Document> collection;
    private final BookService bookService;

    public BookController(MongoCollection<Document> collection, BookService bookService) {
        this.collection = collection;
        this.bookService = bookService;
    }

    @GET
    @Timed
    @Path("/books")
    public Response getAll() {
        List<Document> documents = bookService.findAll(collection);
        return Response.ok(documents).build();
    }

    @GET
    @Path("/book/{id}")
    @Timed
    public Response getBook(@PathParam("id") final String id) {
        List<Document> documents = bookService.getById(collection, id);
        return Response.ok(documents).build();
    }

    @POST
    @Timed
    @Path("/add_book")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(@NotNull @Valid final Book book) {
        bookService.add(collection, book);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Book added");
        return Response.ok(response).build();
    }

    @PUT
    @Path("/update/{id}")
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") @NotNull @Valid final String id, final Book book) {
        bookService.update(collection, id, book);
        Map<String, String> response = new HashMap<>();
        response.put("message", "updated successfully");
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/delete/{id}")
    @Timed
    public Response delete(@PathParam("id") final String id) {
        bookService.delete(collection, id);
        Map<String, String> response = new HashMap<>();
        response.put("message","deleted successfully");
        return Response.ok(response).build();
    }
}