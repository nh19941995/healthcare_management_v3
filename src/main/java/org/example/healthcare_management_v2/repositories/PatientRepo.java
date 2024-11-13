package org.example.healthcare_management_v2.repositories;

import org.example.healthcare_management_v2.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepo extends JpaRepository<Patient, Long> {
    // lấy ra toàn bộ các cuộc hẹn của một bệnh nhân

}
