package com.noteapp.serviceTest;

import com.noteapp.constant.Messages;
import com.noteapp.dto.NoteDto;
import com.noteapp.exception.NoteNotFoundException;
import com.noteapp.model.Note;
import com.noteapp.repositories.NoteRepository;
import com.noteapp.service.NoteService;
import com.noteapp.service.SequenceGeneratorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = NoteService.class)
public class NoteServiceTest {

    @Autowired
    private NoteService noteService;

    @MockBean
    SequenceGeneratorService sequenceGeneratorService;

    @MockBean
    NoteRepository noteRepository;

    NoteDto noteDto = new NoteDto();
    List<NoteDto> noteDtoList = new ArrayList<>();

    Note note = new Note();
    List<Note> noteList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        noteDto.setNote("ma note");
        noteDto.setId(1L);
        noteDtoList.add(noteDto);

        note.setNote("ma note");
        note.setId(1L);
        noteList.add(note);

    }

    @Test
    @DisplayName("test find Note by id")
    void testFindNoteById() {
        when(noteRepository.findNotesById(anyLong())).thenReturn(note);

        NoteDto noteDto1 = noteService.findById(1L);

        Assertions.assertEquals("ma note", noteDto1.getNote());
        verify(noteRepository, times(1)).findNotesById(anyLong());
    }

   @Test
    @DisplayName("test find All Note")
    void testFindAllNote() {
        when(noteRepository.findAll()).thenReturn(noteList);
        when(noteRepository.findAllByPatientIdOrderByIdDesc(anyLong())).thenReturn(noteList);

       List<NoteDto> noteDtos1 = noteService.findAllByPatient(anyLong());

        Assertions.assertEquals(1, noteDtos1.size());
        verify(noteRepository, times(2)).findAllByPatientIdOrderByIdDesc(anyLong());
    }


    @Test
    @DisplayName("test save Note ")
    void testSaveNote() {
        when(noteRepository.save(any(Note.class))).thenReturn(note);

        NoteDto noteDto1 = noteService.save(noteDto);

        Assertions.assertEquals("ma note", noteDto1.getNote());
        verify(noteRepository, times(1)).save(any(Note.class));
    }

     @Test
    @DisplayName("test update Note ")
    void testUpdateNote() {
        noteDto.setId(1L);
        noteDto.setNote("Ma nouvelle note");
        note.setNote("Ma note modifiée");

        when(noteRepository.save(any(Note.class))).thenReturn(note);

        NoteDto noteDto1 = noteService.save(noteDto);

        Assertions.assertEquals("Ma note modifiée", noteDto1.getNote());
        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    @DisplayName("test delete Note")
    void testDeleteNote() throws NoteNotFoundException {
        noteDto.setId(1L);

        when(noteRepository.findById(1L)).thenReturn(Optional.ofNullable(note));

        noteService.delete(noteDto);

        verify(noteRepository, times(1)).deleteById(anyLong());
        verify(noteRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("test delete All Patient Note")
    void testDeleteAllPatientNote() throws NoteNotFoundException {
        noteDto.setId(1L);

        when(noteRepository.findAllByPatientIdOrderByIdDesc(anyLong())).thenReturn(noteList);

        noteService.deleteAllPatientNote(1L);

        verify(noteRepository, times(1)).findAllByPatientIdOrderByIdDesc(anyLong());
        verify(noteRepository, times(1)).deleteAllByPatientId(anyLong());
    }


    @Test
    @DisplayName("test delete Note not found")
    void testDeleteNoteNotFound() {

        when(noteRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        NoteNotFoundException exception = Assertions.assertThrows(NoteNotFoundException.class, () -> {
            noteService.delete(noteDto);
        });

        Assertions.assertEquals(Messages.NOTE_NOT_FOUND,exception.getMessage() );

        verify(noteRepository, times(1)).findById(anyLong());
    }

}
