package org.example.healthcare_management_v2.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Slf4j
@AllArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Tắt CSRF
                .cors(Customizer.withDefaults()) // Tắt CORS
                // Xác thực tất cả các request
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                // Tắt quản lý session
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Cấu hình xác thực cho các request
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/login",
                                "/auth/register/**",
                                "/auth/simpleRegister/**",
                                "/api/public/**",
                                "/api/test/**"
                        ).permitAll()
                        // Đặt quy tắc phân quyền cho các endpoint của Spring Data REST

                        // Medication endpoint
                        .requestMatchers(HttpMethod.GET, "/api/medications/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/medications/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/medications/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/medications/**").hasRole("ADMIN")

                        // Consultation endpoint
                        .requestMatchers(HttpMethod.GET, "/api/consultations/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/consultations/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/consultations/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/consultations/**").hasRole("ADMIN")

                        // User endpoint
                        .requestMatchers(HttpMethod.GET, "/api/timeslots/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/timeslots/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/timeslots/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/timeslots/**").hasRole("ADMIN")

                        // User endpoint
                        .requestMatchers(HttpMethod.GET, "/api/users/**").hasAnyRole("ADMIN", "DOCTOR", "PATIENT")
                        .requestMatchers(HttpMethod.POST, "/api/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/users/**").hasAnyRole("ADMIN", "DOCTOR", "PATIENT")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")

                        // Doctor endpoint
                        .requestMatchers(HttpMethod.GET, "/api/doctors/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/doctors/**").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.PUT, "/api/doctors/**").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/doctors/**").hasRole("ADMIN")

                        // Patient endpoint
                        .requestMatchers(HttpMethod.GET, "/api/patients/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/patients/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/patients/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/patients/**").hasRole("ADMIN")

                        // Booking endpoint
                        .requestMatchers(HttpMethod.GET, "/api/appointments/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/appointments/**").hasAnyRole("ADMIN", "DOCTOR", "PATIENT")
                        .requestMatchers(HttpMethod.PUT, "/api/appointments/**").hasRole("PATIENT")
                        .requestMatchers(HttpMethod.DELETE, "/api/appointments/**").hasRole("PATIENT")

                        // clinic endpoint
                        .requestMatchers(HttpMethod.GET, "/api/clinics/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/clinics/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/clinics/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/clinics/**").hasRole("ADMIN")

                        // role endpoint
                        .requestMatchers(HttpMethod.GET, "/api/roles/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/roles/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/roles/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/roles/**").hasRole("ADMIN")

                        // shedule endpoint
                        .requestMatchers(HttpMethod.GET, "/api/schedules/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/schedules/**").hasRole("DOCTOR")
                        .requestMatchers(HttpMethod.PUT, "/api/schedules/**").hasRole("DOCTOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/schedules/**").hasRole("DOCTOR")

                        // specializations endpoint
                        .requestMatchers(HttpMethod.GET, "/api/specializations/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/specializations/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/specializations/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/specializations/**").hasRole("ADMIN")

                        // admin endpoint
                        .requestMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/admin/**").hasRole("ADMIN")

                        // receptionist endpoint
                        .requestMatchers(HttpMethod.GET, "/api/receptionists/**").hasRole("RECEPTIONIST")
                        .requestMatchers(HttpMethod.POST, "/api/receptionists/**").hasRole("RECEPTIONIST")
                        .requestMatchers(HttpMethod.PUT, "/api/receptionists/**").hasRole("RECEPTIONIST")
                        .requestMatchers(HttpMethod.DELETE, "/api/receptionists/**").hasRole("RECEPTIONIST")
                        //  image endpoint
                        .requestMatchers(HttpMethod.GET, "/api/images/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/images/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/images/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/images/**").permitAll()


                        .anyRequest().authenticated()
                )
                // Thêm Filter để xác thực token và set user vào SecurityContext
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Nếu bạn có tài nguyên tĩnh cần bảo vệ, hãy giữ phương thức này
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
    }
}