package com.mediscreen.controller;

import com.mediscreen.beans.PatientBean;
import com.mediscreen.proxies.MicroservicePatientProxy;
import com.mediscreen.service.MediscreenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Api("API pour les opérations CRUD sur les patients.")
@CrossOrigin(origins = "http://localhost:4200")
public class MediscreenPatientController {

    @Autowired
    MediscreenService mediscreenService;

    @Autowired
    MicroservicePatientProxy patientProxy;

    private static final Logger logger = LogManager.getLogger(MediscreenPatientController.class);


    @ApiOperation(value = "Récupère tous les patients")
    @GetMapping("/patient")
    public List<PatientBean> getAllPatient() {
        logger.info("mediscreen getAllPAtient");
        return patientProxy.patientBeanList();
    }


    @ApiOperation(value = "Calcule le risque de diabète d'un patient grâce à son ID")
    @GetMapping("/patient/calculateRisk/{id}")
    public ResponseEntity calculateRisk(@PathVariable Long id) {
        logger.info("calculate risk " + id);
        try {
            logger.debug( "/calculateRisk/{id} succes");
            return ResponseEntity.status(HttpStatus.OK).body(mediscreenService.calculateRisk(id));
        } catch (Exception e) {
            logger.error("/calculateRisk/{id} error ", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @ApiOperation(value = "Calcule l'âge d'un patient grâce à son ID")
    @GetMapping("/patient/calculateAge/{id}")
    public ResponseEntity calculAge(@PathVariable Long id) {
        logger.info("calculate age " + id);
        try {
            logger.debug( "/calculateAge/{id} succes");
            return ResponseEntity.status(HttpStatus.OK).body(mediscreenService.determinateAge(id));
        } catch (Exception e) {
            logger.error("/calculateAge/{id} error ", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @ApiOperation(value = "Récupère un patient grâce à son ID")
    @GetMapping("/patient/{id}")
    public ResponseEntity getPatient(@PathVariable Long id) {
        logger.info("/patient/{id} " + id);
        try {
            logger.debug( "/patient/{id} succes");
            return ResponseEntity.status(HttpStatus.OK).body(patientProxy.getPatient(id));
        } catch (Exception e) {
            logger.error("/patient/{id} error ", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @ApiOperation(value = "Recherche un patient")
    @PostMapping("/patient/search")
    public ResponseEntity searchPatient(@RequestBody PatientBean patientBean) {
        logger.info("/patient/search ", patientBean.getFirstname() + " " + patientBean.getLastname());
        try{
            logger.debug("/patient/search succes ");
            return ResponseEntity.status(HttpStatus.OK).body(patientProxy.searchPatient(patientBean));
        } catch (Exception e) {
            logger.error("/patient/search error ", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @ApiOperation(value = "Enregistre un patient")
    @PostMapping("/patient/save")
    public ResponseEntity addNewPatient(@RequestBody PatientBean patientBean) {
        logger.info("/patient/save ");
        try{
            logger.debug("/patient/save succes ");
            return ResponseEntity.status(HttpStatus.CREATED).body(patientProxy.addNewPatient(patientBean));
        } catch (Exception e) {
            logger.error("/patient/save error ", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @ApiOperation(value = "Efface un patient grâce à son id")
    @DeleteMapping("/patient/delete/{id}")
    public ResponseEntity deletePatient(@PathVariable Long id) {
        logger.info("/patient/delete/{id} " + id);
        try{
            logger.debug("/patient/delete/id succes ");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(patientProxy.deletePatient(id));
        } catch (Exception e) {
            logger.error("/patient/delete error ", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
