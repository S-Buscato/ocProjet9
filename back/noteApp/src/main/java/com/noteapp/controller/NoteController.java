package com.noteapp.controller;

import com.noteapp.dto.NoteDto;
import com.noteapp.exception.NoteNotFoundException;
import com.noteapp.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class NoteController {

    @Autowired
    NoteService noteService;

    static Logger logger = Logger.getLogger(String.valueOf(NoteController.class));


    @GetMapping("/note/{id}")
    public NoteDto getNote(@PathVariable Long id) {
        logger.info("getNote");
        return noteService.findById(id);
    }

    @GetMapping("/note/search/{patientId}")
    public ResponseEntity searchNote(@PathVariable Long patientId) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(noteService.findAllByPatient(patientId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/note/save")
    public ResponseEntity addNewNote(@RequestBody NoteDto noteDto) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(noteService.save(noteDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/note/delete")
    public ResponseEntity deleteNote(@RequestBody NoteDto noteDto) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(noteService.delete(noteDto));
        } catch (NoteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/note/deleteAll/{patientId}")
    public ResponseEntity deleteNote(@PathVariable long patientId) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(noteService.deleteAllPatientNote(patientId));
        } catch (NoteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
