package org.example.healthcare_management_v2.config;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.healthcare_management_v2.config.dto_config.DoctorInfoDTO;
import org.example.healthcare_management_v2.config.dto_config.UserCreationDTO;
import org.example.healthcare_management_v2.entities.*;
import org.example.healthcare_management_v2.enums.EnumRole;
import org.example.healthcare_management_v2.enums.Status;
import org.example.healthcare_management_v2.repositories.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DataSetupService {
    private final RoleRepo roleRepo;
    private final SpecializationRepo specializationRepo;
    private final ClinicRepo clinicRepo;
    private final UserRepo userRepo;
    private final DoctorRepo doctorRepo;
    private final PatientRepo patientRepo;
    private final PatientStatusRepo patientStatusRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUserWithRoles(UserCreationDTO userDTO) {
        // Tạo user
        User user = User.builder()
                .fullName(userDTO.getFullName())
                .description(userDTO.getDescription())
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .address(userDTO.getAddress())
                .phone(userDTO.getPhone())
                .gender(userDTO.getGender())
                .avatar(userDTO.getAvatar())
                .status(userDTO.getStatus())
                .build();

        // Set roles cho user
        Set<Role> roles = getRoles(userDTO.getRoleTypes());
        user.setRoles(roles);

        // Lưu user trước
        user = userRepo.save(user);

        // Tạo Doctor nếu có
        if (userDTO.getDoctorInfo() != null) {
            createDoctor(user, userDTO.getDoctorInfo());
        }

        // Tạo Patient nếu có
        if (userDTO.isCreatePatient()) {
            createPatient(user);
        }
    }

    private Set<Role> getRoles(List<EnumRole> roleTypes) {
        Set<Role> roles = new HashSet<>();
        for (EnumRole roleType : roleTypes) {
            Role role = roleRepo.findByName(roleType.getRoleName())
                    .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleType));
            roles.add(role);
        }
        return roles;
    }

    private void createDoctor(User user, DoctorInfoDTO doctorInfo) {
        Doctor doctor = new Doctor();
        doctor.setUser(user);
        doctor.setSpecialization(getSpecialization(doctorInfo.getSpecializationId()));
        doctor.setClinic(getClinic(doctorInfo.getClinicId()));
        doctor.setStatus(Status.ACTIVE);
        doctor.setAchievements(doctorInfo.getAchievements());
        doctor.setMedicalTraining(doctorInfo.getMedicalTraining());
        doctorRepo.save(doctor);
    }

    private void createPatient(User user) {
        Patient patient = new Patient();
        patient.setUser(user);
        PatientStatus status = patientStatusRepo.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException("Patient status not found"));
        patient.setStatus(status);
        patientRepo.save(patient);
    }

    private Specialization getSpecialization(Long id) {
        return specializationRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Specialization not found"));
    }

    private Clinic getClinic(Long id) {
        return clinicRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Clinic not found"));
    }
}
