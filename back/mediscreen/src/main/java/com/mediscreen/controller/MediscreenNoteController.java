package com.mediscreen.controller;


import com.mediscreen.beans.NoteBean;
import com.mediscreen.proxies.MicroserviceNoteProxy;
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
public class MediscreenNoteController {

    @Autowired
    MediscreenService mediscreenService;

    @Autowired
    MicroserviceNoteProxy noteProxy;

    private static final Logger logger = LogManager.getLogger(MediscreenNoteController.class);

    @GetMapping("/note/{id}")
    public ResponseEntity getNote(@PathVariable Long id) {
        logger.info("mediscreen getNote " + id);
        try{
            NoteBean noteBean = noteProxy.getNote(id);
            return ResponseEntity.status(HttpStatus.OK).body(noteBean);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping("/note/search/{patientId}")
    public ResponseEntity searchNote(@PathVariable Long patientId){
        logger.info("mediscreen searchNote patient" + patientId);
        try{
            List<NoteBean> noteBean = noteProxy.searchNote(patientId);
            return ResponseEntity.status(HttpStatus.OK).body(noteBean);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/note/save")
    public ResponseEntity addNewNote(@RequestBody NoteBean noteBean) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(noteProxy.addNewNote(noteBean));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/note/delete")
    public ResponseEntity deleteNote(@RequestBody NoteBean noteBean) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(noteProxy.deleteNote(noteBean));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/note/deleteAll/{patientId}")
    public ResponseEntity deleteAllPatientNoteNote(@PathVariable long patientId) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(noteProxy.deleteAllPatientNoteNote(patientId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
