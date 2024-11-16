package org.example.healthcare_management_v2.service;

import org.example.healthcare_management_v2.dto.prescriptionDto.PrescriptionDto;
import org.example.healthcare_management_v2.dto.prescriptionDto.PrescriptionSimpleDto;

public interface PrescriptionService {
    void createPrescription(PrescriptionSimpleDto prescriptionDto);

    PrescriptionDto getPrescriptionByAppointmentId(Long appointmentId);
}
