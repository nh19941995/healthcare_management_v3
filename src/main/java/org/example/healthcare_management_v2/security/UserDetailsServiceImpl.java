package org.example.healthcare_management_v2.security;

import org.example.healthcare_management_v2.entities.User;
import org.example.healthcare_management_v2.enums.Status;
import org.example.healthcare_management_v2.repositories.UserRepo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepository;

    public UserDetailsServiceImpl(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameByAllStatus(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        if (user.getDeletedAt() != null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        if (user.getStatus().equals(Status.LOCKED)) {
            throw new UsernameNotFoundException("User is blocked: " + username);
        }


        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}
