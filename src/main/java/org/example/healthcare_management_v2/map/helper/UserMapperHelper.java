package org.example.healthcare_management_v2.map.helper;

import org.example.healthcare_management_v2.service.FileService;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class UserMapperHelper {
    private final FileService fileService;

    public UserMapperHelper(FileService fileService) {
        this.fileService = fileService;
    }


    @Named("getAvatarForUser")
    public String getAvatarForUser(String username) {
        if (username == null) {
            return null;
        }

        return fileService.getAvatar(username);

    }

}
