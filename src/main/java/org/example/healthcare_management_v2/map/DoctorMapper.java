package org.example.healthcare_management_v2.map;

import org.example.healthcare_management_v2.dto.appointmentDto.DoctorX;
import org.example.healthcare_management_v2.dto.doctorDto.DoctorNoUserDto;
import org.example.healthcare_management_v2.dto.doctorDto.DoctorProfileDto;
import org.example.healthcare_management_v2.dto.doctorDto.Doctor_1;
import org.example.healthcare_management_v2.entities.Doctor;
import org.example.healthcare_management_v2.map.helper.DoctorMapperHelper;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
                UserMapper.class,
                ClinicMapper.class,
                SpecializationMapper.class,
                DoctorMapperHelper.class
        }
)
public interface DoctorMapper {

        DoctorNoUserDto doctorToDoctorNoUserDto(Doctor doctor);


        @Mapping(target = "fullName", source = "user.fullName")
        @Mapping(target = "email", source = "user.email")
        @Mapping(target = "address", source = "user.address")
        @Mapping(target = "phone", source = "user.phone")
        @Mapping(target = "avatar", source = "user.username", qualifiedByName = "getAvatarForDoctor")
        @Mapping(target = "gender", source = "user.gender")
        @Mapping(target = "description", source = "user.description")
        @Mapping(target = "userId", source = "user.id")
        @Mapping(target = "doctorId", source = "id")
        @Mapping(target = "username", source = "user.username")
        DoctorProfileDto doctorToDoctorProfileDto(Doctor doctor);

        @Mapping(target = "fullName", source = "user.fullName")
        @Mapping(target = "username", source = "user.username")
        @Mapping(target = "email", source = "user.email")
        @Mapping(target = "address", source = "user.address")
        @Mapping(target = "phone", source = "user.phone")
//        @Mapping(target = "avatar", source = "user.username", qualifiedByName = "getAvatarForDoctor")
        @Mapping(target = "gender", source = "user.gender")
        @Mapping(target = "description", source = "user.description")
        @Mapping(target = "userId", source = "user.id")
        @Mapping(target = "doctorId", source = "id")
        @Mapping(target = "clinicId", source = "clinic.id")
        @Mapping(target = "specializationId", source = "specialization.id")
        Doctor_1 doctorToDoctor_1(Doctor doctor);

        @Mapping(target = "doctorId", source = "id")
        @Mapping(target = "clinicId", source = "clinic.id")
        @Mapping(target = "specializationId", source = "specialization.id")
        @Mapping(target = "fullName", source = "user.fullName")
        @Mapping(target = "email", source = "user.email")
        @Mapping(target = "address", source = "user.address")
        @Mapping(target = "phone", source = "user.phone")
        @Mapping(target = "avatar", source = "user.username", qualifiedByName = "getAvatarForDoctor")
        @Mapping(target = "gender", source = "user.gender")
        @Mapping(target = "description", source = "user.description")
        @Mapping(target = "username", source = "user.username")
        DoctorX doctorToDoctorX(Doctor doctor);

}
