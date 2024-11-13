package org.example.healthcare_management_v2.repositories;

import org.example.healthcare_management_v2.entities.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecializationRepo extends JpaRepository<Specialization, Long> {
}
