package org.example.healthcare_management_v2.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumRole {
    ADMIN("ADMIN"),
    DOCTOR("DOCTOR"),
    PATIENT("PATIENT"),
    RECEPTIONIST("RECEPTIONIST");

    private final String roleName;
}
