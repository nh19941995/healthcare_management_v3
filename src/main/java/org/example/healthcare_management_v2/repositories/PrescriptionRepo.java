package org.example.healthcare_management_v2.repositories;

import org.example.healthcare_management_v2.entities.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepo extends JpaRepository<Prescription, Long> {
}
