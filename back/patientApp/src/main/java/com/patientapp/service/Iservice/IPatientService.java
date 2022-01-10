package com.patientapp.service.Iservice;

import com.patientapp.dto.PatientDto;
import com.patientapp.exception.PatientAllreadyExists;
import com.patientapp.exception.PatientNotFoundException;

import java.util.List;

public interface IPatientService {

    PatientDto findById(Long id) throws PatientNotFoundException;
    PatientDto save(PatientDto patientDto) throws PatientAllreadyExists, PatientNotFoundException;
    List<PatientDto> findAll();
    PatientDto findByFamily(String firstname, String lastname) throws PatientNotFoundException;
    PatientDto delete(PatientDto PatientDto) throws PatientNotFoundException;

}
