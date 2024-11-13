package org.example.healthcare_management_v2.service;


import org.example.healthcare_management_v2.dto.appointmentDto.AppointmentX;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientService {
    Page<AppointmentX> findAllAppointment(Pageable pageable, String username);
}
