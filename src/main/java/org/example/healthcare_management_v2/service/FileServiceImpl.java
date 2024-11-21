package org.example.healthcare_management_v2.service;

import lombok.extern.slf4j.Slf4j;
import org.example.healthcare_management_v2.entities.User;
import org.example.healthcare_management_v2.enums.FileType;
import org.example.healthcare_management_v2.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.stream.Stream;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    private static final String IMAGE_DATA_FORMAT = "data:image/%s;base64,%s";
    private static final String AVATAR_PREFIX = "_avatar";
    private static final String CLINIC_PREFIX = "clinic_";
    private static final String SPECIALIZATION_PREFIX = "specialization_";
    private final UserRepo userRepository;

    @Value("${file.upload-dir-avatar}")
    private String uploadAvatarDir;

    @Value("${file.upload-dir-clinic}")
    private String uploadClinicDir;

    @Value("${file.upload-dir-specialization}")
    private String uploadSpecializationDir;

    public FileServiceImpl(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String saveAvatar(String username, MultipartFile file) {
        // xác thực file
        validateFile(file);
        // tạo tên file
        String fileName = createFileName(username + AVATAR_PREFIX, getFileExtension(file));
        // lưu file
        return saveFile(uploadAvatarDir, fileName, file);
    }

    @Override
    public String getAvatar(String username) {
        try {
            return getImage(uploadAvatarDir, username + AVATAR_PREFIX);
        }catch (Exception e){
            return getImage(uploadAvatarDir, "default_avatar");
        }
    }

    @Override
    public String getClinicImage(Long clinicId) {
        try {
            return getImage(uploadClinicDir, CLINIC_PREFIX + clinicId);
        }catch (Exception e){
            return getImage(uploadClinicDir, "default_clinic");
        }
    }

    @Override
    public String getSpecializationImage(Long specializationId) {
        try {
            return getImage(uploadSpecializationDir, SPECIALIZATION_PREFIX + specializationId);
        }catch (Exception e){
            return getImage(uploadSpecializationDir, "default_specialization");
        }
    }

    @Override
    public void uploadAvatar(String username, MultipartFile file) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
        // xác thực file
        validateFile(file);
        // tạo tên file
        String fileName = createFileName(username + AVATAR_PREFIX, getFileExtension(file));
        // lưu file
        saveFile(uploadAvatarDir, fileName, file);
        // dường dẫn avatar cho user
        String avatarPath = Paths.get(uploadAvatarDir, fileName).toString();
        user.setAvatar(avatarPath);
        userRepository.save(user);
    }

    // xác thực file
    private void validateFile(MultipartFile file) {
        String extension = getFileExtension(file);
        if (!FileType.isValidExtension(extension)) {
            throw new IllegalArgumentException("File type not allowed. Only JPG, PNG, and GIF files are permitted.");
        }
    }

    // lấy phần mở rộng của file
    private String getFileExtension(MultipartFile file) {
        // lấy tên file gốc
        String originalFileName = file.getOriginalFilename();
        // lấy phần mở rộng của file
        return originalFileName != null && originalFileName.contains(".")
                ? originalFileName.substring(originalFileName.lastIndexOf("."))
                : "";
    }

    // tạo tên file
    private String createFileName(String prefix, String extension) {
        return prefix + extension;
    }

    // lưu file
    private String saveFile(String uploadDir, String fileName, MultipartFile file) {
        try {
            // tạo đường dẫn
            Path uploadPath = Paths.get(uploadDir, fileName);
            // tạo thư mục nếu chưa tồn tại
            Files.createDirectories(uploadPath.getParent());
            // lưu file
            Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
            return uploadPath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", e);
        }
    }

    private String getImage(String directory, String filePrefix) {
        try {
            // tạo đường dẫn
            Path directoryPath = Paths.get(directory);
            // kiểm tra thư mục tồn tại hay không
            validateDirectory(directoryPath);
            // tìm file ảnh theo prefix
            Path imagePath = findImageByPrefix(directoryPath, filePrefix);
            // đọc file ảnh
            byte[] imageBytes = Files.readAllBytes(imagePath);
            // mã hóa file ảnh
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            // lấy phần mở rộng của file ảnh
            String extension = getImageExtension(imagePath);
            // trả về dữ liệu ảnh
            return String.format(IMAGE_DATA_FORMAT, extension, base64Image);
        } catch (IOException e) {
            throw new RuntimeException("Could not get image. Please try again!", e);
        }
    }

    // kiểm tra thư mục tồn tại hay không
    private void validateDirectory(Path directory) throws IOException {
        if (!Files.exists(directory)) {
            throw new IOException("Directory not found: " + directory);
        }
    }

    // tìm file ảnh theo prefix
    private Path findImageByPrefix(Path directory, String prefix) throws IOException {
        try (Stream<Path> stream = Files.list(directory)) {
            return stream
                    .filter(path -> path.getFileName().toString().startsWith(prefix))
                    .findFirst()
                    .orElseThrow(() -> new IOException("Image not found with prefix: " + prefix));
        }
    }


    // lấy phần mở rộng của file ảnh
    private String getImageExtension(Path imagePath) throws IOException {
        String fileName = imagePath.getFileName().toString();
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        throw new IOException("File extension not found for image: " + fileName);
    }
}