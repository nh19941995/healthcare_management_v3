package org.example.healthcare_management_v2.controller;

import lombok.AllArgsConstructor;
import org.example.healthcare_management_v2.dto.medicationDto.MedicationDto;
import org.example.healthcare_management_v2.map.MedicationMapper;
import org.example.healthcare_management_v2.repositories.MedicationRepo;
import org.example.healthcare_management_v2.service.MedicationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/medications")
public class MedicationController {

    private final MedicationService medicationService;
    private final MedicationRepo medicationRepo;
    private final MedicationMapper medicationMapper;

    // lấy danh sách thuốc
    // url: localhost:8080/api/medications
    @GetMapping
    public ResponseEntity<Page<MedicationDto>> getMedications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(medicationService.findAll(pageable));
    }

    // lấy tên thuốc theo id
    // url: localhost:8080/api/medications/medication_id
    @GetMapping("/{medication_id}")
    public ResponseEntity<MedicationDto> getMedicationById(
            @PathVariable Long medication_id
    ) {
        MedicationDto medicationDto = medicationRepo.findById(medication_id).map(medicationMapper::medicationToMedicationDto)
                .orElseThrow(() -> new RuntimeException("Medication not found"));
        return ResponseEntity.ok(medicationDto);
    }

}
