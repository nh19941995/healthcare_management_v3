package org.example.healthcare_management_v2.map;

import org.example.healthcare_management_v2.dto.RoleDto;
import org.example.healthcare_management_v2.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {
    RoleDto roleToRoleDto(Role role);
}
