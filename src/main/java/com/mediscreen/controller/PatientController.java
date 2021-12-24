package com.mediscreen.controller;

import com.mediscreen.model.Patient;
import com.mediscreen.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {

    @Autowired PatientService patientService;
    static Logger logger = Logger.getLogger(String.valueOf(PatientController.class));


    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/patient")
    public List<Patient> getAllPatient() {
        logger.info("getAllPatient");
        return patientService.findAll();
    }

    @RequestMapping("/test")
    public String test() {
        logger.info("test");
        return "Coucou";
    }

    @PostMapping("/patients/add")
    public ResponseEntity<Patient> addNewPatient(@RequestBody Patient patient) {
        System.out.println(patient);
        if(patientService.findByFamily(patient.getFirstname(), patient.getLastname()).isPresent()){
            return  ResponseEntity.status(HttpStatus.CONFLICT).body(patient);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(patientService.save(patient));
    }

    @PostMapping("/patients/update")
    public ResponseEntity<Patient> updatePatient(@RequestBody Patient patient) {
        System.out.println(patient);
        if(!patientService.findByFamily(patient.getFirstname(), patient.getLastname()).isPresent()){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(patient);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(patientService.save(patient));
    }

}
