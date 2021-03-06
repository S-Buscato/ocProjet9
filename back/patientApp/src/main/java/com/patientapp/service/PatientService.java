package com.patientapp.service;

import com.patientapp.dto.PatientDto;
import com.patientapp.dto.PatientMapper;
import com.patientapp.exception.PatientAllreadyExists;
import com.patientapp.exception.PatientNotFoundException;
import com.patientapp.exception.RequiredInputException;
import com.patientapp.model.Patient;
import com.patientapp.repositories.PatientRepository;
import com.patientapp.service.Iservice.IPatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor(onConstructor =  @__(@Autowired))
public class PatientService implements IPatientService {

    private final PatientRepository patientRepository;


    @Override
    public PatientDto findById(Long id) throws PatientNotFoundException{
        if(patientRepository.findById(id).isPresent()) {
            return PatientMapper.INSTANCE.patientToPatientDTO(patientRepository.findById(id).get());
        } else {
            throw  new PatientNotFoundException();
        }
    }

    @Override
    public PatientDto save(PatientDto patientDto) throws PatientAllreadyExists, PatientNotFoundException, RequiredInputException {
        if(patientDto.getFirstname().isEmpty() || patientDto.getLastname().isEmpty() || patientDto.getDob() == null || patientDto.getSex() == null) {
            throw new RequiredInputException();
        }
        if(patientDto.getId() == 0 && patientRepository.findByFamily(patientDto.getFirstname(), patientDto.getLastname()).isPresent()) {
            throw new PatientAllreadyExists();
        }
        Patient patient = new Patient();

        if(patientDto.getId() != 0) {
            patient = PatientMapper.INSTANCE.patientDTOtoPatient(findById(patientDto.getId()));
        }
        patient.setFirstname(patientDto.getFirstname());
        patient.setLastname(patientDto.getLastname());
        patient.setAddress(patientDto.getAddress());
        patient.setDob(patientDto.getDob());
        patient.setSex(patientDto.getSex());
        patient.setPhone(patientDto.getPhone());

        return PatientMapper.INSTANCE.patientToPatientDTO(patientRepository.save(patient));
    }

    @Override
    public List<PatientDto> findAll() {
        List<Patient> patients = StreamSupport.stream(patientRepository.findAll().spliterator(),
                false).collect(Collectors.toList());

        return PatientMapper.INSTANCE.patientToPatientDTO(patients);
    }

    @Override
    public PatientDto findByFamily(String firstname, String lastname) throws PatientNotFoundException {
        Patient patient;
        if(patientRepository.findByFamily(firstname, lastname).isPresent()) {
            patient = patientRepository.findByFamily(firstname, lastname).get();
        } else {
            throw new PatientNotFoundException();
        }
        return PatientMapper.INSTANCE.patientToPatientDTO(patient);
    }

    @Override
    public long delete(Long id) throws PatientNotFoundException {
        if(patientRepository.findById(id).isPresent()) {
            patientRepository.deleteById(id);;
            return id;
        } else {
            throw new PatientNotFoundException();
        }
    }
}
