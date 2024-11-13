package org.example.healthcare_management_v2.enums;

public enum AppointmentsStatus {
    PENDING,    // đang chờ
    CONFIRMED,  // đã xác nhận
    COMPLETED,  // đã hoàn thành
    CANCELLED,  // đã hủy
    NO_SHOW,    // không đến
    RESCHEDULED; // đã đổi lịch

    // Phương thức để kiểm tra trạng thái có hợp lệ không
    public static boolean isValidStatus(String status) {
        // Kiểm tra xem status có tồn tại trong enum hay không
        try {
            AppointmentsStatus.valueOf(status); // Nếu status hợp lệ, sẽ không có lỗi
            return true;
        } catch (IllegalArgumentException e) {
            return false; // Trả về false nếu status không hợp lệ
        }
    }
}

