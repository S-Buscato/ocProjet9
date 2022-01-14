package com.mediscreen.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class PatientBean {
    private long id;
    private String firstname;
    private String lastname;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dob;
    private String address;
    private String phone;
    private Character sex;
}
