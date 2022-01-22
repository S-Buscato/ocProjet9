package com.noteapp.service.iservice;

import com.noteapp.dto.NoteDto;
import com.noteapp.exception.NoteNotFoundException;
import com.noteapp.model.Note;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

import java.util.List;

public interface INoteService {

    NoteDto findById(Long id);

    void onBeforeConvert(BeforeConvertEvent<Note> event);

    NoteDto save(NoteDto noteDto);
    List<NoteDto> findAllByPatient(Long patientId);
    long delete(long noteId) throws NoteNotFoundException;
    long deleteAllPatientNote(Long patientId) throws NoteNotFoundException;
}
