package com.patientapp.patientapp.serviceTest;

import com.patientapp.constant.Messages;
import com.patientapp.dto.PatientDto;
import com.patientapp.exception.PatientAllreadyExists;
import com.patientapp.exception.PatientNotFoundException;
import com.patientapp.exception.RequiredInputException;
import com.patientapp.model.Patient;
import com.patientapp.repositories.PatientRepository;
import com.patientapp.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = PatientService.class)
@RequiredArgsConstructor(onConstructor =  @__(@Autowired))
public class PatientServiceTest {

    private final  PatientService patientService;

    @MockBean
    PatientRepository patientRepository;

    Patient patient = new Patient();
    List<Patient> patientList = new ArrayList<>();

    PatientDto patientDto = new PatientDto();
    List<PatientDto> patientDtos = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        patientDto.setPhone("0102030405");
        patientDto.setAddress("1, rue de nulle part");
        patientDto.setDob(new Date(01/01/1976));
        patientDto.setSex('M');
        patientDto.setFirstname("John");
        patientDto.setLastname("Doe");

        patient.setId(1);
        patient.setPhone("1111111111");
        patient.setAddress("2, avenue d'ailleurs");
        patient.setDob(new Date(12/12/1981));
        patient.setSex('F');
        patient.setFirstname("Ginette");
        patient.setLastname("Doe");

        patientList.add(patient);
        patientDtos.add(patientDto);
    }

    @Test
    @DisplayName("test find Patient by id")
    void testFindPatientById() throws PatientNotFoundException {
        when(patientRepository.findById(1L)).thenReturn(Optional.ofNullable(patient));

        PatientDto patientDto1 = patientService.findById(1L);

        Assertions.assertEquals("Doe", patientDto1.getLastname());
        verify(patientRepository, times(2)).findById(1L);
    }

    @Test
    @DisplayName("test find Patient by id not found exception")
    void testFindPatientByIdNotFoundException()  {
        when(patientRepository.findById(1L)).thenReturn(Optional.ofNullable(null));

        PatientNotFoundException exception = Assertions.assertThrows(PatientNotFoundException.class, () -> {
            patientService.findById(1L);
        });

        Assertions.assertEquals(Messages.PATIENT_NOT_FOUND,exception.getMessage() );
        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("test find All Patient")
    void testFindAllPatient() {
        when(patientRepository.findAll()).thenReturn(patientList);

        List<PatientDto> patientDtos1 = patientService.findAll();

        Assertions.assertEquals(1, patientDtos1.size());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("test find Patient by familly")
    void testFindPatientByFamilly() throws PatientNotFoundException {
        when(patientRepository.findByFamily(anyString(),anyString())).thenReturn(Optional.ofNullable(patient));

        PatientDto patientDto1 = patientService.findByFamily("Ginette", "Doe");

        Assertions.assertEquals("Ginette", patientDto1.getFirstname());
        verify(patientRepository, times(2)).findByFamily(anyString(),anyString());
    }

    @Test
    @DisplayName("test save Patient ")
    void testSavePatient() throws PatientAllreadyExists, PatientNotFoundException, RequiredInputException {
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        PatientDto patientDto1 = patientService.save(patientDto);

        Assertions.assertEquals("Ginette", patientDto1.getFirstname());
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    @DisplayName("test save Patient All ready exists Exception ")
    void testSavePatientAllReadyExistsException() throws PatientAllreadyExists, PatientNotFoundException {
        when(patientRepository.findByFamily(anyString(),anyString())).thenReturn(Optional.ofNullable(patient));

        PatientAllreadyExists exception = Assertions.assertThrows(PatientAllreadyExists.class, () -> {
            patientService.save(patientDto);;
        });

        Assertions.assertEquals(Messages.PATIENT_ALREADY_EXISTS,exception.getMessage() );
        verify(patientRepository, times(1)).findByFamily(anyString(),anyString());

    }

    @Test
    @DisplayName("test update Patient ")
    void testUpdatePatient() throws PatientAllreadyExists, PatientNotFoundException, RequiredInputException {
        patientDto.setId(1L);
        patientDto.setFirstname("John");
        patient.setFirstname("John");

        when(patientRepository.save(any(Patient.class))).thenReturn(patient);
        when(patientRepository.findById(1L)).thenReturn(Optional.ofNullable(patient));

        PatientDto patientDto1 = patientService.save(patientDto);

        Assertions.assertEquals("John", patientDto1.getFirstname());
        verify(patientRepository, times(1)).save(any(Patient.class));
        verify(patientRepository, times(2)).findById(1L);
    }

 @Test
    @DisplayName("test delete Patient")
    void testDeletePatient() throws PatientNotFoundException {
        patientDto.setId(1L);

        when(patientRepository.findById(1L)).thenReturn(Optional.ofNullable(patient));

        patientService.delete(1L);

        verify(patientRepository, times(1)).deleteById(anyLong());
        verify(patientRepository, times(1)).findById(1L);
    }
}
