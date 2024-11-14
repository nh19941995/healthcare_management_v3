package org.example.healthcare_management_v2.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.example.healthcare_management_v2.dto.doctorDto.DoctorProfileDto;
import org.example.healthcare_management_v2.dto.doctorDto.Doctor_1;
import org.example.healthcare_management_v2.dto.doctorDto.UpdateDoctorDto;
import org.example.healthcare_management_v2.entities.Clinic;
import org.example.healthcare_management_v2.entities.Doctor;
import org.example.healthcare_management_v2.entities.User;
import org.example.healthcare_management_v2.enums.EnumRole;
import org.example.healthcare_management_v2.exceptions.BusinessException;
import org.example.healthcare_management_v2.map.DoctorMapper;
import org.example.healthcare_management_v2.repositories.ClinicRepo;
import org.example.healthcare_management_v2.repositories.DoctorRepo;
import org.example.healthcare_management_v2.repositories.SpecializationRepo;
import org.example.healthcare_management_v2.repositories.UserRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final UserRepo userRepository;
    private final ClinicRepo clinicRepository;
    private final SpecializationRepo specializationRepository;
    private final DoctorRepo doctorRepository;
    private final DoctorMapper doctorMapper;
    private final RoleService roleService;

    @Override
    public UpdateDoctorDto updateProfile(UpdateDoctorDto doctorDto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Doctor existingDoctor = user.getDoctor();
        if (existingDoctor != null) {

            existingDoctor.setAchievements(doctorDto.getAchievements());
            existingDoctor.setMedicalTraining(doctorDto.getMedicalTraining());
            existingDoctor.getUser().setAvatar(doctorDto.getAvatar());
            // Nếu id của clinic khác với id của clinic hiện tại
            if (!doctorDto.getClinicId().equals(existingDoctor.getClinic().getId()) ) {
                Clinic clinic = clinicRepository.findById(doctorDto.getClinicId())
                        .orElseThrow(() -> new RuntimeException("Clinic not found"));
                existingDoctor.setClinic(clinic);
            }
            // Nếu id của specialization khác với id của specialization hiện tại
            if (!doctorDto.getSpecializationId().equals(existingDoctor.getSpecialization().getId())) {
                // Lưu thông tin specialization
                existingDoctor.setSpecialization(specializationRepository.findById(doctorDto.getSpecializationId())
                        .orElseThrow(() -> new RuntimeException("Specialization not found")));
            }

            user.setFullName(doctorDto.getFullName());
            user.setPhone(doctorDto.getPhone());
            user.setEmail(doctorDto.getEmail());
            user.setAddress(doctorDto.getAddress());
            user.setGender(doctorDto.getGender());
            user.setDescription(doctorDto.getDescription());

            // Lưu thông tin bác sĩ
            doctorRepository.save(existingDoctor);
            return doctorDto;
        }
        return null;
    }

    @Override
    public DoctorProfileDto getDoctorProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Doctor doctor = user.getDoctor();

        if (doctor == null) {
            throw new RuntimeException("User is not a doctor");
        }

        return doctorMapper.doctorToDoctorProfileDto(doctor);
    }

    @Override
    public Doctor findByUsername(String username) {
        // Tìm user theo username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(
                        "Username not found",
                        "The username '" + username + "' does not exist. Please check the username and try again.",
                        HttpStatus.NOT_FOUND));
        // Kiểm tra xem user có phải là bác sĩ không
        roleService.checkUserRole(username, EnumRole.DOCTOR.name());
        return user.getDoctor();

    }

    @Override
    public Page<Doctor_1> findAll(@NonNull Pageable pageable) {
        return doctorRepository.findAll(pageable)
                .map(doctorMapper::doctorToDoctor_1);
    }




}
