package org.example.healthcare_management_v2.map.helper;

import org.example.healthcare_management_v2.service.FileService;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class SpecializationMapperHelper {
    private final FileService fileService;

    public SpecializationMapperHelper(FileService fileService) {
        this.fileService = fileService;
    }

    @Named("getSpecializationImage")
    public String getSpecializationImage(Long specializationId) {
        if (specializationId == null) {
            return null;
        }
        try {
            return fileService.getSpecializationImage(specializationId);
        } catch (Exception e) {
            return null;
        }
    }
}
