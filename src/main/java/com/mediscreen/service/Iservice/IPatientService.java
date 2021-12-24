package com.mediscreen.service.Iservice;

import com.mediscreen.model.Patient;

import java.util.List;
import java.util.Optional;

public interface IPatientService {

    Patient findById(Long id);
    Patient save(Patient patient);
    List<Patient> findAll();
    Optional<Patient> findByFamily(String firstname, String lastname);

}
