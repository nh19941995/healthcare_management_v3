package org.example.healthcare_management_v2.entities;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.*;
import org.example.healthcare_management_v2.enums.AppointmentsStatus;
import org.hibernate.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "appointments")  // bảng bác sĩ
@SQLDelete(sql = "UPDATE appointments SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "appointment_date")
    private LocalDate appointmentDate;

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
    private AppointmentsStatus status;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    // tên cột chứa khóa phụ trong bảng bookings là time_slot_id
    // cột phụ time_slot_id sẽ dc thêm vào bảng bookings
    @JoinColumn(name = "time_slot_id")
    private TimeSlot timeSlot;

    @ToString.Exclude
    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL)
    private Prescription prescription; // đơn thuốc

    @ToString.Exclude
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.DETACH
            }
    )
    // tên cột chứa khóa phụ trong bảng bookings là doctor_id
    // cột phụ doctor_id sẽ dc thêm vào bảng bookings
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ToString.Exclude
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.DETACH
            }
    )
    // tên cột chứa khóa phụ trong bảng bookings là patient_id
    // cột phụ patient_id sẽ dc thêm vào bảng bookings
    @JoinColumn(name = "patient_id")
    private Patient patient;



//    // Helper method for Doctor
//    public void setDoctor(Doctor doctor) {
//        if (this.doctor != null) {
//            this.doctor.getAppointments().remove(this);
//        }
//        this.doctor = doctor;
//        if (doctor != null) {
//            doctor.getAppointments().add(this);
//        }
//    }
//
//    // Helper method for Patient
//    public void setPatient(Patient patient) {
//        if (this.patient != null) {
//            this.patient.getAppointments().remove(this);
//        }
//        this.patient = patient;
//        if (patient != null) {
//            patient.getAppointments().add(this);
//        }
//    }
}
