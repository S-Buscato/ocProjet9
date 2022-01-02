package com.mediscreen.controller;

import com.mediscreen.dto.PatientDto;
import com.mediscreen.exception.PatientAllreadyExists;
import com.mediscreen.exception.PatientNotFoundException;
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


    @GetMapping("/patient")
    public List<PatientDto> getAllPatient() {
        logger.info("getAllPatient");
        return patientService.findAll();
    }

    @GetMapping("/patient/{id}")
    public PatientDto getPatient(@PathVariable Long id) {
        logger.info("getPatient");
        return patientService.findById(id);
    }

    @PostMapping("/patient/search")
    public ResponseEntity searchPatient(@RequestBody PatientDto patientDto) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(patientService.findByFamily(patientDto.getFirstname(), patientDto.getLastname()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/patient/save")
    public ResponseEntity addNewPatient(@RequestBody PatientDto patientDto) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(patientService.save(patientDto));
        } catch (PatientAllreadyExists e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/patient/delete")
    public ResponseEntity deletePatient(@RequestBody PatientDto patientDto) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(patientService.delete(patientDto));
        } catch (PatientNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}