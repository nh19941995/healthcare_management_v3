package org.example.healthcare_management_v2.repositories;

import org.example.healthcare_management_v2.entities.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepo extends JpaRepository<Consultation, Long> {
}
