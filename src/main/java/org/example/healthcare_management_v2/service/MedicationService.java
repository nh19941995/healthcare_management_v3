package org.example.healthcare_management_v2.service;

import org.example.healthcare_management_v2.dto.medicationDto.MedicationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

public interface MedicationService {
    Page<MedicationDto> findAll(@NonNull Pageable pageable);
}
