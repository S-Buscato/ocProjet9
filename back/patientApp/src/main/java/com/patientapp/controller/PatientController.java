package com.patientapp.controller;

import com.patientapp.dto.PatientDto;
import com.patientapp.exception.PatientAllreadyExists;
import com.patientapp.exception.PatientNotFoundException;
import com.patientapp.service.PatientService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor(onConstructor =  @__(@Autowired))
public class PatientController {

    @Autowired PatientService patientService;
    private static final Logger logger = LogManager.getLogger(PatientController.class);


    @ApiOperation(value = "Récupère tous les patients")
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

    @ApiOperation(value = "Récupère un patient grâce à son ID")
    @GetMapping("/patient/{id}")
    public ResponseEntity getPatient(@PathVariable Long id) {
        logger.info("getPatient/patient/{id} " + id);
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

    @ApiOperation(value = "Recherche un patient")
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

    @ApiOperation(value = "Enregistre un patient")
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

    @ApiOperation(value = "Efface un patient")
    @DeleteMapping("/patient/delete/{id}")
    public ResponseEntity deletePatient(@PathVariable Long id) {
        logger.info("/patient/delete/{id} " + id);
        try{
            logger.debug("/patient/delete succes ");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(patientService.delete(id));
        } catch (PatientNotFoundException e) {
            logger.error("/patient/save error ", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("/patient/save error ", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
