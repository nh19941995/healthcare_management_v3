package org.example.healthcare_management_v2.repositories;

import org.example.healthcare_management_v2.entities.PatientStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientStatusRepo extends JpaRepository<PatientStatus, Long> {
}
