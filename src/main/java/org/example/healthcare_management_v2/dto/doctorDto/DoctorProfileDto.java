package org.example.healthcare_management_v2.dto.doctorDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.healthcare_management_v2.dto.clinicDto.ClinicDtoNoDoctor;
import org.example.healthcare_management_v2.dto.specializationDto.SpecializationDtoNoDoctor;
import org.example.healthcare_management_v2.enums.Gender;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorProfileDto {
    private Long userId;
    private Long doctorId;
    private String achievements;
    private String medicalTraining;
    private ClinicDtoNoDoctor clinic;
    private SpecializationDtoNoDoctor specialization;
    private String fullName;
    private String email;
    private String address;
    private String phone;
    private String avatar;
    private Gender gender;
    private String description;
}
