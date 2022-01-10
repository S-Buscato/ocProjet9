package com.patientapp.controller;

import com.patientapp.dto.PatientDto;
import com.patientapp.exception.PatientAllreadyExists;
import com.patientapp.exception.PatientNotFoundException;
import com.patientapp.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor(onConstructor =  @__(@Autowired))
public class PatientController {

    @Autowired PatientService patientService;
    private static final Logger logger = LogManager.getLogger(PatientController.class);



    @GetMapping("/patient")
    public ResponseEntity getAllPatient() {
        logger.info("getAllPatient");
        try {
            logger.debug("getAllPatient succes");
            return ResponseEntity.status(HttpStatus.OK).body(patientService.findAll());
        } catch (Exception e) {
            logger.error("getAllPatient error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity getPatient(@PathVariable Long id) {
        logger.info("/patient/{id} " + id);
        try {
            logger.debug( "/patient/{id} succes");
            return ResponseEntity.status(HttpStatus.OK).body(patientService.findById(id));
        } catch (PatientNotFoundException e) {
            logger.error("/patient/{id} error ", e.getMessage());
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("/patient/{id} error ", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/patient/search")
    public ResponseEntity searchPatient(@RequestBody PatientDto patientDto) {
        logger.info("/patient/search ", patientDto.getFirstname() + " " + patientDto.getLastname());
        try{
            logger.debug("/patient/search succes ");
            return ResponseEntity.status(HttpStatus.OK).body(patientService.findByFamily(patientDto.getFirstname(), patientDto.getLastname()));
        } catch (PatientNotFoundException e) {
            logger.error("/patient/search error ", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("/patient/search error ", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/patient/save")
    public ResponseEntity addNewPatient(@RequestBody PatientDto patientDto) {
        logger.info("/patient/save ");
        try{
            logger.debug("/patient/save succes ");
            return ResponseEntity.status(HttpStatus.CREATED).body(patientService.save(patientDto));
        } catch (PatientAllreadyExists e) {
            logger.error("/patient/save error ", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            logger.error("/patient/save error ", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/patient/delete")
    public ResponseEntity deletePatient(@RequestBody PatientDto patientDto) {
        logger.info("/patient/delete ");
        try{
            logger.debug("/patient/delete succes ");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(patientService.delete(patientDto));
        } catch (PatientNotFoundException e) {
            logger.error("/patient/save error ", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("/patient/save error ", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
