package org.example.healthcare_management_v2.service;

import lombok.AllArgsConstructor;
import org.example.healthcare_management_v2.dto.appointmentDto.AppointmentX;
import org.example.healthcare_management_v2.entities.Appointment;
import org.example.healthcare_management_v2.map.AppointmentMapper;
import org.example.healthcare_management_v2.repositories.AppointmentRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final AppointmentRepo appointmentRepo;
    private final AppointmentMapper appointmentMapper;

    @Override
    public Page<AppointmentX> findAllAppointment(Pageable pageable, String username) {

        Page<Appointment> appointments= appointmentRepo.findByPatientUserUsername(
                username, pageable
        );
        return appointments.map(appointmentMapper::appointmentToAppointmentX);
    }
}
