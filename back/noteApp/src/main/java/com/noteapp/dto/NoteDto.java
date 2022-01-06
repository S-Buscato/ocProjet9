package com.noteapp.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class NoteDto {
    private long id;
    private long patientId;
    private Date createdDate;
    private Date updatedDate;
    private String note;
}
