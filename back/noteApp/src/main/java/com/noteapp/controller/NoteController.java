package com.noteapp.controller;

import com.noteapp.dto.NoteDto;
import com.noteapp.exception.NoteNotFoundException;
import com.noteapp.service.NoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@Api("API pour les opérations CRUD sur les notes des patients.")
@CrossOrigin(origins = "http://localhost:4200")
public class NoteController {

    @Autowired
    NoteService noteService;

    static Logger logger = Logger.getLogger(String.valueOf(NoteController.class));


    @ApiOperation(value = "Récupère une note grâce à son ID")
    @GetMapping("/note/{id}")
    public ResponseEntity getNote(@PathVariable Long id) {
        logger.info("getNote " + id);
        try{
            return ResponseEntity.status(HttpStatus.OK).body(noteService.findById(id));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @ApiOperation(value = "Récupère toutes les notes d'un patient grâce à l'ID du patient")
    @GetMapping("/note/search/{patientId}")
    public ResponseEntity searchNote(@PathVariable Long patientId) {
        logger.info("searchNote patient " + patientId);
        try{
            return ResponseEntity.status(HttpStatus.OK).body(noteService.findAllByPatient(patientId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @ApiOperation(value = "Enregistre ou met à jour une note")
    @PostMapping("/note/save")
    public ResponseEntity addNewNote(@RequestBody NoteDto noteDto) {
        logger.info("addNewNote " + noteDto.getNote());
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(noteService.save(noteDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @ApiOperation(value = "Efface une note grâce à son ID")
    @DeleteMapping("/note/delete/{noteId}")
    public ResponseEntity deleteNote(@PathVariable long noteId) {
        logger.info("deleteNote " + noteId);
        try{
            return ResponseEntity.status(HttpStatus.OK).body(noteService.delete(noteId));
        } catch (NoteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @ApiOperation(value = "Efface toutes les notes d'un patient grâce à l'ID patient")
    @DeleteMapping("/note/deleteAll/{patientId}")
    public ResponseEntity deleteAllPatientNote(@PathVariable long patientId) {
        logger.info("deleteAllPatientNote " + patientId);
        try{
            return ResponseEntity.status(HttpStatus.OK).body(noteService.deleteAllPatientNote(patientId));
        } catch (NoteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
