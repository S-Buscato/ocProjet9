package com.mediscreen.service;

import com.mediscreen.dto.PatientDto;
import com.mediscreen.dto.PatientMapper;
import com.mediscreen.exception.PatientAllreadyExists;
import com.mediscreen.model.Patient;
import com.mediscreen.repositories.PatientRepository;
import com.mediscreen.service.Iservice.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PatientService implements IPatientService {

    @Autowired
    PatientRepository patientRepository;


    @Override
    public PatientDto findById(Long id) {
        return PatientMapper.INSTANCE.patientToPatientDTO(patientRepository.findById(id).get());
    }

    @Override
    public PatientDto save(PatientDto patientDto){
        Patient patient = new Patient();
        if(patientRepository.findByFamily(patientDto.getFirstname(), patientDto.getLastname()).isPresent()) {
            patient = patientRepository.findByFamily(patientDto.getFirstname(), patientDto.getLastname()).get();
            patient.setFirstname(patientDto.getFirstname());
            patient.setLastname(patientDto.getLastname());
            patient.setAddress(patientDto.getAddress());
            patient.setDob(patientDto.getDob());
            patient.setSex(patientDto.getSex());
            patient.setPhone(patientDto.getPhone());
            patient.setAddress(patientDto.getAddress());
        }
        return PatientMapper.INSTANCE.patientToPatientDTO(patientRepository.save(patient));
    }

    @Override
    public List<PatientDto> findAll() {
        List<Patient> patients = StreamSupport.stream(patientRepository.findAll().spliterator(),
                false).collect(Collectors.toList());

        return PatientMapper.INSTANCE.patientToPatientDTO(patients);
    }

    @Override
    public PatientDto findByFamily(String firstname, String lastname) {
        Patient patient = new Patient();
        if(patientRepository.findByFamily(firstname, lastname).isPresent()) {
            patient = patientRepository.findByFamily(firstname, lastname).get();
        }
        return PatientMapper.INSTANCE.patientToPatientDTO(patient);
    }
}
