package org.example.healthcare_management_v2.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.example.healthcare_management_v2.dto.specializationDto.SpecializationDtoNoDoctor;
import org.example.healthcare_management_v2.map.SpecializationMapper;
import org.example.healthcare_management_v2.repositories.SpecializationRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SpecializationServiceImpl implements SpecializationService {

    private final SpecializationRepo specializationRepo;
    private final SpecializationMapper specializationMapper;

    @Override
    public Page<SpecializationDtoNoDoctor> findAll(@NonNull Pageable pageable) {
        return specializationRepo.findAll(pageable)
                .map(specializationMapper:: specializationToSpecializationDtoNoDoctor);
    }
}
