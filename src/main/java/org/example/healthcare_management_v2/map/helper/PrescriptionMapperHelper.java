package org.example.healthcare_management_v2.map.helper;

import lombok.AllArgsConstructor;
import org.example.healthcare_management_v2.dto.prescriptionMedicationDto.PrescriptionMedicationDto;
import org.example.healthcare_management_v2.entities.PrescriptionMedication;
import org.example.healthcare_management_v2.map.PrescriptionMedicationMapper;
import org.example.healthcare_management_v2.repositories.PrescriptionMedicationRepo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PrescriptionMapperHelper {
    private final PrescriptionMedicationRepo prescriptionMedicationRepo;
    private final PrescriptionMedicationMapper prescriptionMedicationMapper;

    @Named("getMedicationsByPrescriptionId")
    public Set<PrescriptionMedicationDto> getMedicationsByAppointmentId(Long prescriptionId) {
        // Lấy danh sách thuốc dựa vào id của đơn thuốc và chuyển đổi sang Set
        List<PrescriptionMedication> medications = prescriptionMedicationRepo.findAllByPrescriptionId(prescriptionId);
        return medications.stream()
                .map(prescriptionMedicationMapper::mapToDto) // sử dụng PrescriptionMedicationMapper
                .collect(Collectors.toSet()); // Chuyển List sang Set
    }
}

