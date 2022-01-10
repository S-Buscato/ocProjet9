package com.noteapp.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noteapp.dto.NoteDto;
import com.noteapp.exception.NoteNotFoundException;
import com.noteapp.service.NoteService;
import com.noteapp.service.SequenceGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    NoteService noteService;

    @MockBean
    SequenceGeneratorService sequenceGeneratorService;

    NoteDto noteDto = new NoteDto();
    List<NoteDto> noteDtoList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        noteDto.setNote("ma note");
        noteDto.setPatientId(1L);
        noteDtoList.add(noteDto);

    }

    @Test
    @DisplayName("test get All patient note Succes")
    void testGetAllPatientNote() throws Exception {

        when(noteService.findAllByPatient(1L)).thenReturn(noteDtoList);

        mockMvc.perform(get("/note/search/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].patientId", is(1)));

        verify(noteService, times(1)).findAllByPatient(anyLong());
    }

   @Test
    @DisplayName("test get note by id Succes")
    void testGetNoteById() throws Exception {
        noteDto.setId(1L);

        when(noteService.findById(1L)).thenReturn(noteDto);

        mockMvc.perform(get("/note/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patientId", is(1)));

        verify(noteService, times(1)).findById(anyLong());
    }


    @Test
    @DisplayName("test save patient note Succes")
    void testSavePatientNOte() throws Exception {

        when(noteService.save(any(NoteDto.class))).thenReturn(noteDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/note/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.patientId", is(1)));

        verify(noteService, times(1)).save(any(NoteDto.class));
    }


    @Test
    @DisplayName("test delete note succes")
    void testDeleteNote() throws Exception {

        when(noteService.delete(any(NoteDto.class))).thenReturn(noteDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/note/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.patientId", is(1)));

        verify(noteService, times(1)).delete(any(NoteDto.class));
    }

    @Test
    @DisplayName("test  delete note not found exception")
    void testDeleteNoteNotFoundException() throws Exception {
        NoteNotFoundException e = new NoteNotFoundException();

        when(noteService.delete(any(NoteDto.class))).thenThrow(e);

        mockMvc.perform(MockMvcRequestBuilders.post("/note/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(noteService, times(1)).delete(any(NoteDto.class));
    }

    @Test
    @DisplayName("test All Patient note succes")
    void testDeleteAllPatientNote() throws Exception {

        when(noteService.deleteAllPatientNote(anyLong())).thenReturn(1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/note/deleteAll/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$", is(1)));

        verify(noteService, times(1)).deleteAllPatientNote(anyLong());
    }
}
