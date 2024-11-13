package org.example.healthcare_management_v2.dto.appointmentDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.healthcare_management_v2.dto.TimeSlotDto;
import org.example.healthcare_management_v2.enums.AppointmentsStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentX {
    private Long id;
    private LocalDate appointmentDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private AppointmentsStatus status;
    private TimeSlotDto timeSlot;
    private DoctorX doctor;
    private PatientX patient;
}
