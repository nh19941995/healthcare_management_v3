package org.example.healthcare_management_v2.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.example.healthcare_management_v2.dto.medicationDto.MedicationDto;
import org.example.healthcare_management_v2.map.MedicationMapper;
import org.example.healthcare_management_v2.repositories.MedicationRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepo medicationRepo;
    private final MedicationMapper medicationMapper;

    @Override
    public Page<MedicationDto> findAll(@NonNull Pageable pageable) {
        return medicationRepo.findAll(pageable).map(medicationMapper::medicationToMedicationDto);
    }
}
