package org.example.healthcare_management_v2.service;

import org.example.healthcare_management_v2.dto.clinicDto.AllClinicDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

public interface ClinicService {
    Page<AllClinicDto> findAll(@NonNull Pageable pageable);
}
