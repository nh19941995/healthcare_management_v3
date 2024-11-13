package org.example.healthcare_management_v2.dto.doctorDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.healthcare_management_v2.enums.Gender;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDoctorDto {
    private String achievements;
    private String medicalTraining;
    private Long clinicId;
    private Long specializationId;

    private String fullName;
    private String email;
    private String address;
    private String phone;
    private String avatar;
    private Gender gender;
    private String description;
}
