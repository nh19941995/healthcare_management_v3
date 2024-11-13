package org.example.healthcare_management_v2.dto.prescriptionMedicationDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionMedicationDto {
    private Long medicationId; // thông tin thuốc
    private Long individualizedDosage; // liều lượng cá nhân
    private String note; // ghi chú khi sử dụng thuốc
}
