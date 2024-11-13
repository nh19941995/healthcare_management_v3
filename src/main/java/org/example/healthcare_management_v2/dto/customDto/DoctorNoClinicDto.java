package org.example.healthcare_management_v2.dto.customDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.healthcare_management_v2.dto.ClinicDto;
import org.example.healthcare_management_v2.dto.SpecializationDto;
import org.example.healthcare_management_v2.entities.User;
import org.example.healthcare_management_v2.enums.Status;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorNoClinicDto {
    private Long id;
    private String achievements;
    private String medicalTraining;
    private Status status;
    private String lockReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private User user;
    //    private Set<Appointment> appointments = new HashSet<>();
    private ClinicDto clinic;
    private SpecializationDto specialization;
}
