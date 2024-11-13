package org.example.healthcare_management_v2.service;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.healthcare_management_v2.dto.userDto.UpdateUserDto;
import org.example.healthcare_management_v2.dto.userDto.UserWithDoctorDto;
import org.example.healthcare_management_v2.entities.Role;
import org.example.healthcare_management_v2.entities.User;
import org.example.healthcare_management_v2.enums.EnumRole;
import org.example.healthcare_management_v2.exceptions.BusinessException;
import org.example.healthcare_management_v2.exceptions.ResourceNotFoundException;
import org.example.healthcare_management_v2.map.UserMapper;
import org.example.healthcare_management_v2.repositories.RoleRepo;
import org.example.healthcare_management_v2.repositories.UserRepo;
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
    private final FileService fileService;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + currentUsername));

        if (isPatientOnly(currentUser) && !currentUser.getId().equals(userId)) {
            throw new AccessDeniedException("You can only action your own profile");
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
        userRepository.save(user);
    }

    @Override
    public void updateProfile(Long userId, UpdateUserDto userDto) {
        // kiểm tra xem user có phải là người sở hữu không
        User oldUser = validateOwnership(userId);

        userMapper.updateUserFromUpdateUserDto(userDto, oldUser);
        userRepository.save(oldUser);
    }

    @Override
    public void blockOrUnblock(String username,String reason) {
        User user = userRepository.findByUsernameAndDeletedAtIsNotNull(username)
                .orElse(
                        userRepository.findByUsername(username)
                                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username))
                );
        user.setDeletedAt(
                user.getDeletedAt() == null ? java.time.LocalDateTime.now() : null
        );
        user.setLockReason(reason);
        userRepository.save(user);
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

    private boolean isPatientOnly(User user) {
        Role patientRole = roleRepository.findByName(EnumRole.PATIENT.getRoleName())
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        return user.getRoles().contains(patientRole) && user.getRoles().size() == 1;
    }
}