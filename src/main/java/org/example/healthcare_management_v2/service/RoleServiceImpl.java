package org.example.healthcare_management_v2.service;

import lombok.AllArgsConstructor;
import org.example.healthcare_management_v2.entities.Role;
import org.example.healthcare_management_v2.entities.User;
import org.example.healthcare_management_v2.exceptions.ResourceNotFoundException;
import org.example.healthcare_management_v2.repositories.RoleRepo;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepo;
    private final UserService userService;

    @Override
    public Role findByName(String roleName) {
        return roleRepo.findByName(roleName).orElseThrow(() -> new ResourceNotFoundException("roleName", "roleName", roleName));
    }

    @Override
    public void checkUserRole(String username, String roleName) {
        User user = userService.findByUsername(username);
        Role role = findByName(roleName);
        if (!user.getRoles().contains(role)) {
            throw new ResourceNotFoundException("role", "role", roleName);
        }

    }
}
