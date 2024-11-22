package org.example.healthcare_management_v2.repositories;

import org.example.healthcare_management_v2.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface DoctorRepo extends JpaRepository<Doctor, Long> {
    Set<Doctor> findAllByClinicId(Long id);
}
