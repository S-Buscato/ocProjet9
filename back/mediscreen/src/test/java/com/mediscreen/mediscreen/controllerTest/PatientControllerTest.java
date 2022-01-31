package com.mediscreen.mediscreen.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.beans.NoteBean;
import com.mediscreen.beans.PatientBean;
import com.mediscreen.constant.Messages;
import com.mediscreen.constant.RiskControl;
import com.mediscreen.proxies.MicroserviceNoteProxy;
import com.mediscreen.proxies.MicroservicePatientProxy;
import com.mediscreen.service.MediscreenService;
import feign.FeignException;
import feign.Request;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
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
    MediscreenService mediscreenService;

    @MockBean
    MicroservicePatientProxy patientProxy;

    @MockBean
    MicroserviceNoteProxy noteProxy;

    PatientBean patient = new PatientBean();
    List<PatientBean> patientList = new ArrayList<>();
    NoteBean noteBean = new NoteBean();
    List<NoteBean> noteBeanList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        patient.setId(1L);
        patient.setPhone("0102030405");
        patient.setAddress("1, rue de nulle part");
        patient.setDob(new Date(01/01/1976));
        patient.setSex('M');
        patient.setFirstname("John");
        patient.setLastname("Doe");

        patientList.add(patient);

        noteBean.setNote("ma note");
        noteBean.setId(1L);
        noteBean.setPatientId(1L);
        noteBean.setCreatedDate(new Date(01/01/2022));
        noteBean.setCreatedDate(new Date(15/01/2022));

        noteBeanList.add(noteBean);


    }

    @Test
    @DisplayName("test calculateRisk patient Succes")
    void testcalculateRiskPatient() throws Exception {
        when(mediscreenService.calculateRisk(anyLong())).thenReturn("none");
        when(patientProxy.getPatient(anyLong())).thenReturn(ResponseEntity.status(HttpStatus.OK).body(patient));
        when(noteProxy.searchNote(anyLong())).thenReturn(ResponseEntity.status(HttpStatus.OK).body(noteBeanList));

        mockMvc.perform(get("/patient/calculateRisk/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("none")));

        verify(mediscreenService, times(1)).calculateRisk(anyLong());
    }

    @Test
    @DisplayName("test calculateRiskWithoutDouble patient Succes")
    void testcalculateRiskPatientWithoutDouble() throws Exception {
        when(mediscreenService.calulateRiskWithoutDouble(anyLong())).thenReturn(RiskControl.NONE);
        when(patientProxy.getPatient(anyLong())).thenReturn(ResponseEntity.status(HttpStatus.OK).body(patient));
        when(noteProxy.searchNote(anyLong())).thenReturn(ResponseEntity.status(HttpStatus.OK).body(noteBeanList));

        mockMvc.perform(get("/patient/calulateRiskWithoutDouble/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(RiskControl.NONE)));

        verify(mediscreenService, times(1)).calulateRiskWithoutDouble(anyLong());
    }

    @Test
    @DisplayName("test patient age Succes")
    void testcalculatePatientAge() throws Exception {
        when(mediscreenService.determinateAge(anyLong())).thenReturn(45);
        when(patientProxy.getPatient(anyLong())).thenReturn(ResponseEntity.status(HttpStatus.OK).body(patient));
        when(mediscreenService.calculAge(any())).thenReturn(45);

        mockMvc.perform(get("/patient/calculateAge/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(45)));

        verify(mediscreenService, times(1)).determinateAge(anyLong());
    }

    @Test
    @DisplayName("test get All patient Succes")
    void testGetAllPatient() throws Exception {
        when(patientProxy.patientBeanList()).thenReturn(ResponseEntity.status(HttpStatus.OK).body(patientList));

        mockMvc.perform(get("/patient")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstname", is(patientList.get(0).getFirstname())));

        verify(patientProxy, times(1)).patientBeanList();
    }

    @Test
    @DisplayName("test get patient Succes")
    void testGetPatient() throws Exception {
        when(patientProxy.getPatient(1L)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(patient));

        mockMvc.perform(get("/patient/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("body.firstname", is(patient.getFirstname())));

        verify(patientProxy, times(1)).getPatient(anyLong());
    }

    @Test
    @DisplayName("test get patient note not found exception")
    void testGetPatientNotFoundException() throws Exception {
        when(patientProxy.getPatient(anyLong())).thenThrow(FeignException.errorStatus(
                "getPatient",
                Response.builder()
                        .status(404)
                        .reason("Not Found")
                        .request(Request.create(
                                Request.HttpMethod.GET,
                                "/patient/1",
                                Collections.emptyMap(),
                                null,
                                StandardCharsets.UTF_8,
                                null))
                        .body(Messages.PATIENT_NOT_FOUND, StandardCharsets.UTF_8)//this field is required for construtor
                        .build()));

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", is(Messages.PATIENT_NOT_FOUND)))
                .andExpect(status().isNotFound());

        verify(patientProxy, times(1)).getPatient(anyLong());
    }

    @Test
    @DisplayName("test search patient Succes")
    void testSearchPatient() throws Exception {

        when(patientProxy.searchPatient(any(PatientBean.class))).thenReturn(ResponseEntity.status(HttpStatus.OK).body(patient));

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("body.firstname", is(patient.getFirstname())));

        verify(patientProxy, times(1)).searchPatient(any(PatientBean.class));
    }

    @Test
    @DisplayName("test save patient note Succes")
    void testSavePatient() throws Exception {

        when(patientProxy.addNewPatient(any(PatientBean.class))).thenReturn( ResponseEntity.status(HttpStatus.OK).body(patient));

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteBean))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())

                .andExpect(jsonPath("body.id", is(1)));

        verify(patientProxy, times(1)).addNewPatient(any(PatientBean.class));
    }

    @Test
    @DisplayName("test save patient Required Input Exception")
    void testSavePatientRequiredInputException() throws Exception {
        when(patientProxy.addNewPatient(any(PatientBean.class))).thenThrow(FeignException.errorStatus(
                "savePatient",
                Response.builder()
                        .status(400)
                        .reason("Bad Request")
                        .request(Request.create(
                                Request.HttpMethod.POST,
                                "/patient/save",
                                Collections.emptyMap(),
                                null,
                                StandardCharsets.UTF_8,
                                null))
                        .body(Messages.REQUIRED_INPUT, StandardCharsets.UTF_8)//this field is required for construtor
                        .build()));

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteBean))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", is(Messages.REQUIRED_INPUT)))
                .andExpect(status().isBadRequest());

        verify(patientProxy, times(1)).addNewPatient(any(PatientBean.class));
    }


    @Test
    @DisplayName("test delete patient succes")
    void testDeletePatient() throws Exception {

        when(patientProxy.deletePatient(anyLong())).thenReturn(ResponseEntity.status(HttpStatus.OK).body(1L));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/patient/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("body", is(1)))
                        .andExpect(status().isAccepted());

        verify(patientProxy, times(1)).deletePatient(anyLong());
    }

    @Test
    @DisplayName("test delete patient Exception")
    void testDeletePatientException() throws Exception {
        when(patientProxy.deletePatient(anyLong())).thenThrow(FeignException.errorStatus(
                "deletePatient",
                Response.builder()
                        .status(400)
                        .reason("Bad Request")
                        .request(Request.create(
                                Request.HttpMethod.DELETE,
                                "/patient/delete/1",
                                Collections.emptyMap(),
                                null,
                                StandardCharsets.UTF_8,
                                null))
                        .body(Messages.PATIENT_NOT_FOUND, StandardCharsets.UTF_8)//this field is required for construtor
                        .build()));

        mockMvc.perform(MockMvcRequestBuilders.delete("/patient/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", is(Messages.PATIENT_NOT_FOUND)))
                .andExpect(status().isBadRequest());

        verify(patientProxy, times(1)).deletePatient(anyLong());
    }
}
