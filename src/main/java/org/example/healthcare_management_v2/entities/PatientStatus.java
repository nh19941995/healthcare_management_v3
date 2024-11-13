package org.example.healthcare_management_v2.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "patient_status") // bảng trạng thái bệnh nhân
public class PatientStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private String status;

    @Column(name = "description")
    private String description;

    public PatientStatus(String status, String description) {
        this.status = status;
        this.description = description;
    }
}
