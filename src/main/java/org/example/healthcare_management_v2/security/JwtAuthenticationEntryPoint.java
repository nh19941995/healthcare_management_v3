package org.example.healthcare_management_v2.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
// Đảm bảo rằng các yêu cầu không được xác thực sẽ nhận được phản hồi thích hợp.
// Ngăn chặn truy cập trái phép vào các tài nguyên bảo mật của ứng dụng.
// Cung cấp một cơ chế xử lý lỗi nhất quán cho các trường hợp xác thực thất bại.

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        log.error("Responding with unauthorized error. Message - {}", authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}