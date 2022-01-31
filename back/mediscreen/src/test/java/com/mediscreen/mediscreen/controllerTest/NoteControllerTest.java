package com.mediscreen.mediscreen.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.beans.NoteBean;
import com.mediscreen.constant.Messages;
import com.mediscreen.proxies.MicroserviceNoteProxy;
import com.mediscreen.proxies.MicroservicePatientProxy;
import com.mediscreen.service.MediscreenService;
import feign.FeignException;
import feign.Request;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
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
@RequiredArgsConstructor(onConstructor =  @__(@Autowired))
public class NoteControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @MockBean
    MediscreenService mediscreenService;

    @MockBean
    MicroservicePatientProxy patientProxy;

    @MockBean
    MicroserviceNoteProxy noteProxy;

    NoteBean noteBean = new NoteBean();
    List<NoteBean> noteBeanList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        noteBean.setId(1L);
        noteBean.setNote("ma note");
        noteBean.setPatientId(1L);
        noteBeanList.add(noteBean);
    }

    @Test
    @DisplayName("test get All patient note Succes")
    void testGetAllPatientNote() throws Exception {

        when(noteProxy.searchNote(anyLong())).thenReturn(ResponseEntity.status(HttpStatus.OK).body(noteBeanList));

        mockMvc.perform(get("/note/search/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("body", hasSize(1)))
                .andExpect(jsonPath("body[0].patientId", is(1)));

        verify(noteProxy, times(1)).searchNote(anyLong());
    }

    @Test
    @DisplayName("test get note by id Succes")
    void testGetNoteById() throws Exception {

        when(noteProxy.getNote(anyLong())).thenReturn(ResponseEntity.status(HttpStatus.OK).body(noteBean));

        mockMvc.perform(get("/note/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("body.patientId", is(1)));

        verify(noteProxy, times(1)).getNote(anyLong());
    }


    @Test
    @DisplayName("test save patient note Succes")
    void testSavePatientNote() throws Exception {

        when(noteProxy.addNewNote(any(NoteBean.class))).thenReturn( ResponseEntity.status(HttpStatus.OK).body(noteBean));

        mockMvc.perform(MockMvcRequestBuilders.post("/note/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteBean))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())

                .andExpect(jsonPath("body.patientId", is(1)));

        verify(noteProxy, times(1)).addNewNote(any(NoteBean.class));
    }


    @Test
    @DisplayName("test delete note succes")
    void testDeleteNote() throws Exception {

        when(noteProxy.deleteNote(anyLong())).thenReturn(ResponseEntity.status(HttpStatus.OK).body(1L));

        mockMvc.perform(MockMvcRequestBuilders.delete("/note/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("body", is(1)));

        verify(noteProxy, times(1)).deleteNote(anyLong());
    }


    @Test
    @DisplayName("test  delete note not found exception")
    void testDeleteNoteNotFoundException() throws Exception {
        when(noteProxy.deleteNote(anyLong())).thenThrow(FeignException.errorStatus(
                "noteDataDelete",
                Response.builder()
                        .status(404)
                        .reason("Not Found")
                        .request(Request.create(
                                Request.HttpMethod.DELETE,
                                "/note/delete/1",
                                Collections.emptyMap(),
                                null,
                                StandardCharsets.UTF_8,
                                null))
                        .body(Messages.NOTE_NOT_FOUND, StandardCharsets.UTF_8)//this field is required for construtor
                        .build()));

        mockMvc.perform(MockMvcRequestBuilders.delete("/note/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", is(Messages.NOTE_NOT_FOUND )))
                .andExpect(status().isNotFound());

        verify(noteProxy, times(1)).deleteNote(anyLong());
    }

    @Test
    @DisplayName("test All Patient note succes")
    void testDeleteAllPatientNote() throws Exception {

        when(noteProxy.deleteAllPatientNote(anyLong())).thenReturn(ResponseEntity.status(HttpStatus.OK).body(1L));

        mockMvc.perform(MockMvcRequestBuilders.delete("/note/deleteAll/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("body", is(1)));

        verify(noteProxy, times(1)).deleteAllPatientNote(anyLong());
    }
}
