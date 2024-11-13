package org.example.healthcare_management_v2.dto.userDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.healthcare_management_v2.dto.RoleDto;
import org.example.healthcare_management_v2.dto.doctorDto.DoctorNoUserDto;
import org.example.healthcare_management_v2.enums.Gender;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWithDoctorDto {
    private Long id;
    private String fullName;
    private String username;
    private String email;
    private String address;
    private String phone;
    private String avatar;
    private Gender gender;
    private String status;
    private String lockReason;
    private String description;
    private Set<RoleDto> roles;
    private DoctorNoUserDto doctor;
}
