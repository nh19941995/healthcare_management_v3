package org.example.healthcare_management_v2.config.dto_config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorInfoDTO {
    private Long specializationId;
    private Long clinicId;
    private String achievements;
    private String medicalTraining;
}
