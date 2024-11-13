package org.example.healthcare_management_v2.controller;

import lombok.AllArgsConstructor;
import org.example.healthcare_management_v2.dto.ClinicDto;
import org.example.healthcare_management_v2.dto.RoleDto;
import org.example.healthcare_management_v2.dto.UserDto;
import org.example.healthcare_management_v2.entities.Clinic;
import org.example.healthcare_management_v2.map.ClinicMapper;
import org.example.healthcare_management_v2.map.DoctorMapper;
import org.example.healthcare_management_v2.map.RoleMapper;
import org.example.healthcare_management_v2.map.UserMapper;
import org.example.healthcare_management_v2.repositories.ClinicRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@AllArgsConstructor
public class TestController {

    private final ClinicMapper clinicMapper;
    private final ClinicRepo clinicRepo;


    // url: localhost:8080/api/test/clinic
    @GetMapping("/clinic")
    public Clinic testClinic() {
        return clinicRepo.findById(1L).orElseThrow();
    }

}
