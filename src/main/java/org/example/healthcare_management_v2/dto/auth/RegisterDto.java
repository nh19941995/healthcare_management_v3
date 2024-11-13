package org.example.healthcare_management_v2.dto.auth;

import lombok.*;
import org.example.healthcare_management_v2.enums.Gender;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String address;
    private String phone;
    private Gender gender;
    private String description;
}
