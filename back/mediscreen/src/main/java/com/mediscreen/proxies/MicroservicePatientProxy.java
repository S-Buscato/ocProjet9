package com.mediscreen.proxies;

import com.mediscreen.beans.PatientBean;
import com.mediscreen.exception.PatientNotFoundException;
import com.mediscreen.exception.RequiredInputException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservice-patient", url = "localhost:9002")
/*
@FeignClient(name = "microservice-patient", url = "PatientApp:9002")
*/
public interface MicroservicePatientProxy {

    @GetMapping("/patient")
    ResponseEntity<List<PatientBean>> patientBeanList();

    @GetMapping("/patient/{id}")
    ResponseEntity<PatientBean> getPatient(@PathVariable Long id);

    @PostMapping("/patient/search")
    ResponseEntity<PatientBean> searchPatient(@RequestBody PatientBean patientBean);

    @PostMapping("/patient/save")
    ResponseEntity<PatientBean> addNewPatient(@RequestBody PatientBean patientBean);

    @DeleteMapping("/patient/delete/{id}")
    ResponseEntity<Long> deletePatient(@PathVariable Long id);

}
