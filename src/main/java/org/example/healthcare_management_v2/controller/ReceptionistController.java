package org.example.healthcare_management_v2.controller;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.example.healthcare_management_v2.dto.appointmentDto.AppointmentX;
import org.example.healthcare_management_v2.dto.auth.ApiResponse;
import org.example.healthcare_management_v2.service.AppointmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/receptionists")
public class ReceptionistController {
    private final AppointmentService appointmentService;

    // lấy tất cả cuộc hẹn chưa xác nhận
    // url: localhost:8080/api/receptionists?page=0&size=10
    @GetMapping
    public ResponseEntity<Page<AppointmentX>> getAllPendding(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(appointmentService.findAllPendingAppointment(pageable));
    }

    // xác nhận cuộc hẹn
    // url: localhost:8080/api/receptionists/1/CONFIRMED
    @PutMapping("{appointment_id}/{status}")
    public ResponseEntity<ApiResponse> confirmAppointment(
            @PathVariable Long appointment_id,
            @PathVariable @Pattern(regexp = "^(PENDING|CONFIRMED|COMPLETED|NO_SHOW|CANCELLED|RESCHEDULED)$", message = "Invalid status name") String status
    ) {
        appointmentService.confirmAppointment(appointment_id,status);
        ApiResponse apiResponse = new ApiResponse(true, "Appointment: " + status);
        return ResponseEntity.ok(apiResponse);
    }
}
