package org.example.healthcare_management_v2.map;

import org.example.healthcare_management_v2.dto.SpecializationDto;
import org.example.healthcare_management_v2.dto.specializationDto.SpecializationDtoNoDoctor;
import org.example.healthcare_management_v2.entities.Specialization;
import org.example.healthcare_management_v2.map.helper.SpecializationMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
                DoctorMapper.class,
                SpecializationMapperHelper.class
        }
)
public interface SpecializationMapper {

        @Mapping(target = "image", source = "id", qualifiedByName = "getSpecializationImage")
        SpecializationDto specializationToSpecializationDto(Specialization specialization);

        @Mapping(target = "image", source = "id", qualifiedByName = "getSpecializationImage")
        SpecializationDtoNoDoctor specializationToSpecializationDtoNoDoctor(Specialization specialization);
}
