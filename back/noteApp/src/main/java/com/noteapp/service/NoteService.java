package com.noteapp.service;

import com.noteapp.controller.NoteController;
import com.noteapp.dto.NoteDto;
import com.noteapp.dto.mapper.NoteMapper;
import com.noteapp.exception.NoteNotFoundException;
import com.noteapp.model.Note;
import com.noteapp.repositories.NoteRepository;
import com.noteapp.service.iservice.INoteService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor =  @__(@Autowired))
public class NoteService implements INoteService {
    private final NoteRepository noteRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    private static final Logger logger = LogManager.getLogger(NoteService.class);


    @Override
    public NoteDto findById(Long id) {
        NoteDto noteDto = NoteMapper.INSTANCE.noteToNoteDTO(noteRepository.findNotesById(id));
        logger.info("noteServide findById return Note : " + noteDto.getNote());
        return noteDto;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Note> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGeneratorService.generateSequence(Note.SEQUENCE_NAME));
        }
    }

    @Override
    public NoteDto save(NoteDto noteDto) {
        Note note = NoteMapper.INSTANCE.noteDTOtoNote(noteDto);
        Date today = Date.from(Instant.now());
        //creation
        if(note.getId() == 0) {
            note.setId(sequenceGeneratorService.generateSequence(Note.SEQUENCE_NAME));
            note.setCreatedDate(today);
        };
        //update
        note.setUpdatedDate(today);
        return NoteMapper.INSTANCE.noteToNoteDTO(noteRepository.save(note));
    }

    @Override
    public List<NoteDto> findAllByPatient(Long patientId) {
        List<Note> notes = new ArrayList<>();
        if(noteRepository.findAllByPatientIdOrderByIdDesc(patientId) != null) {
            notes = noteRepository.findAllByPatientIdOrderByIdDesc(patientId);
        }
        return NoteMapper.INSTANCE.noteToNoteDTOList(notes);
    }

    @Override
    public long delete(long noteId) throws NoteNotFoundException {
        if(noteRepository.findById(noteId).isPresent()) {
            noteRepository.deleteById(noteId);
        } else {
            throw new NoteNotFoundException();
        }
        return noteId;
    }

    @Override
    public long deleteAllPatientNote(Long patientId) {
        if(!noteRepository.findAllByPatientIdOrderByIdDesc(patientId).isEmpty()) {
            noteRepository.deleteAllByPatientId(patientId);
        }
        return patientId;
    }
}
