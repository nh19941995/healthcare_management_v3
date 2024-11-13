package org.example.healthcare_management_v2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotDto {
    private Long id;
    private LocalTime startAt;
    private LocalTime endAt;
}
