package com.patientapp.patientapp.serviceTest.IT;

import com.patientapp.dto.PatientDto;
import com.patientapp.exception.PatientAllreadyExists;
import com.patientapp.exception.PatientNotFoundException;
import com.patientapp.exception.RequiredInputException;
import com.patientapp.repositories.PatientRepository;
import com.patientapp.service.PatientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class PatientServiceIT {

    @Autowired
    PatientService patientService;

    @Autowired
    PatientRepository patientRepository;

    @Test
    @DisplayName("test add one Patient Success")
    void testSavePatient() throws PatientAllreadyExists, PatientNotFoundException, RequiredInputException {

        PatientDto patientDto = new PatientDto();
        patientDto.setPhone("0102030405");
        patientDto.setAddress("1, rue de nulle part");
        patientDto.setDob(new Date(01/01/1976));
        patientDto.setSex('M');
        patientDto.setFirstname("John");
        patientDto.setLastname("Doe");

        PatientDto patientSaved = patientService.save(patientDto);
        Assertions.assertEquals(patientSaved.getFirstname(), patientDto.getFirstname());

        Assertions.assertTrue(patientRepository.findById(patientSaved.getId()).isPresent());
    }
}
