package org.example.healthcare_management_v2.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.healthcare_management_v2.dto.auth.ApiResponse;
import org.example.healthcare_management_v2.dto.auth.LoginRequest;
import org.example.healthcare_management_v2.dto.auth.RegisterDto;
import org.example.healthcare_management_v2.entities.User;
import org.example.healthcare_management_v2.map.UserMapper;
import org.example.healthcare_management_v2.security.AuthService;
import org.example.healthcare_management_v2.security.JwtResponse;
import org.example.healthcare_management_v2.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/auth")
@Slf4j
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserMapper userMapper;
    private final FileService fileService;


    // url: localhost:8080/auth/login
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {
        log.info("Controller - Login request: {}", loginRequest);
        String token = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    // dùng để đăng ký user
    // url: localhost:8080/auth/register
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> register(
            @ModelAttribute RegisterDto registerDto,
            @RequestParam("avatar") MultipartFile avatar) {

        log.info("Controller - Register request: {}", registerDto);

        // Ánh xạ từ `RegisterDto` sang `User`
        User user = userMapper.RegisterDtoToUser(registerDto);

        // Đăng ký user
        authService.register(user,avatar);

        // Trả về phản hồi thành công
        ApiResponse apiResponse = new ApiResponse(true, "Registration successful!");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    // dùng để đăng ký user không có ảnh đại diện
    // url: localhost:8080/auth/simpleRegister
    @PostMapping(value = "/simpleRegister")
    public ResponseEntity<ApiResponse> registerNoImage(
            @RequestBody RegisterDto registerDto) {
        log.info("Controller - Register request: {}", registerDto);

        // Ánh xạ từ `RegisterDto` sang `User`
        User user = userMapper.RegisterDtoToUser(registerDto);

        // Đăng ký user
        authService.register(user);

        // Trả về phản hồi thành công
        ApiResponse apiResponse = new ApiResponse(true, "Registration successful!");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

}
