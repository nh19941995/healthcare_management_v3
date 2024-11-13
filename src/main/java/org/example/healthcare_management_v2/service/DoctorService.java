package org.example.healthcare_management_v2.service;


import org.example.healthcare_management_v2.dto.doctorDto.DoctorProfileDto;
import org.example.healthcare_management_v2.dto.doctorDto.Doctor_1;
import org.example.healthcare_management_v2.dto.doctorDto.UpdateDoctorDto;
import org.example.healthcare_management_v2.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

public interface DoctorService {
    // Cập nhật thông tin của Doctor
    UpdateDoctorDto updateProfile(UpdateDoctorDto doctorDto, String username);

    // Lấy thông tin của Doctor theo username
    DoctorProfileDto getDoctorProfile(String username);

    // Lấy thông tin của Doctor theo username
    Doctor findByUsername(String username);

    // Lấy tất cả Doctor (có phân trang)
    Page<Doctor_1> findAll(@NonNull Pageable pageable);

}
