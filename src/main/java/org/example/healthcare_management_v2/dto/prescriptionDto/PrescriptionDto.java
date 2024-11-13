package org.example.healthcare_management_v2.dto.prescriptionDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.healthcare_management_v2.dto.prescriptionMedicationDto.PrescriptionMedicationDto;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionDto {
    private Long appointmentId; // thông tin cuộc hẹn
    private String medicalDiagnosis; // chuẩn đoán bệnh
    Set<PrescriptionMedicationDto> medications; // danh sách thuốc trong đơn thuốc
}
