package com.mediscreen.proxies;

import com.mediscreen.beans.PatientBean;
import com.patientapp.dto.PatientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

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

    @PostMapping("/patient/delete")
    PatientBean deletePatient(@RequestBody PatientBean patientBean);

}
