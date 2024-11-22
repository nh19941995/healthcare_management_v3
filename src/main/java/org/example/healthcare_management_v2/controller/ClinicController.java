package org.example.healthcare_management_v2.controller;

import lombok.AllArgsConstructor;
import org.example.healthcare_management_v2.dto.clinicDto.AllClinicDto;
import org.example.healthcare_management_v2.entities.Clinic;
import org.example.healthcare_management_v2.entities.Doctor;
import org.example.healthcare_management_v2.map.AllClinicMapper;
import org.example.healthcare_management_v2.repositories.ClinicRepo;
import org.example.healthcare_management_v2.repositories.DoctorRepo;
import org.example.healthcare_management_v2.service.ClinicService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping("/api/clinics")
@AllArgsConstructor
public class ClinicController {

    private final ClinicService clinicService;
    private final ClinicRepo clinicRepository;
    private final AllClinicMapper clinicMapper;
    private final DoctorRepo doctorRepo;

    // lay danh sách các phòng khám
    // url: localhost:8080/api/clinics?page=0&size=5
    @GetMapping("")
    public ResponseEntity<Page<AllClinicDto>> getAllClinics(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(clinicService.findAll(pageable));
    }

    // lấy thông tin phòng khám theo id
    // url: localhost:8080/api/clinics/1
    @GetMapping("/{id}")
    public ResponseEntity<AllClinicDto> getClinicById(@PathVariable Long id) {
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clinic not found: " + id));
        Set<Doctor> doctors = doctorRepo.findAllByClinicId(id);
        clinic.setDoctors(doctors);

        AllClinicDto ClinicDtoWithDoctor = clinicMapper.clinicToAllClinicDto(clinic);
        return ResponseEntity.ok(ClinicDtoWithDoctor);
    }


}
