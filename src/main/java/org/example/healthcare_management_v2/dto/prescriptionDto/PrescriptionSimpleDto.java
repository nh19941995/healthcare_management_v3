package org.example.healthcare_management_v2.dto.prescriptionDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.healthcare_management_v2.dto.prescriptionMedicationDto.PrescriptionMedicationSimpleDto;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionSimpleDto {
    private Long appointmentId; // thông tin cuộc hẹn
    private String medicalDiagnosis; // chuẩn đoán bệnh
    Set<PrescriptionMedicationSimpleDto> medications; // danh sách thuốc trong đơn thuốc
}
