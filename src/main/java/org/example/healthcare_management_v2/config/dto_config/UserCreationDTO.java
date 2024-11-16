package org.example.healthcare_management_v2.config.dto_config;

import lombok.Builder;
import lombok.Data;
import org.example.healthcare_management_v2.enums.EnumRole;
import org.example.healthcare_management_v2.enums.Gender;
import org.example.healthcare_management_v2.enums.Status;

import java.util.List;

@Data
@Builder
public class UserCreationDTO {
    private String fullName;
    private String description;
    private String username;
    private String password;
    private String email;
    private String address;
    private String phone;
    private Gender gender;
    private String avatar;
    private Status status;
    private List<EnumRole> roleTypes;
    private DoctorInfoDTO doctorInfo;
    private boolean createPatient;
}
