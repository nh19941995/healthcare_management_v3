package org.example.healthcare_management_v2.entities;


import jakarta.persistence.*;
import lombok.*;
import org.example.healthcare_management_v2.enums.Status;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "doctors")  // bảng bác sĩ
@SQLDelete(sql = "UPDATE doctors SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL and status = 'ACTIVE'")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            fetch = FetchType.EAGER
    )
    // tên cột chứa khóa phụ trong bảng doctors là user_id
    // cột phụ sẽ dc thêm vào bảng doctors
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "achievements")
    private String achievements;

    @Column(name = "medical_training")
    private String medicalTraining;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "lock_reason")
    private String lockReason;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ToString.Exclude
    // mappedBy trỏ tới tên biến doctors trong entity Booking
    @OneToMany(mappedBy = "doctor", cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH
    }, fetch = FetchType.LAZY)
    private Set<Appointment> appointments = new HashSet<>();

//    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    // tên cột chứa khóa phụ trong bảng doctors là clinic_id
    // cột phụ clinic_id sẽ dc thêm vào bảng doctors
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    // tên cột chứa khóa phụ trong bảng doctors là specialization_id
    // cột phụ specialization_id sẽ dc thêm vào bảng doctors
    @JoinColumn(name = "specialization_id")
    private Specialization specialization;

}
