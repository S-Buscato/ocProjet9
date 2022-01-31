package com.mediscreen.mediscreen.serviceTest;

import com.mediscreen.beans.NoteBean;
import com.mediscreen.beans.PatientBean;
import com.mediscreen.constant.RiskControl;
import com.mediscreen.proxies.MicroserviceNoteProxy;
import com.mediscreen.proxies.MicroservicePatientProxy;
import com.mediscreen.service.MediscreenService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

@RequiredArgsConstructor(onConstructor =  @__(@Autowired))
@SpringBootTest(classes = MediscreenService.class)
public class ServiceTest {

    private final  MediscreenService mediscreenService;

    @MockBean
    MicroserviceNoteProxy noteProxy;

    @MockBean
    MicroservicePatientProxy patientProxy;



    PatientBean patient = new PatientBean();
    NoteBean noteBean = new NoteBean();
    NoteBean noteBean2 = new NoteBean();

    List<NoteBean> noteBeanList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        patient.setId(1L);
        patient.setPhone("0102030405");
        patient.setAddress("1, rue de nulle part");
        patient.setDob(new Date(01 / 01 / 1976));
        patient.setSex('M');
        patient.setFirstname("John");
        patient.setLastname("Doe");

        noteBean.setId(1L);
        noteBean.setNote("fumeur,Anormal,Cholestérol");
        noteBean.setPatientId(1L);
        noteBeanList.add(noteBean);

        noteBean2.setId(2L);
        noteBean2.setNote("fumeur, poids, taille");
        noteBean2.setPatientId(1L);
        noteBeanList.add(noteBean2);
    }

    @Test
    @DisplayName("test calcul age success")
    void testCalculAge() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        int Age =  Period.between(LocalDate.parse(simpleDateFormat.format(patient.getDob()), DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalDate.now()).getYears();
        int calculeAge = mediscreenService.calculAge(patient.getDob());
        Assertions.assertEquals(Age, calculeAge);
    }

    @Test
    @DisplayName("test determinate Age success")
    void testDeterminateAge() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        int Age =  Period.between(LocalDate.parse(simpleDateFormat.format(patient.getDob()), DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalDate.now()).getYears();

        when(patientProxy.getPatient(anyLong())).thenReturn(ResponseEntity.status(HttpStatus.OK).body(patient));
        int calculeAge = mediscreenService.determinateAge(patient.getId());
        Assertions.assertEquals(Age, calculeAge);
        verify(patientProxy, times(1)).getPatient(anyLong());

    }

    @Test
    @DisplayName("test Determinate None Risk age > 30 success")
    void testDeterminateNoneRisk() {
        String risk = mediscreenService.determinateRisk(31,1,'M');
        Assertions.assertEquals( RiskControl.NONE, risk);
    }

    @Test
    @DisplayName("test Determinate Borderline Risk age > 30 success")
    void testDeterminateBorderlineRisk() {
        String risk = mediscreenService.determinateRisk(31,2,'M');
        Assertions.assertEquals(RiskControl.BORDERLINE, risk);
    }

    @Test
    @DisplayName("test Determinate InDanger Risk age > 30 success")
    void testDeterminateInDangerRisk() {
        String risk = mediscreenService.determinateRisk(31,6,'M');
        Assertions.assertEquals( RiskControl.IN_DANGER, risk);
    }

    @Test
    @DisplayName("test Determinate Early onset Risk age > 30 success")
    void testDeterminateEarlyOnsetRisk() {
        String risk = mediscreenService.determinateRisk(31,8,'M');
        Assertions.assertEquals( RiskControl.EARLY_ONSET, risk);
    }


    @Test
    @DisplayName("test Determinate None Risk M < 30 success")
    void testDeterminateNoneRiskM() {
        String risk = mediscreenService.determinateRisk(29,2,'M');
        Assertions.assertEquals(RiskControl.NONE, risk);
    }

    @Test
    @DisplayName("test Determinate InDanger Risk M < 30 success")
    void testDeterminateInDangerRiskM() {
        String risk = mediscreenService.determinateRisk(29,3,'M');
        Assertions.assertEquals( RiskControl.IN_DANGER, risk);
    }

    @Test
    @DisplayName("test Determinate Early onset Risk M <  30 success")
    void testDeterminateEarlyOnsetRiskM() {
        String risk = mediscreenService.determinateRisk(29,5,'M');
        Assertions.assertEquals( RiskControl.EARLY_ONSET, risk);
    }

    @Test
    @DisplayName("test Determinate None Risk F < 30 success")
    void testDeterminateNoneRiskF() {
        String risk = mediscreenService.determinateRisk(29,3,'F');
        Assertions.assertEquals(RiskControl.NONE, risk);
    }

    @Test
    @DisplayName("test Determinate InDanger Risk F < 30 success")
    void testDeterminateInDangerRiskF() {
        String risk = mediscreenService.determinateRisk(29,4,'F');
        Assertions.assertEquals( RiskControl.IN_DANGER, risk);
    }

    @Test
    @DisplayName("test Determinate Early onset Risk F <  30 success")
    void testDeterminateEarlyOnsetRiskF() {
        String risk = mediscreenService.determinateRisk(29,7,'F');
        Assertions.assertEquals( RiskControl.EARLY_ONSET, risk);
    }


    @Test
    @DisplayName("test calculate Risk success")
    void testCalculateRisk() {
        when(patientProxy.getPatient(anyLong())).thenReturn(ResponseEntity.status(HttpStatus.OK).body(patient));
        when(noteProxy.searchNote(anyLong())).thenReturn( ResponseEntity.status(HttpStatus.OK).body(noteBeanList));

        String risk = mediscreenService.calculateRisk(patient.getId());
        Assertions.assertEquals( RiskControl.IN_DANGER, risk);
    }

    @Test
    @DisplayName("test calculate Risk sans double success")
    void testCalculateRiskWithoutDouble() {
        when(patientProxy.getPatient(anyLong())).thenReturn(ResponseEntity.status(HttpStatus.OK).body(patient));
        when(noteProxy.searchNote(anyLong())).thenReturn(ResponseEntity.status(HttpStatus.OK).body(noteBeanList));

        String risk = mediscreenService.calulateRiskWithoutDouble(patient.getId());
        Assertions.assertEquals( RiskControl.BORDERLINE, risk);
    }
}
