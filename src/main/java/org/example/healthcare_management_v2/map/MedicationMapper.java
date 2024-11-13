package org.example.healthcare_management_v2.map;

import org.example.healthcare_management_v2.dto.medicationDto.MedicationDto;
import org.example.healthcare_management_v2.entities.Medication;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
        }
)
public interface MedicationMapper {

    MedicationDto medicationToMedicationDto(Medication medication);
    Medication medicationDtoToMedication(MedicationDto medicationDto);

}
