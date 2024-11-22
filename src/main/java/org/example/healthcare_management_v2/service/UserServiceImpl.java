package org.example.healthcare_management_v2.service;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.healthcare_management_v2.dto.userDto.UpdateUserDto;
import org.example.healthcare_management_v2.dto.userDto.UserUpdateDto;
import org.example.healthcare_management_v2.dto.userDto.UserWithDoctorDto;
import org.example.healthcare_management_v2.entities.*;
import org.example.healthcare_management_v2.enums.EnumRole;
import org.example.healthcare_management_v2.enums.Status;
import org.example.healthcare_management_v2.exceptions.BusinessException;
import org.example.healthcare_management_v2.exceptions.ResourceNotFoundException;
import org.example.healthcare_management_v2.map.UserMapper;
import org.example.healthcare_management_v2.repositories.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepository;
    private final RoleRepo roleRepository;
    private final UserMapper userMapper;
    private final DoctorRepo doctorRepo;
    private final PatientRepo patientRepo;
    private final PatientStatusRepo patientStatusRepo;
    private final SpecializationRepo specializationRepo;
    private final ClinicRepo clinicRepo;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(
                        "Username not found",
                        "The username '" + username + "' does not exist. Please check the username and try again.",
                        HttpStatus.NOT_FOUND));
    }

    @Override
    public Page<UserWithDoctorDto> findAll(@NonNull Pageable pageable) {
        // lấy ra tất cả user
        Page<User> userPage = userRepository.findAll(pageable);
        // chuyển đổi từ User sang UserDto
        return userPage.map(userMapper::userToUserWithDoctorDto);
    }

    @Override
    public Page<UserWithDoctorDto> findAllUserInDB(@NonNull Pageable pageable) {
        Page<User> userPage = userRepository.allUserInDb(pageable);
        return userPage.map(userMapper::userToUserWithDoctorDto);
    }

    @Override
    public void checkUsernameExistence(String username) {
        userRepository.findByUsername(username).ifPresent(u -> {
            throw new BusinessException(
                    "Username already exists",
                    "The username '" + username + "' is already taken. Please choose a different username.",
                    HttpStatus.CONFLICT);
        });
    }

    @Override
    public User validateOwnership(Long userId) {
        // Kiểm tra xem người dùng hiện tại có phải là người sở hữu tài khoản không
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Kiểm tra xem người dùng có vai trò admin
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        // lấy ra user theo id
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));

        // chỉ admin hoặc người dùng hiện tại mới có thể cập nhật ảnh đại diện
        if (!currentUsername.equals(currentUser.getUsername()) && !isAdmin)
        {
            throw new RuntimeException("You can update your own profile");
        }

        return currentUser;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new BusinessException("Role not found",
                        "No role found with name: " + roleName,
                        HttpStatus.NOT_FOUND));
        user.getRoles().add(role);
        if (roleName.equals(EnumRole.DOCTOR.getRoleName())) {
            Doctor doctor = new Doctor();
            doctor.setStatus(Status.ACTIVE);
            doctor.setUser(user);
            doctorRepo.save(doctor);
            user.setDoctor(doctor);
        }
        if (roleName.equals(EnumRole.PATIENT.getRoleName())) {
            Patient patient = new Patient();
            PatientStatus patientStatus = patientStatusRepo.findById(1L)
                    .orElseThrow(() -> new BusinessException("Patient status not found",
                            "No patient status found with id: 1",
                            HttpStatus.NOT_FOUND));
            patient.setStatus(patientStatus);
            patient.setUser(user);
            patientRepo.save(patient);
            user.setPatient(new Patient());
        }
        userRepository.save(user);
    }

    @Override
    public void updateProfile(Long userId, UpdateUserDto userDto) {
        // kiểm tra xem user có phải là người sở hữu không
        User oldUser = validateOwnership(userId);

        userMapper.updateUserFromUpdateUserDto(userDto, oldUser);
        userRepository.save(oldUser);
    }

    @Transactional
    @Override
    public void updateProfilev2(UserUpdateDto userDto) {
        User oldUser = validateOwnership(userDto.getId());
        userMapper.updateUserFromUserUpdateDto(userDto, oldUser);
        Doctor doctor = oldUser.getDoctor();
        if (doctor != null) {
             Specialization specialization = specializationRepo.findById(userDto.getSpecializationId())
                    .orElseThrow(() -> new BusinessException("Specialization not found",
                            "No specialization found with id: " + userDto.getSpecializationId(),
                            HttpStatus.NOT_FOUND));
            Clinic clinic = clinicRepo.findById(userDto.getClinicId())
                    .orElseThrow(() -> new BusinessException("Clinic not found",
                            "No clinic found with id: " + userDto.getClinicId(),
                            HttpStatus.NOT_FOUND));

            doctor.setSpecialization(specialization);
            doctor.setClinic(clinic);
            doctor.setAchievements(userDto.getAchievements());
            doctor.setMedicalTraining(userDto.getMedicalTraining());
            doctorRepo.save(doctor);
        }
        userRepository.save(oldUser);
    }

    @Override
    public User blockOrUnblock(String username,String reason) {
        // lấy ra user theo username nếu nó tồn tại trong db
        User user = userRepository.findByUserInDb(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        // nếu user chưa bị xóa thì khóa user
        if (user.getStatus() == Status.ACTIVE) {
            user.setStatus(Status.LOCKED);
            user.setLockReason(reason);
        } else {
            user.setStatus(Status.ACTIVE);
            user.setLockReason(null);
        }
        userRepository.save(user);
        return user;
    }

    @Override
    public void checkStatus(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        if (user.getDeletedAt() != null) {
            throw new BusinessException("User is blocked",
                    "The user '" + username + "' is blocked. Please contact the administrator for more information.",
                    HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public Page<UserWithDoctorDto> findAllUserByStatus( Pageable pageable,String status) {
        return switch (status) {
            case "ALL" -> findAllUserInDB(pageable);
            case "NONDELETED" -> userRepository.findAll(pageable).map(userMapper::userToUserWithDoctorDto);
            case "DELETED" -> userRepository.findAllDeleted(pageable).map(userMapper::userToUserWithDoctorDto);
            default -> userRepository.findByStatus(status, pageable).map(userMapper::userToUserWithDoctorDto);
        };
    }

    @Override
    public void deleteUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        user.setDeletedAt(
                // lấy thời gian hiện tại
                java.time.LocalDateTime.now()
        );
        userRepository.save(user);
    }

    private boolean isPatientOnly(User user) {
        Role patientRole = roleRepository.findByName(EnumRole.PATIENT.getRoleName())
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        return user.getRoles().contains(patientRole) && user.getRoles().size() == 1;
    }
}


