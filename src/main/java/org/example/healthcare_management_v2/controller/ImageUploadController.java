package org.example.healthcare_management_v2.controller;

import org.example.healthcare_management_v2.dto.auth.ApiResponse;
import org.example.healthcare_management_v2.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/api/images")
public class ImageUploadController {

    private final FileService fileService;

    @Value("${file.upload-dir-logo}")
    private String UPLOAD_DIR;

    public ImageUploadController(FileService fileService) {
        this.fileService = fileService;
    }

    // url: localhost:8080/api/images/upload
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // Lấy tên gốc của tệp
            String fileName = file.getOriginalFilename();

            // Tạo đường dẫn đầy đủ đến thư mục tải lên
            Path uploadPath = Paths.get(UPLOAD_DIR, fileName);

            // Kiểm tra và tạo thư mục nếu chưa tồn tại
            Files.createDirectories(uploadPath.getParent());

            // Sao chép tệp vào thư mục tải lên
            Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.status(HttpStatus.OK).body("Tải lên tệp thành công: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi tải lên tệp: " + e.getMessage());
        }
    }

    // url: localhost:8080/api/images/download/{userName}
    @GetMapping("/download/{userName}")
    public ResponseEntity<String> downloadImage(@PathVariable("userName") String userName) {
        return ResponseEntity.ok(fileService.getAvatar(userName));
    }

    // url: localhost:8080/api/images/uploadAvatar
    @PostMapping("/uploadAvatar")
    public ResponseEntity<ApiResponse> uploadAvatar(
            @RequestParam("file") MultipartFile file,
            @RequestParam("username") String username
    ) {
        // Kiểm tra xem người dùng hiện tại có phải là người sở hữu tài khoản không
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Kiểm tra xem người dùng có vai trò admin
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        // chỉ admin hoặc người dùng hiện tại mới có thể cập nhật ảnh đại diện
        if (!currentUsername.equals(username) && !isAdmin)
        {
            throw new RuntimeException("You can update your own profile");
        }

        fileService.uploadAvatar(username, file);
        return ResponseEntity.ok(new ApiResponse(true, "Upload avatar successfully"));
    }

}
