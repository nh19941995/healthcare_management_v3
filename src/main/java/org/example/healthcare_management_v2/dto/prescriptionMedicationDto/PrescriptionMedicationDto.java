package org.example.healthcare_management_v2.dto.prescriptionMedicationDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.healthcare_management_v2.dto.medicationDto.MedicationDto;
import org.example.healthcare_management_v2.entities.Medication;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionMedicationDto {
    private MedicationDto medication;  // thông tin thuốc
    private String totalDosage; // tổng liều lượng
    private String note; // ghi chú khi sử dụng thuốc
    private String dosageInstructions; // hướng dẫn liều dùng
}
