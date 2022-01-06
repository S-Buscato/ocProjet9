package com.noteapp.repositories;

import com.noteapp.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<Note, Long> {
    Note findNotesById(long id);
    List<Note> findAllByPatientIdOrderByIdDesc(Long patientId);
    void  deleteAllByPatientId(Long patientId);
}
