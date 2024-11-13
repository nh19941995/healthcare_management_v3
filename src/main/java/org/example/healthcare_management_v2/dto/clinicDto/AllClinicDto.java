package org.example.healthcare_management_v2.dto.clinicDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.healthcare_management_v2.dto.doctorDto.Doctor_1;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllClinicDto {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String description;
    private String image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private Set<Doctor_1> doctors;
}
