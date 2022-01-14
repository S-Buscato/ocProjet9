package com.mediscreen.beans;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class NoteBean {
    private long id;
    private Date createdDate;
    private Date updatedDate;
    private long patientId;
    private String note;
}
