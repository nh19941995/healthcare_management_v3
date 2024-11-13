package org.example.healthcare_management_v2.service;

import org.example.healthcare_management_v2.dto.appointmentDto.AppointmentDto;
import org.example.healthcare_management_v2.dto.appointmentDto.AppointmentX;
import org.example.healthcare_management_v2.entities.Appointment;
import org.example.healthcare_management_v2.enums.AppointmentsStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

public interface AppointmentService {

    // Tạo một appointment mới
    AppointmentDto createAppointment(
            String patient_username, String doctor_username, Long timeSlot_id, String appointmentDate);

    // Kiểm tra xem một appointment có tồn tại hay không
    void ckeckAppointment(String doctor_username, Long timeSlot_id, String appointmentDate);

    // Lấy danh sách appointment đang chờ xác nhận
    Page<AppointmentX> findAllPendingAppointment(@NonNull Pageable pageable);

    // đổi trạng thái của appointment
    void confirmAppointment(Long appointment_id, String status);


    // Truy vấn để lấy danh sách Appointment của một Doctor với phân trang
    Page<AppointmentX> findAppointmentsByDoctorUsername(String username, String status, Pageable pageable);
}
