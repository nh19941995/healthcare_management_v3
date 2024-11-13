package org.example.healthcare_management_v2.repositories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.example.healthcare_management_v2.entities.Appointment;
import org.example.healthcare_management_v2.enums.AppointmentsStatus;
import org.example.healthcare_management_v2.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

    // Tìm kiếm một Appointment dựa trên appointmentDate, timeSlot và doctor
    Optional<Appointment> findByAppointmentDateAndTimeSlotIdAndDoctorId(
            LocalDate appointmentDate,
            Long timeSlotId,
            Long doctorId
    );

    // Tìm kiếm phan trang moi Appointment co status la PENDING
    Page<Appointment> findAllByStatus(AppointmentsStatus status, Pageable pageable);


    // Tìm kiếm một Appointment dựa trên patient_user_username và status
    Page<Appointment> findByPatientUserUsernameAndStatus (
            @NotBlank(message = "Username is required")
            @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
            String patient_user_username,
            AppointmentsStatus status,
            Pageable pageable
    );

    // Tìm kiếm một Appointment dựa trên patient_user_username
    Page<Appointment> findByPatientUserUsername (
            @NotBlank(message = "Username is required")
            @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
            String patient_user_username,
            Pageable pageable
    );

    // Truy vấn để lấy danh sách Appointment của một Doctor bằng username với phân trang
    @Query("SELECT a FROM Appointment a WHERE a.doctor.user.username = :username and a.status = :status")
    Page<Appointment> findAppointmentsByDoctorUsername(String username, AppointmentsStatus status, Pageable pageable);


}
