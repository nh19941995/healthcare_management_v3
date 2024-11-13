package org.example.healthcare_management_v2.dto.medicationDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicationDto {
    private Long id;
    private String name; // tên thuốc
    private String type; // loại thuốc
    private String manufacturer; // nhà sản xuất
    private String dosage; // liều lượng sản phẩm
    private String instructions; // hướng dẫn sử dụng
}
