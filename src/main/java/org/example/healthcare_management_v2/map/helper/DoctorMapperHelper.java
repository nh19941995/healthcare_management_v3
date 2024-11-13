package org.example.healthcare_management_v2.map.helper;

import org.example.healthcare_management_v2.service.FileService;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapperHelper {

    private final FileService fileService;

    @Autowired
    public DoctorMapperHelper(FileService fileService) {
        this.fileService = fileService;
    }

    @Named("getAvatarForDoctor")
    public String getAvatarForDoctor(String username) {
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
