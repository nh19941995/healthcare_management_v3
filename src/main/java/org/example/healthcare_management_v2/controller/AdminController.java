package org.example.healthcare_management_v2.controller;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.example.healthcare_management_v2.dto.auth.ApiResponse;
import org.example.healthcare_management_v2.dto.userDto.UserWithDoctorDto;
import org.example.healthcare_management_v2.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    // nâng cấp role cho user
    // url: localhost:8080/admin/updateRole/ababab@A111/ADMIN
    @PutMapping("/updateRole/{userName}/{roleName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> roleUpdate(
            @PathVariable String userName,
            @PathVariable @Pattern(regexp = "^(ADMIN|DOCTOR|PATIENT|RECEPTIONIST)$", message = "Invalid role name") String roleName
    ) {
        userService.addRoleToUser(userName, roleName);
        return ResponseEntity.ok(new ApiResponse(true, "Role updated successfully!"));
    }

    // lấy tất cả user (có phân trang)
    // url: localhost:8080/admin/users?page=0&size=5
    @GetMapping("/users")
    public ResponseEntity<Page<UserWithDoctorDto>> getAllUsersActive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    // lấy tất cả user trong db (có phân trang)
    // url: localhost:8080/admin/allUsersInDb?page=0&size=5
    @GetMapping("/allUsersInDb")
    public ResponseEntity<Page<UserWithDoctorDto>> getAllUsersInDb(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(userService.findAllUserInDB(pageable));
    }



    // khóa hoăc mở khóa user
    // url: localhost:8080/admin/blockOrUnblock/ababab@A111/reason
    @PutMapping("/blockOrUnblock/{username}/{reason}")
    public ResponseEntity<ApiResponse> blockOrUnblock(
            @PathVariable String username,
            @PathVariable String reason
    ) {
        userService.blockOrUnblock(username, reason);
        return ResponseEntity.ok(new ApiResponse(true, "User blocked successfully!"));
    }

}