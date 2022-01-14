package com.mediscreen.controller;

import com.mediscreen.beans.PatientBean;
import com.mediscreen.proxies.MicroservicePatientProxy;
import com.mediscreen.service.MediscreenService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MediscreenPatientController {

    @Autowired
    MediscreenService mediscreenService;

    @Autowired
    MicroservicePatientProxy patientProxy;

    private static final Logger logger = LogManager.getLogger(MediscreenPatientController.class);


    @RequestMapping("/patient")
    public List<PatientBean> getAllPatient() {
        logger.info("mediscreen getAllPAtient");
        return patientProxy.patientBeanList();
    }


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

    @PostMapping("/patient/delete")
    public ResponseEntity deletePatient(@RequestBody PatientBean patientBean) {
        logger.info("/patient/delete ");
        try{
            logger.debug("/patient/delete succes ");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(patientProxy.deletePatient(patientBean));
        } catch (Exception e) {
            logger.error("/patient/save error ", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
