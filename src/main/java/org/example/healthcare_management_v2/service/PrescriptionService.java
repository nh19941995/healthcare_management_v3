package org.example.healthcare_management_v2.service;

import org.example.healthcare_management_v2.dto.prescriptionDto.PrescriptionDto;

public interface PrescriptionService {
    void createPrescription(PrescriptionDto prescriptionDto);
}
