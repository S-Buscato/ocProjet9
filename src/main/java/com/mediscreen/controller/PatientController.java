package com.mediscreen.controller;

import com.mediscreen.dto.PatientDto;
import com.mediscreen.exception.PatientAllreadyExists;
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
    public List<PatientDto> getAllPatient() {
        logger.info("getAllPatient");
        return patientService.findAll();
    }

    @RequestMapping("/test")
    public String test() {
        logger.info("test");
        return "Coucou";
    }

    @PostMapping("/patient/save")
    public ResponseEntity<PatientDto> addNewPatient(@RequestBody PatientDto patientDto) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(patientService.save(patientDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(patientDto);
        }
    }

}
