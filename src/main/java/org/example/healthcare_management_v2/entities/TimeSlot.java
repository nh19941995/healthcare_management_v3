package org.example.healthcare_management_v2.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "time_slots") // bảng thời gian
@ToString
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_at", nullable = false)
    private LocalTime startAt;

    @Column(name = "end_at", nullable = false)
    private LocalTime endAt;

    @JsonIgnore
    @OneToMany(
            mappedBy = "timeSlot",
            // chỉ áp dụng với lưu và cập nhật
            // xóa timeSlot sẽ không ảnh hưởng đến booking
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    private Set<Appointment> appointments = new HashSet<>();


}
