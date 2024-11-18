package org.example.healthcare_management_v2.service;

import org.example.healthcare_management_v2.dto.userDto.UpdateUserDto;
import org.example.healthcare_management_v2.dto.userDto.UserWithDoctorDto;
import org.example.healthcare_management_v2.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;


public interface UserService {

    // tìm user theo username
    User findByUsername(String username);

    // lấy hết user
    Page<UserWithDoctorDto> findAll(@NonNull Pageable pageable);

    // lấy hết user trong db
    Page<UserWithDoctorDto> findAllUserInDB(@NonNull Pageable pageable);

    // kiểm tra username tồn tại
    void checkUsernameExistence (String username);

    // thêm role cho user
    void addRoleToUser(String username, String roleName);

    // kiểm tra quyền sở hữu
    User validateOwnership (Long userId);

    // cập nhật thông tin user
    public void updateProfile(Long userId, UpdateUserDto userDto);

    // khóa user
    public User blockOrUnblock(String username, String reason);

    // kiểm tra trạng thái user
    void checkStatus(String username);

    // lấy user theo status
    Page<UserWithDoctorDto> findAllUserByStatus( Pageable pageable,String status);

    // xóa user bằng username
    void deleteUserByUsername(String username);

}
