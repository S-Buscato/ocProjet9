package com.mediscreen.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class PatientDto {

    private long id;
    private String firstname;
    private String lastname;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date dob;
    private String address;
    private String phone;
    private Character sex;

}
