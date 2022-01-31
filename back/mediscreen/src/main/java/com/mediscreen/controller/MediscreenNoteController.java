package com.mediscreen.controller;


import com.mediscreen.beans.NoteBean;
import com.mediscreen.proxies.MicroserviceNoteProxy;
import feign.FeignException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api("API pour les opérations CRUD sur les notes des patients.")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor(onConstructor =  @__(@Autowired))
public class MediscreenNoteController {

    private final MicroserviceNoteProxy noteProxy;
    private static final Logger logger = LogManager.getLogger(MediscreenNoteController.class);

    @ApiOperation(value = "Récupère une note grâce à son ID")
    @GetMapping("/note/{id}")
    public ResponseEntity getNote(@PathVariable Long id) {
        logger.info("mediscreen getNote " + id);
        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(noteProxy.getNote(id));
        } catch (FeignException e) {
            logger.error("/note/{id} feign client error ",e.contentUTF8());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.contentUTF8());
        }  catch (Exception e) {
            logger.error("/note/{id} error ", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @ApiOperation(value = "Récupère toutes les notes d'un patient grâce à l'ID du patient")
    @GetMapping("/note/search/{patientId}")
    public ResponseEntity searchNote(@PathVariable Long patientId){
        logger.info("mediscreen searchNote patient " + patientId);
        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(noteProxy.searchNote(patientId));
        } catch (FeignException e) {
            logger.error("/note/search/{patientId} feign client error ", e.contentUTF8());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("/note/search/{patientId} error ", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @ApiOperation(value = "Enregistre une note")
    @PostMapping("/note/save")
    public ResponseEntity addNewNote(@RequestBody NoteBean noteBean) {
        logger.info("mediscreen addNewNote patient " + noteBean.getPatientId());
        try{
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(noteProxy.addNewNote(noteBean));
        } catch (FeignException e) {
            logger.error("/patient/{id} feign client error ",e.contentUTF8());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("/patient/{id} error ", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    @ApiOperation(value = "Efface une note grâce à son ID")
    @DeleteMapping("/note/delete/{noteId}")
    public ResponseEntity deleteNote(@PathVariable long noteId) {
        logger.info("mediscreen delete patient note " + noteId);
        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(noteProxy.deleteNote(noteId));
        } catch (FeignException e) {
            logger.error("/note/delete/{noteId} feign client error ",e.contentUTF8());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("/note/delete/{noteId} error ", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @ApiOperation(value = "Efface toutes les notes d'un patient grâce à l'ID patient")
    @DeleteMapping("/note/deleteAll/{patientId}")
    public ResponseEntity deleteAllPatientNote(@PathVariable long patientId) {
        logger.info("mediscreen deleteAllPatientNoteNote patient " + patientId);
        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(noteProxy.deleteAllPatientNote(patientId));
        }  catch (FeignException e) {
            logger.error("/note/deleteAll/{patientId} feign client error ",e.contentUTF8());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("/note/deleteAll/{patientId} error " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

}
