package org.example.healthcare_management_v2.controller;

import lombok.AllArgsConstructor;
import org.example.healthcare_management_v2.dto.specializationDto.SpecializationDtoNoDoctor;
import org.example.healthcare_management_v2.service.SpecializationService;
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
@RequestMapping("/api/specializations")
public class SpecializationController {

    private final SpecializationService specializationService;

    // lay danh sách các chuyên khoa
    // url: localhost:8080/api/specializations?page=0&size=5
    @GetMapping("")
    public ResponseEntity<Page<SpecializationDtoNoDoctor>> getAllSpecialization(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size

    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(specializationService.findAll(pageable));
    }
}
