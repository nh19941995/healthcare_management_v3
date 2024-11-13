package org.example.healthcare_management_v2.repositories;

import org.example.healthcare_management_v2.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepo extends JpaRepository<Doctor, Long> {

}
