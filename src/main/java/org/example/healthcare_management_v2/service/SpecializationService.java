package org.example.healthcare_management_v2.service;

import org.example.healthcare_management_v2.dto.specializationDto.SpecializationDtoNoDoctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

public interface SpecializationService {
    Page<SpecializationDtoNoDoctor> findAll(@NonNull Pageable pageable);
}
