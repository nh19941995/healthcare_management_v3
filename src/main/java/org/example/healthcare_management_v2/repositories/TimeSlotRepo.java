package org.example.healthcare_management_v2.repositories;

import org.example.healthcare_management_v2.entities.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSlotRepo extends JpaRepository<TimeSlot, Long> {
}
