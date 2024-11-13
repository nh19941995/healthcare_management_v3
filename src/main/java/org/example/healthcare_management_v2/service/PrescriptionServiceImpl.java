package org.example.healthcare_management_v2.service;

import lombok.AllArgsConstructor;
import org.example.healthcare_management_v2.dto.prescriptionDto.PrescriptionDto;
import org.example.healthcare_management_v2.dto.prescriptionMedicationDto.PrescriptionMedicationDto;
import org.example.healthcare_management_v2.entities.Appointment;
import org.example.healthcare_management_v2.entities.Medication;
import org.example.healthcare_management_v2.entities.Prescription;
import org.example.healthcare_management_v2.entities.PrescriptionMedication;
import org.example.healthcare_management_v2.exceptions.BusinessException;
import org.example.healthcare_management_v2.exceptions.ResourceNotFoundException;
import org.example.healthcare_management_v2.map.PrescriptionMapper;
import org.example.healthcare_management_v2.repositories.AppointmentRepo;
import org.example.healthcare_management_v2.repositories.MedicationRepo;
import org.example.healthcare_management_v2.repositories.PrescriptionMedicationRepo;
import org.example.healthcare_management_v2.repositories.PrescriptionRepo;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {

    private final AppointmentRepo appointmentRepository;
    private final PrescriptionMedicationRepo prescriptionMedicationRepository;
    private final MedicationRepo medicationRepo;
    private final PrescriptionRepo prescriptionRepository;
    private final PrescriptionMapper prescriptionMapper;

    @Override
    @Transactional
    public void createPrescription(PrescriptionDto prescriptionDto) {
        // tìm cuộc hẹn
        Appointment appointment = appointmentRepository.findById(prescriptionDto.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Appointment", "id", prescriptionDto.getAppointmentId())
                );

        // kiểm tra quyền tạo đơn thuốc
        verifyPrescriptionCreationPermission(appointment);

        // tạo và lưu đơn thuốc
        Prescription prescription = createPrescriptionEntity(prescriptionDto, appointment);

        // tạo và lưu danh sách thuốc trong đơn thuốc
        List<PrescriptionMedication> prescriptionMedications = createPrescriptionMedications(prescriptionDto, prescription);

        // lưu danh sách thuốc trong đơn thuốc
        prescriptionMedicationRepository.saveAll(prescriptionMedications);
    }



    @Override
    public PrescriptionDto getPrescriptionByAppointmentId(Long appointmentId) {
        // goi ra cuoc hen
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", appointmentId));
        // kiểm tra quyền truy cập cuôc hẹn
        verifyPrescriptionAccessPermission(appointment);
        // lấy thông tin đơn thuốc
        Prescription prescription = appointment.getPrescription();
        // chuyển đổi thông tin đơn thuốc sang định dạng DTO
        return prescriptionMapper.prescriptionToPrescriptionDto(prescription);
    }




    // kiểm tra quyền tạo đơn thuốc
    private void verifyPrescriptionCreationPermission(Appointment appointment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        // chỉ bác sĩ hoặc quản trị viên mới có thể tạo đơn thuốc
        if (!appointment.getDoctor().getUser().getUsername().equals(currentUsername) && !isAdmin) {
            throw new AccessDeniedException("Insufficient permissions to create prescription for this appointment");
        }
    }

    // tạo đơn thuốc
    private Prescription createPrescriptionEntity(PrescriptionDto prescriptionDto, Appointment appointment) {
        if (appointment.getPrescription() != null) {
            // Ném BusinessException với thông báo và status HTTP
            throw new BusinessException("Prescription already exists for this appointment.",
                    "A prescription has already been created for this appointment ID.",
                    HttpStatus.BAD_REQUEST);
        }
        Prescription prescription = new Prescription();
        // thiết lập thông tin đơn thuốc vào cuộc hẹn
        prescription.setAppointment(appointment);
        // thiết lập chuẩn đoán bệnh
        prescription.setMedicalDiagnosis(prescriptionDto.getMedicalDiagnosis());
        return prescriptionRepository.save(prescription);
    }

    // tạo danh sách thuốc trong đơn thuốc
    private List<PrescriptionMedication> createPrescriptionMedications(
            PrescriptionDto prescriptionDto, Prescription prescription) {
        return prescriptionDto.getMedications().stream()
                .map(medicationDto -> createPrescriptionMedication(medicationDto, prescription))
                .collect(Collectors.toList());
    }

    // tạo thông tin thuốc trong đơn thuốc
    private PrescriptionMedication createPrescriptionMedication(
            PrescriptionMedicationDto medicationDto, Prescription prescription) {
        Medication medication = medicationRepo.findById(medicationDto.getMedicationId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Medication", "id", medicationDto.getMedicationId())
                );

        return PrescriptionMedication.builder()
                .prescription(prescription)
                .medication(medication)
                .individualizedDosage(medicationDto.getIndividualizedDosage())
                .note(medicationDto.getNote())
                .build();
    }

    // kiểm tra quyền truy cập đơn thuốc
    private void verifyPrescriptionAccessPermission(Appointment appointment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        // chỉ bác sĩ hoặc quản trị viên hoặc bệnh nhân mới có thể truy cập đơn thuốc
        if (
                // bác sĩ cuộc hẹn
                !appointment.getDoctor().getUser().getUsername().equals(currentUsername)
                // bệnh nhân cuộc hẹn
                && !appointment.getPatient().getUser().getUsername().equals(currentUsername)
                // quản trị viên
                && !isAdmin) {
            throw new AccessDeniedException("Insufficient permissions to access prescription for this appointment");
        }
    }
}