package com.noteapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@NoArgsConstructor
@Data
@Generated
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "notes")
public class Note {


    @Transient
    public static final String SEQUENCE_NAME = "notes_sequence";

    @Id
    private long id;

    @Field(value = "date_create")
    private Date createdDate;

    @Field(value = "date_update")
    private Date updatedDate;

    @Field(value = "patient")
    private long patientId;

    @Field(value = "note")
    private String note;
}

