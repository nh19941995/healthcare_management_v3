package org.example.healthcare_management_v2.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.healthcare_management_v2.dto.appointmentDto.AppointmentDto;
import org.example.healthcare_management_v2.dto.appointmentDto.AppointmentX;
import org.example.healthcare_management_v2.entities.Appointment;
import org.example.healthcare_management_v2.entities.Doctor;
import org.example.healthcare_management_v2.entities.TimeSlot;
import org.example.healthcare_management_v2.entities.User;
import org.example.healthcare_management_v2.enums.AppointmentsStatus;
import org.example.healthcare_management_v2.map.AppointmentMapper;
import org.example.healthcare_management_v2.repositories.AppointmentRepo;
import org.example.healthcare_management_v2.repositories.TimeSlotRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepo appointmentRepository;
    private final UserService userService;
    private final DoctorService doctorService;
    private final TimeSlotRepo timeSlotRepo;
    private final AppointmentMapper appointmentMapper;


    @Override
    public AppointmentDto createAppointment(
            String patient_username, String doctor_username, Long timeSlot_id, String appointmentDate) {
        // kiểm tra xem có ai đặt hẹn vào thời gian này chưa
        ckeckAppointment(doctor_username, timeSlot_id, appointmentDate);
         // bắt đầu tạo cuộc hẹn
        User patient = userService.findByUsername(patient_username);
        Doctor doctor = doctorService.findByUsername(doctor_username);

        TimeSlot timeSlot = timeSlotRepo.findById(timeSlot_id)
                .orElseThrow(() -> new RuntimeException("Time slot not found"));
        // tạo appointmentDate từ string
        Appointment appointment =  new Appointment();
        LocalDate date = LocalDate.parse(appointmentDate);
        // set trạng thái của cuộc hẹn là PENDING
        appointment.setStatus(AppointmentsStatus.PENDING);
        // set ngày hẹn
        appointment.setAppointmentDate(date);
        // set bác sĩ và bệnh nhân
        appointment.setPatient(patient.getPatient());
        appointment.setDoctor(doctor);
        // set time slot
        appointment.setTimeSlot(timeSlot);
        // lưu cuộc hẹn
        appointmentRepository.save(appointment);
        return appointmentMapper.appointmentToAppointmentDto(appointment);
    }

    @Override
    public void ckeckAppointment(String doctor_username, Long timeSlot_id, String appointmentDate) {
        // lấy ra bác sĩ
        Doctor doctor = doctorService.findByUsername(doctor_username);
        // tạo giờ hẹn từ string
        LocalDate date = LocalDate.parse(appointmentDate);
        // tìm cuộc hẹn xem có ai đặt hẹn vào thời gian này không
        Optional<Appointment> existingAppointment =
                appointmentRepository
                        .findByAppointmentDateAndTimeSlotIdAndDoctorId(date, timeSlot_id, doctor.getId());
        // nếu có người đặt hẹn rồi thì thông báo lỗi
        if (existingAppointment.isPresent()) {
            throw new RuntimeException("Appointment already booked for this time slot and doctor");
        }
    }

    @Transactional
    @Override
    public Page<AppointmentX> findAllPendingAppointment(@NonNull Pageable pageable) {
        return appointmentRepository.findAllByStatus(AppointmentsStatus.PENDING, pageable)
                .map(appointmentMapper::appointmentToAppointmentX);
    }

    @Override
    public void confirmAppointment(Long appointment_id, String status) {
        Appointment appointment = appointmentRepository.findById(appointment_id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + appointment_id));

        // Kiểm tra xem status truyền vào có hợp lệ hay không (nếu cần)
        if (status != null && AppointmentsStatus.isValidStatus(status)) {
            appointment.setStatus(AppointmentsStatus.valueOf(status));
        } else {
            throw new RuntimeException("Invalid status name");
        }

        // Lưu lại thông tin appointment sau khi thay đổi
        appointmentRepository.save(appointment);
    }

    @Override
    public Page<AppointmentX> findAppointmentsByDoctorUsername(
            String username, String status, Pageable pageable
    ) {
        AppointmentsStatus appointmentStatus = AppointmentsStatus.valueOf(status);
        return appointmentRepository.findAppointmentsByDoctorUsername(username,appointmentStatus, pageable)
                .map(appointmentMapper::appointmentToAppointmentX);
    }

}
