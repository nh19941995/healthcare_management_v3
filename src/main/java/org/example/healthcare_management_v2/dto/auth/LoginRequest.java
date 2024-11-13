package org.example.healthcare_management_v2.dto.auth;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginRequest {
    private String username;
    private String password;
}

