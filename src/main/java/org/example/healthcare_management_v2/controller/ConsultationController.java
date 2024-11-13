package org.example.healthcare_management_v2.controller;

import lombok.AllArgsConstructor;
import org.example.healthcare_management_v2.entities.Consultation;
import org.example.healthcare_management_v2.repositories.ConsultationRepo;
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
@RequestMapping("/api/consultations")
public class ConsultationController {
    private final ConsultationRepo consultationRepo;

    @GetMapping("")
    public ResponseEntity<Page<Consultation>> getAllConsultations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(consultationRepo.findAll(pageable));
    }

}
