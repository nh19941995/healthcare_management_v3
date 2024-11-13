package org.example.healthcare_management_v2.controller;

import lombok.AllArgsConstructor;
import org.example.healthcare_management_v2.dto.auth.ApiResponse;
import org.example.healthcare_management_v2.dto.prescriptionDto.PrescriptionDto;
import org.example.healthcare_management_v2.service.PrescriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/prescriptions")
public class PrescriptionController {
    private final PrescriptionService prescriptionService;

    // tạo đơn thuốc
    // url http://localhost:8080/api/prescriptions
    @PostMapping("")
    public ResponseEntity<ApiResponse> createPrescription(
            @RequestBody PrescriptionDto prescriptionDto
    ) {
        prescriptionService.createPrescription(prescriptionDto);

        return ResponseEntity.ok(
                new ApiResponse(true, "Prescription created successfully!")
        );
    }

    // lấy thông tin đơn thuốc theo id của cuộc hẹn
    // url http://localhost:8080/api/prescriptions/appointment/{appointment_id}
    @GetMapping("/appointment/{appointment_id}")
    public ResponseEntity<PrescriptionDto> getPrescriptionByAppointmentId(
            @PathVariable Long appointment_id
    ) {
        PrescriptionDto prescriptionDto = prescriptionService.getPrescriptionByAppointmentId(appointment_id);
        return ResponseEntity.ok(prescriptionDto);
    }
}
