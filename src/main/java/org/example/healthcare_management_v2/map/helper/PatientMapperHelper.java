package org.example.healthcare_management_v2.map.helper;

import org.example.healthcare_management_v2.service.FileService;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class PatientMapperHelper {
    private final FileService fileService;

    public PatientMapperHelper(FileService fileService) {
        this.fileService = fileService;
    }

    @Named("getAvatarForPatient")
    public String getAvatarForPatient(String username) {
        if (username == null) {
            return null;
        }
        try {
            return fileService.getAvatar(username);
        } catch (Exception e) {
            return null;
        }
    }

}
