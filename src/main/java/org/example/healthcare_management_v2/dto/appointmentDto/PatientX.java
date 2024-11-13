package org.example.healthcare_management_v2.dto.appointmentDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.healthcare_management_v2.enums.Gender;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientX {
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String avatar;
    private Gender gender;

}
