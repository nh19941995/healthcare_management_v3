package org.example.healthcare_management_v2.enums;

import lombok.Getter;

@Getter
public enum FileType {
    JPG(".jpg"),
    PNG(".png"),
    GIF(".gif");

    private final String extension;

    FileType(String extension) {
        this.extension = extension;
    }

    // Phương thức kiểm tra phần mở rộng hợp lệ
    public static boolean isValidExtension(String extension) {
        for (FileType fileType : values()) {
            if (fileType.getExtension().equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }
}

