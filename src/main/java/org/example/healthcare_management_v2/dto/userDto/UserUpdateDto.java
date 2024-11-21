package org.example.healthcare_management_v2.dto.userDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.healthcare_management_v2.enums.Gender;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {
    // user
    private Long id;
    private String fullName;
    private String username;
    private String email;
    private String address;
    private String phone;
    private Gender gender;
    private String description;
    // doctor
    private String achievements;
    private String medicalTraining;
    private Long clinicId;
    private Long specializationId;
}
