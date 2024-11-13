package org.example.healthcare_management_v2.security;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.healthcare_management_v2.entities.Patient;
import org.example.healthcare_management_v2.entities.Role;
import org.example.healthcare_management_v2.entities.User;
import org.example.healthcare_management_v2.enums.EnumRole;
import org.example.healthcare_management_v2.enums.Status;
import org.example.healthcare_management_v2.exceptions.BusinessException;
import org.example.healthcare_management_v2.repositories.PatientRepo;
import org.example.healthcare_management_v2.repositories.PatientStatusRepo;
import org.example.healthcare_management_v2.repositories.RoleRepo;
import org.example.healthcare_management_v2.repositories.UserRepo;
import org.example.healthcare_management_v2.service.FileService;
import org.example.healthcare_management_v2.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class AuthService {
    private final PatientRepo patientRepository;
    private final UserService userService;
    // dùng để thực hiện các thao tác liên quan đến User
    private final UserRepo userRepository;
    // dùng để mã hóa mật khẩu
    private final PasswordEncoder passwordEncoder;
    // dùng để tạo và kiểm tra token
    private final JwtTokenUtil jwtTokenUtil;
    // dùng để load thông tin User
    private final UserDetailsService userDetailsService;
    // dùng để thực hiện xác thực người dùng
    private final AuthenticationManager authenticationManager;
    private final RoleRepo roleRepository;
    private final PatientStatusRepo patientStatusRepo;
    private final FileService fileService;

    // đăng nhập
    public String login(String username, String password) throws Exception {
        log.info("Login request for user: {}", username);
        try {
            // xác thực người dùng với username và password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            // Nếu xác thực thành công, set authentication vào SecurityContext.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (DisabledException e) {
            // nếu tài khoản bị vô hiệu hóa
            throw new LockedException("", e);
        } catch (BadCredentialsException e) {
            // nếu username hoặc password không đúng
            throw new BadCredentialsException("", e);
        }
        // xác thực thành công, tạo token và trả về cho người dùng
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }

    // đăng ký tài khoản
    @Transactional
    public void register(User user, MultipartFile avatar) {
        try {
            log.info("Service - Register request for user: {}", user);

            // Kiểm tra xem username đã tồn tại chưa
            userService.checkUsernameExistence(user.getUsername());

            // Đặt trạng thái tài khoản là ACTIVE và mã hóa mật khẩu
            user.setStatus(Status.ACTIVE);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Tạo Patient và liên kết với User
            Patient patient = Patient.builder()  // Sử dụng builder nếu có
                    .user(user)
                    .status(
                            patientStatusRepo.getReferenceById(4L)  // Lấy trạng thái ACTIVE
                    )       // Thêm trạng thái cho Patient nếu cần
                    .build();
            user.setPatient(patient);

            // Tạo vai trò bệnh nhân cho người dùng
            Role role = roleRepository.findByName(EnumRole.PATIENT.getRoleName())
                    .orElseThrow(() -> new BusinessException("Role not found",
                            "No role found with name: " + EnumRole.PATIENT.getRoleName(),
                            HttpStatus.NOT_FOUND));
            user.setRoles(Set.of(role));

            String urlImage = fileService.saveAvatar(user.getUsername(), avatar);
            user.setAvatar(urlImage);
            userRepository.save(user);
            patientRepository.save(patient);

        } catch (BusinessException e) {
            log.error("Service - Register failed: {}", e.getMessage());
            throw new BusinessException("Registration failed", e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // đăng ký tài khoản ko anh
    @Transactional
    public void register(User user) {
        try {
            log.info("Service - Register request for user: {}", user);

            // Kiểm tra xem username đã tồn tại chưa
            userService.checkUsernameExistence(user.getUsername());

            // Đặt trạng thái tài khoản là ACTIVE và mã hóa mật khẩu
            user.setStatus(Status.ACTIVE);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Tạo Patient và liên kết với User
            Patient patient = Patient.builder()  // Sử dụng builder nếu có
                    .user(user)
                    .status(
                            patientStatusRepo.getReferenceById(4L)  // Lấy trạng thái ACTIVE
                    )       // Thêm trạng thái cho Patient nếu cần
                    .build();
            user.setPatient(patient);

            // Tạo vai trò bệnh nhân cho người dùng
            Role role = roleRepository.findByName(EnumRole.PATIENT.getRoleName())
                    .orElseThrow(() -> new BusinessException("Role not found",
                            "No role found with name: " + EnumRole.PATIENT.getRoleName(),
                            HttpStatus.NOT_FOUND));
            user.setRoles(Set.of(role));

            userRepository.save(user);
            patientRepository.save(patient);

        } catch (BusinessException e) {
            log.error("Service - Register failed: {}", e.getMessage());
            throw new BusinessException("Registration failed", e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
