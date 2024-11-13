package org.example.healthcare_management_v2.controller;

import lombok.AllArgsConstructor;
import org.example.healthcare_management_v2.dto.TimeSlotDto;
import org.example.healthcare_management_v2.entities.TimeSlot;
import org.example.healthcare_management_v2.map.TimeSlotMapper;
import org.example.healthcare_management_v2.repositories.TimeSlotRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/timeslots")
@AllArgsConstructor
public class TimeslotController {

    private final TimeSlotRepo timeSlotRepo;
    private TimeSlotMapper timeSlotMapper;


    // lay danh sách các time slot
    // url: localhost:8080/api/timeslots
    @GetMapping("")
    public ResponseEntity<List<TimeSlotDto>> getAllTimeSlots() {
        List<TimeSlot> timeSlots = timeSlotRepo.findAll();
        List<TimeSlotDto> timeSlotDtos = timeSlots.stream()
                .map(timeSlotMapper::timeSlotToTimeSlotDto)
                .toList();
        return ResponseEntity.ok(timeSlotDtos);
    }
}
