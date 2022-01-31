package com.mediscreen.proxies;

import com.mediscreen.beans.NoteBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservice-note", url = "localhost:9003")
/*
@FeignClient(name = "microservice-note", url = "noteApp:9003")
*/
public interface MicroserviceNoteProxy {

    @GetMapping("/note/{id}")
    ResponseEntity<NoteBean> getNote(@PathVariable Long id) ;

    @GetMapping("/note/search/{patientId}")
    ResponseEntity<List<NoteBean>> searchNote(@PathVariable Long patientId);

    @PostMapping("/note/save")
    ResponseEntity<NoteBean> addNewNote(@RequestBody NoteBean noteBean) ;

    @DeleteMapping("/note/delete/{noteId}")
    ResponseEntity<Long> deleteNote(@PathVariable long noteId) throws Exception;

    @DeleteMapping("/note/deleteAll/{patientId}")
    ResponseEntity<Long> deleteAllPatientNote(@PathVariable long patientId) ;

}


