package org.example.healthcare_management_v2.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.example.healthcare_management_v2.dto.clinicDto.AllClinicDto;
import org.example.healthcare_management_v2.entities.Clinic;
import org.example.healthcare_management_v2.map.AllClinicMapper;
import org.example.healthcare_management_v2.repositories.ClinicRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClinicServiceImpl implements ClinicService {

    private final ClinicRepo clinicRepo;
    private final AllClinicMapper allClinicMapper;

    @Override
    public Page<AllClinicDto> findAll(@NonNull Pageable pageable) {
        // Lấy ra tất cả các phòng khám
        Page<Clinic> clinicPage = clinicRepo.findAll(pageable);

        // Chuyển đổi từ entity sang dto
        return clinicPage.map(allClinicMapper::clinicToAllClinicDto);
    }
}
