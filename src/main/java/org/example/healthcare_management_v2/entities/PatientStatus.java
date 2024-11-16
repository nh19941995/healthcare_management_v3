package org.example.healthcare_management_v2.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(
            mappedBy = "status",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    private Set<Patient> patients = new HashSet<>();

    public PatientStatus(String status, String description) {
        this.status = status;
        this.description = description;
    }
}
