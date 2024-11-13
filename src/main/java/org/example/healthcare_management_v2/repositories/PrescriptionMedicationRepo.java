package org.example.healthcare_management_v2.repositories;

import org.example.healthcare_management_v2.entities.PrescriptionMedication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionMedicationRepo extends JpaRepository<PrescriptionMedication, Long> {
}
