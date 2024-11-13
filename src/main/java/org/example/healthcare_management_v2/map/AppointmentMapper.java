package org.example.healthcare_management_v2.map;


import org.example.healthcare_management_v2.dto.appointmentDto.AppointmentDto;
import org.example.healthcare_management_v2.dto.appointmentDto.AppointmentX;
import org.example.healthcare_management_v2.entities.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
                DoctorMapper.class,
                PatientMapper.class,
                TimeSlotMapper.class
        }
)
public interface AppointmentMapper {
    AppointmentDto appointmentToAppointmentDto(Appointment appointment);

    AppointmentX appointmentToAppointmentX(Appointment appointment);
}
