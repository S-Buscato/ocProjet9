package com.mediscreen.proxies;

import com.mediscreen.beans.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
@FeignClient(name = "microservice-patient", url = "PatientApp:9002")
*/
@FeignClient(name = "microservice-patient", url = "localhost:9002")
public interface MicroservicePatientProxy {

    @GetMapping("/patient")
    List<PatientBean> patientBeanList();

    @GetMapping("/patient/{id}")
    PatientBean getPatient(@PathVariable Long id);

    @PostMapping("/patient/search")
    PatientBean searchPatient(@RequestBody PatientBean patientBean);

    @PostMapping("/patient/save")
    PatientBean addNewPatient(@RequestBody PatientBean patientBean);

    @DeleteMapping("/patient/delete/{id}")
    Long deletePatient(@PathVariable Long id);

}
