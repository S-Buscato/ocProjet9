package com.mediscreen.service;

import com.mediscreen.model.Patient;
import com.mediscreen.repositories.PatientRepository;
import com.mediscreen.service.Iservice.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PatientService implements IPatientService {

    @Autowired
    PatientRepository patientRepository;

    @Override
    public Patient findById(Long id) {
        return patientRepository.findById(id).get();
    }

    @Override
    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public List<Patient> findAll() {
        List<Patient> patients = StreamSupport.stream(patientRepository.findAll().spliterator(),
                false).collect(Collectors.toList());

        return patients;
    }

    @Override
    public Optional<Patient> findByFamily(String firstname, String lastname) {
        return patientRepository.findByFamily(firstname, lastname);
    }
}
