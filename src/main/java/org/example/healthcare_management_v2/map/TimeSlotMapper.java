package org.example.healthcare_management_v2.map;

import org.example.healthcare_management_v2.dto.TimeSlotDto;
import org.example.healthcare_management_v2.entities.TimeSlot;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
        } // bảo đảm MapStruct biết cách sử dụng RoleMapper cho các ánh xạ giữa Role và RoleDto
)
public interface TimeSlotMapper {

    TimeSlotDto timeSlotToTimeSlotDto(TimeSlot timeSlot);
}
