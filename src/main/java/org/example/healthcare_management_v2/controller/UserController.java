package org.example.healthcare_management_v2.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.healthcare_management_v2.dto.UserDto;
import org.example.healthcare_management_v2.dto.userDto.UpdateUserDto;
import org.example.healthcare_management_v2.entities.User;
import org.example.healthcare_management_v2.map.UserMapper;
import org.example.healthcare_management_v2.repositories.UserRepo;
import org.example.healthcare_management_v2.service.FileService;
import org.example.healthcare_management_v2.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserRepo userRepository;
    private final UserMapper userMapper;
    private final UserService userService;
    private final FileService fileService;

    // lấy thông tin user theo username
    // url: localhost:8080/users/username
    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {

        // Kiểm tra xem người dùng hiện tại có phải là người sở hữu tài khoản không
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Kiểm tra xem người dùng có vai trò admin
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        // chỉ admin hoặc người dùng hiện tại mới có thể cập nhật ảnh đại diện
        if (!currentUsername.equals(username) && !isAdmin)
        {
            throw new RuntimeException("You can view your own profile");
        }

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("User not found: " + username));

        UserDto userDto = userMapper.userToUserDto(user);
        return ResponseEntity.ok(userDto);
    }


    // update theo userId
    // url: localhost:8080/api/users
    @PutMapping("")
    public ResponseEntity<UpdateUserDto> updateUser(
            @RequestBody UpdateUserDto userDto
    ) {
        userService.updateProfile(userDto.getId(), userDto);
        return ResponseEntity.ok(userDto);
    }

    // lấy ảnh avatar của user
    // url: localhost:8080/api/users/avatar/username
    @GetMapping("/avatar/{username}")
    public ResponseEntity<String> getAvatar(@PathVariable String username) {
        return ResponseEntity.ok(fileService.getAvatar(username));
    }

}
