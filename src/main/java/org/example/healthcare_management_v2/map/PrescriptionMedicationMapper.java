package org.example.healthcare_management_v2.map;

import org.example.healthcare_management_v2.dto.prescriptionMedicationDto.PrescriptionMedicationDto;
import org.example.healthcare_management_v2.entities.PrescriptionMedication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
        }
)
public interface PrescriptionMedicationMapper {

    PrescriptionMedication mapToEntity(PrescriptionMedicationDto prescriptionMedicationDto);

    PrescriptionMedicationDto mapToDto(PrescriptionMedication prescriptionMedication);
}
