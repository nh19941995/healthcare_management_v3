package org.example.healthcare_management_v2.map;


import org.example.healthcare_management_v2.dto.prescriptionDto.PrescriptionDto;
import org.example.healthcare_management_v2.entities.Prescription;
import org.example.healthcare_management_v2.map.helper.PatientMapperHelper;
import org.example.healthcare_management_v2.map.helper.PrescriptionMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
                PatientMapperHelper.class,
                PrescriptionMedicationMapper.class,
                PrescriptionMapperHelper.class
        }
)
public interface PrescriptionMapper {

    @Mapping(target = "appointmentId", source = "appointment.id")
    @Mapping(target = "medications", source = "id", qualifiedByName = "getMedicationsByPrescriptionId")
    PrescriptionDto prescriptionToPrescriptionDto(Prescription prescription);



}
