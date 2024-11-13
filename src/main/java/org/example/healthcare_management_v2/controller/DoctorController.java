package org.example.healthcare_management_v2.controller;

import lombok.AllArgsConstructor;
import org.example.healthcare_management_v2.dto.appointmentDto.AppointmentDto;
import org.example.healthcare_management_v2.dto.appointmentDto.AppointmentX;
import org.example.healthcare_management_v2.dto.doctorDto.DoctorProfileDto;
import org.example.healthcare_management_v2.dto.doctorDto.Doctor_1;
import org.example.healthcare_management_v2.dto.doctorDto.UpdateDoctorDto;
import org.example.healthcare_management_v2.repositories.DoctorRepo;
import org.example.healthcare_management_v2.service.AppointmentService;
import org.example.healthcare_management_v2.service.DoctorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctors")
@AllArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;
    private final DoctorRepo doctorRepo;
    private final AppointmentService appointmentService;

    // cập nhật thông tin bác sĩ
    // url: localhost:8080/api/doctors/username
    @PutMapping("/{username}")
    public ResponseEntity<UpdateDoctorDto> updateProfile(
            @RequestBody UpdateDoctorDto doctorDto, @PathVariable String username
    ) {
        // Kiểm tra xem người dùng hiện tại có phải là người sở hữu tài khoản không
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        if (!currentUsername.equals(username)) {
            throw new RuntimeException("You can only view your own profile");
        }
        return ResponseEntity.ok(doctorService.updateProfile(doctorDto, username));
    }

    // lấy thông tin bác sĩ theo username
    // url: localhost:8080/api/doctors/username
    @GetMapping("/{username}")
    public ResponseEntity<DoctorProfileDto> getDoctorProfile(@PathVariable String username) {
        DoctorProfileDto doctorDto = doctorService.getDoctorProfile(username);
        return ResponseEntity.ok(doctorDto);
    }

    // lấy tất cả bác sĩ (có phân trang)
    // url: localhost:8080/api/doctors
    @GetMapping
    public ResponseEntity<Page<Doctor_1>> getDoctors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(doctorService.findAll(pageable));
    }


    // lay danh sach lich kham cua bac si
    // url: localhost:8080/api/doctors/appointments/username
    @GetMapping("/appointments/{username}")
    public ResponseEntity<Page<AppointmentX>> getAppointments(
            @PathVariable String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(appointmentService.findAppointmentsByDoctorUsername(username,pageable));
    }


    // tạo đơn thuốc
    // url: localhost:8080/api/doctors/AppointmentId/prescription
//    @PostMapping("/prescription")
//    public ResponseEntity<String> createPrescription(
//            @RequestBody PrescriptionDto prescriptionDto
//    ) {
//        doctorService.createPrescription(prescriptionDto);
//        return ResponseEntity.ok("Prescription created successfully");
//    }
}
