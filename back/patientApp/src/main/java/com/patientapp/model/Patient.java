package com.patientapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@NoArgsConstructor
@Data
@Generated
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name="patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="firstname")
    private String firstname;

    @Column(name="lastname")
    private String lastname;

    @Column(name="dob")
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dob;

    @Column(name="address")
    private String address;

    @Column(name="phone")
    @Size(min=10, max=10)
    private String phone;

    @Column(name="sex")
    private Character sex;
}
