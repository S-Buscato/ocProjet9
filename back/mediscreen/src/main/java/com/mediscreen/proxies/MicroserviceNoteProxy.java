package com.mediscreen.proxies;

import com.mediscreen.beans.NoteBean;
import com.noteapp.dto.NoteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "microservice-note", url = "localhost:9003")
public interface MicroserviceNoteProxy {

    @GetMapping("/note/{id}")
    NoteBean getNote(@PathVariable Long id) ;

    @GetMapping("/note/search/{patientId}")
    List<NoteBean> searchNote(@PathVariable Long patientId);

    @PostMapping("/note/save")
    ResponseEntity<NoteBean> addNewNote(@RequestBody NoteBean noteBean) ;

    @PostMapping("/note/delete")
    ResponseEntity<NoteBean> deleteNote(@RequestBody NoteBean noteBean);

    @PostMapping("/note/deleteAll/{patientId}")
    ResponseEntity<Long> deleteAllPatientNoteNote(@PathVariable long patientId) ;

}
