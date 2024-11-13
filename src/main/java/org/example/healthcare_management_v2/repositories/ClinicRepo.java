package org.example.healthcare_management_v2.repositories;

import org.example.healthcare_management_v2.entities.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRepo extends JpaRepository<Clinic, Long> {
}
