package org.example.healthcare_management_v2.map.helper;

import org.example.healthcare_management_v2.service.FileService;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class ClinicMapperHelper {
    private final FileService fileService;

    public ClinicMapperHelper(FileService fileService) {
        this.fileService = fileService;
    }

    @Named("getClinicImage")
    public String getClinicImage(Long clinicId) {
        if (clinicId == null) {
            return null;
        }
        try {
            return fileService.getClinicImage(clinicId);
        } catch (Exception e) {
            return null;
        }
    }
}
