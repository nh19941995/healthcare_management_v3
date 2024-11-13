package org.example.healthcare_management_v2.map;


import org.example.healthcare_management_v2.dto.ClinicDto;
import org.example.healthcare_management_v2.dto.clinicDto.ClinicDtoNoDoctor;
import org.example.healthcare_management_v2.entities.Clinic;
import org.example.healthcare_management_v2.map.helper.ClinicMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
                DoctorMapper.class,
                ClinicMapperHelper.class
        }
)
public interface ClinicMapper {

    @Mapping(target = "image", source = "id", qualifiedByName = "getClinicImage")
    ClinicDto clinicToClinicDto(Clinic clinic);

    @Mapping(target = "image", source = "id", qualifiedByName = "getClinicImage")
    ClinicDtoNoDoctor clinicToClinicDtoNoDoctor(Clinic clinic);

}
