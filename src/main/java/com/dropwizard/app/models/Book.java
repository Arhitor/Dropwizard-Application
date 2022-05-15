package com.dropwizard.app.models;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Book implements Serializable {

    private String id;
    private String title;
    private String author;
    private Status status;
    private Rating rating;
    private LocalDateTime addedAt;
    private Date finishedAt;

    public Book (){}

}
