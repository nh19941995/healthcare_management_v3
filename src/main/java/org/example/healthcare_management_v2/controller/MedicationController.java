package org.example.healthcare_management_v2.controller;

import lombok.AllArgsConstructor;
import org.example.healthcare_management_v2.dto.medicationDto.MedicationDto;
import org.example.healthcare_management_v2.service.MedicationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/medications")
public class MedicationController {

    private final MedicationService medicationService;

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

}
