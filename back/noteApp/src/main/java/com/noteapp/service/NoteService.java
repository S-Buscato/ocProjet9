package com.noteapp.service;

import com.noteapp.dto.NoteDto;
import com.noteapp.dto.mapper.NoteMapper;
import com.noteapp.exception.NoteNotFoundException;
import com.noteapp.model.Note;
import com.noteapp.repositories.NoteRepository;
import com.noteapp.service.iservice.INoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class NoteService implements INoteService {
    @Autowired
    NoteRepository noteRepository;

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    @Override
    public NoteDto findById(Long id) {
        return NoteMapper.INSTANCE.noteToNoteDTO(noteRepository.findNotesById(id));
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
        System.out.println("date : " + today);
        if(note.getId() == 0) {
            note.setId(sequenceGeneratorService.generateSequence(Note.SEQUENCE_NAME));
            note.setCreatedDate(today);
        };

        note.setUpdatedDate(today);
        return NoteMapper.INSTANCE.noteToNoteDTO(noteRepository.save(note));
    }

    @Override
    public List<NoteDto> findAllByPatient(Long patientId) {
        List<Note> notes = patientId == null ? noteRepository.findAll() : noteRepository.findAllByPatientIdOrderByIdDesc(patientId);
        return NoteMapper.INSTANCE.noteToNoteDTOList(notes);
    }

    @Override
    public NoteDto delete(NoteDto noteDto) throws NoteNotFoundException {
        if(noteRepository.findById(noteDto.getId()).isPresent()) {
            noteRepository.deleteById(noteDto.getId());
        } else {
            throw new NoteNotFoundException();
        }
        return noteDto;
    }

    @Override
    public long deleteAllPatientNote(Long patientId) throws NoteNotFoundException {
        if(!noteRepository.findAllByPatientIdOrderByIdDesc(patientId).isEmpty()) {
            noteRepository.deleteAllByPatientId(patientId);
        }else {
            throw new NoteNotFoundException();
        }
        return patientId;
    }
}
