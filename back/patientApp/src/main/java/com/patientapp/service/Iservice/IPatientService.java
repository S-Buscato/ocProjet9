package com.patientapp.service.Iservice;

import com.patientapp.dto.PatientDto;
import com.patientapp.exception.PatientAllreadyExists;
import com.patientapp.exception.PatientNotFoundException;
import com.patientapp.exception.RequiredInputException;

import java.util.List;

public interface IPatientService {

    PatientDto findById(Long id) throws PatientNotFoundException;
    PatientDto save(PatientDto patientDto) throws PatientAllreadyExists, PatientNotFoundException, RequiredInputException;
    List<PatientDto> findAll();
    PatientDto findByFamily(String firstname, String lastname) throws PatientNotFoundException;
    long delete(Long patientId) throws PatientNotFoundException;

}
