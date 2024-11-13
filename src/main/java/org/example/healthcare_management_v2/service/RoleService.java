package org.example.healthcare_management_v2.service;

import org.example.healthcare_management_v2.entities.Role;

public interface RoleService {
    void checkUserRole(String username, String roleName);

    Role findByName(String name);

}
