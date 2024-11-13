package org.example.healthcare_management_v2.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "medications")  // dữ liệu thuốc
@SQLDelete(sql = "UPDATE medications SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name; // tên thuốc

    @Column(name = "type", nullable = false)
    private String type; // loại thuốc

    @Column(name = "manufacturer", nullable = false)
    private String manufacturer; // nhà sản xuất

    @Column(name = "dosage", nullable = false)
    private String dosage; // liều lượng

    @Column(name = "instructions", nullable = false)
    private String instructions; // hướng dẫn sử dụng

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


}
