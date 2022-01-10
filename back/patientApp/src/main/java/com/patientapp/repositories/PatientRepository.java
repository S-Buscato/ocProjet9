package com.patientapp.repositories;

import com.patientapp.model.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {

    @Query(" select p from Patient p where  p.firstname = :firstname and p.lastname = :lastname")
    Optional<Patient> findByFamily( String firstname, String lastname );
}
