package com.patientapp.patientapp.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patientapp.dto.PatientDto;
import com.patientapp.exception.PatientAllreadyExists;
import com.patientapp.exception.PatientNotFoundException;
import com.patientapp.model.Patient;
import com.patientapp.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor =  @__(@Autowired))
public class PatientControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @MockBean
    PatientService patientService;


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
    @DisplayName("test get All patient Succes")
    void testGetAllPatient() throws Exception {
        when(patientService.findAll()).thenReturn(patientDtos);

        mockMvc.perform(get("/patient")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstname", is(patientDto.getFirstname())));

        verify(patientService, times(1)).findAll();
    }

    @Test
    @DisplayName("test get patient Succes")
    void testGetPatient() throws Exception {
        patientDto.setId(1L);

        when(patientService.findById(1L)).thenReturn(patientDto);

        mockMvc.perform(get("/patient/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", is(patientDto.getFirstname())));

        verify(patientService, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("test get patient not found")
    void testGetPatientNotFound() throws Exception {
        patientDto.setId(1L);
        PatientNotFoundException e = new PatientNotFoundException();
        when(patientService.findById(1L)).thenThrow(e);

        mockMvc.perform(get("/patient/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(patientService, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("test search patient not found exception")
    void testSearchPatientNotFoundException() throws Exception {
        PatientNotFoundException e = new PatientNotFoundException();

        when(patientService.findByFamily(anyString(),anyString())).thenThrow(e);

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(patientService, times(1)).findByFamily(anyString(),anyString());
    }

    @Test
    @DisplayName("test search patient Succes")
    void testSearchPatient() throws Exception {

        when(patientService.findByFamily(anyString(),anyString())).thenReturn(patientDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", is(patientDto.getFirstname())));

        verify(patientService, times(1)).findByFamily(anyString(),anyString());
    }

    @Test
    @DisplayName("test save patient Succes")
    void testSavePatient() throws Exception {

        when(patientService.save(any(PatientDto.class))).thenReturn(patientDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.firstname", is(patientDto.getFirstname())));

        verify(patientService, times(1)).save(any(PatientDto.class));
    }

    @Test
    @DisplayName("test save patient exists exeption")
    void testSavePatientExistsException() throws Exception {
        PatientAllreadyExists e = new PatientAllreadyExists();

        when(patientService.save(any(PatientDto.class))).thenThrow(e);

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        verify(patientService, times(1)).save(any(PatientDto.class));
    }

    @Test
    @DisplayName("test  delete patient not found exception")
    void testDeletePatientNotFoundException() throws Exception {
        PatientNotFoundException e = new PatientNotFoundException();

        when(patientService.delete(anyLong())).thenThrow(e);

        mockMvc.perform(MockMvcRequestBuilders.delete("/patient/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(patientService, times(1)).delete(anyLong());
    }

    @Test
    @DisplayName("test delete patient succes")
    void testDeletePatient() throws Exception {

        when(patientService.delete(anyLong())).thenReturn(1L);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/patient/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isAccepted());

        verify(patientService, times(1)).delete(anyLong());
    }
}
