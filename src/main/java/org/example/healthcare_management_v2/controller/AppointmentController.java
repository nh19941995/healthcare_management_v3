package org.example.healthcare_management_v2.controller;

import lombok.AllArgsConstructor;
import org.example.healthcare_management_v2.dto.appointmentDto.AppointmentDto;
import org.example.healthcare_management_v2.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/appointments")
@AllArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;


    // tạo một cuộc hẹn mới
    // url: localhost:8080/api/appointments/patient_username/doctor_username/timeSlot_id/appointmentDate
    @PostMapping("{patient_username}/{doctor_username}/{timeSlot_id}/{appointmentDate}")
    public ResponseEntity<AppointmentDto> addAppointment(
            @PathVariable String patient_username,
            @PathVariable String doctor_username,
            @PathVariable Long timeSlot_id,
            @PathVariable String appointmentDate
    ) {
        // Kiểm tra xem người dùng hiện tại có phải là người sở hữu tài khoản không
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        if (!currentUsername.equals(patient_username)) {
            throw new RuntimeException("You can only create appointment for yourself");
        }
        AppointmentDto appointment = appointmentService.createAppointment(
                patient_username, doctor_username, timeSlot_id, appointmentDate);
        return ResponseEntity.ok(appointment);
    }

}
