package org.example.healthcare_management_v2.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.example.healthcare_management_v2.enums.Gender;
import org.example.healthcare_management_v2.enums.Status;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users") // bảng người dùng
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonSerialize(as = Long.class)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be less than 100 characters")
    @Column(name = "full_name")
    private String fullName;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Column(name = "username", unique = true)
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern.List({
            @Pattern(
                    regexp = ".*[A-Z].*",
                    message = "Password must contain at least one uppercase letter"),
            @Pattern(
                    regexp = ".*[a-z].*",
                    message = "Password must contain at least one lowercase letter"),
            @Pattern(
                    regexp = ".*[0-9].*",
                    message = "Password must contain at least one number"),
            @Pattern(
                    regexp = ".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*",
                    message = "Password must contain at least one special character")
    })
    @Column(name = "password")
    private String password;

    @Size(max = 255, message = "Address must be less than 255 characters")
    @Column(name = "address")
    private String address;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number should be valid")
    @Column(name = "phone")
    private String phone;

    @Column(name = "avatar")
    private String avatar;

    @NotNull(message = "Gender is required")
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Size(max = 255, message = "Description must be less than 255 characters")
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "lock_reason")
    private String lockReason;

    @ToString.Exclude
    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = { CascadeType.MERGE }
    )
    @JoinTable(
            // tên bảng trung gian
            name = "user_role",
            // tên cột chứa khóa phụ trong bảng trung gian của User
            joinColumns = @JoinColumn(name = "user_id"),
            // tên cột chứa khóa phụ trong bảng trung gian của Roles
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @ToString.Exclude
    @OneToOne(mappedBy = "user")
    private Doctor doctor;

    @ToString.Exclude
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Patient patient;
}