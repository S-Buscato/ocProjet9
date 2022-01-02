package com.mediscreen.service.Iservice;

import com.mediscreen.dto.PatientDto;
import com.mediscreen.exception.PatientAllreadyExists;
import com.mediscreen.exception.PatientNotFoundException;

import java.util.List;

public interface IPatientService {

    PatientDto findById(Long id);
    PatientDto save(PatientDto patientDto) throws PatientAllreadyExists;
    List<PatientDto> findAll();
    PatientDto findByFamily(String firstname, String lastname);
    PatientDto delete(PatientDto PatientDto) throws PatientNotFoundException;

}
