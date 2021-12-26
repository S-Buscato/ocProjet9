package com.mediscreen.service.Iservice;

import com.mediscreen.dto.PatientDto;
import com.mediscreen.exception.PatientAllreadyExists;

import java.util.List;
import java.util.Optional;

public interface IPatientService {

    PatientDto findById(Long id);
    PatientDto save(PatientDto patientDto) throws PatientAllreadyExists;
    List<PatientDto> findAll();
    PatientDto findByFamily(String firstname, String lastname);

}
