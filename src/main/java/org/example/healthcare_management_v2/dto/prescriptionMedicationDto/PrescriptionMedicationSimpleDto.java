package org.example.healthcare_management_v2.dto.prescriptionMedicationDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionMedicationSimpleDto {
    private Long medicationId;  // thông tin thuốc
    private String totalDosage; // tổng liều lượng
    private String note; // ghi chú khi sử dụng thuốc
    private String dosageInstructions; // hướng dẫn liều dùng
}
