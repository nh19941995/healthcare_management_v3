package org.example.healthcare_management_v2.controller;

import lombok.AllArgsConstructor;
import org.example.healthcare_management_v2.dto.appointmentDto.AppointmentX;
import org.example.healthcare_management_v2.service.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    // lấy toàn bộ thông tin cuộc hẹn của một bệnh nhân
    // url: localhost:8080/api/patients/appointments/username?page=0&size=10
    @GetMapping("/appointments/{username}")
    public ResponseEntity<Page<AppointmentX>> getAppointments(
            @PathVariable String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AppointmentX> allAppointment = patientService.findAllAppointment(
                pageable, username
        );
        return ResponseEntity.ok(allAppointment);
    }
}
