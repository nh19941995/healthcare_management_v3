package org.example.healthcare_management_v2.repositories;

import org.example.healthcare_management_v2.entities.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ClinicRepo extends JpaRepository<Clinic, Long> {

    @Query("SELECT c FROM Clinic c LEFT JOIN FETCH c.doctors WHERE c.id = :id")
    Optional<Clinic> findByIdWithDoctors(@Param("id") Long id);


}
