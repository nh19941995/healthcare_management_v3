package org.example.healthcare_management_v2.map;

import org.example.healthcare_management_v2.dto.appointmentDto.PatientX;
import org.example.healthcare_management_v2.entities.Patient;
import org.example.healthcare_management_v2.map.helper.PatientMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
                PatientMapperHelper.class
        }
)
public interface PatientMapper {

    @Mapping(target = "fullName", source = "user.fullName")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "address", source = "user.address")
    @Mapping(target = "phone", source = "user.phone")
    @Mapping(target = "avatar", source = "user.username", qualifiedByName = "getAvatarForPatient")
    @Mapping(target = "gender", source = "user.gender")
    @Mapping(target = "username", source = "user.username")
    PatientX patientToPatientX(Patient patient);
}
