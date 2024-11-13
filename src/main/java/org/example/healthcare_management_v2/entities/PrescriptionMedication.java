package org.example.healthcare_management_v2.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "prescription_medications") // bảng chi tiết thuốc trong đơn thuốc
public class PrescriptionMedication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prescription_id", nullable = false)
    private Prescription prescription; // thông tin đơn thuốc

    @ManyToOne
    @JoinColumn(name = "medication_id", nullable = false)
    private Medication medication; // thông tin thuốc

    @Column(name = "individualized_dosage", nullable = false)
    private Long individualizedDosage; // liều lượng cá nhân

    @Column(name = "note")
    private String note;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}
