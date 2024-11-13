package org.example.healthcare_management_v2.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String saveAvatar(String fileName, MultipartFile file);
    String getAvatar(String username);
    String getClinicImage(Long clinicId);
    String getSpecializationImage(Long specializationId);
    void uploadAvatar(String username, MultipartFile file);
}
