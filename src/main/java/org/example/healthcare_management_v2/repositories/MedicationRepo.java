package org.example.healthcare_management_v2.repositories;

import org.example.healthcare_management_v2.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepo extends JpaRepository<Medication, Long> {
}
