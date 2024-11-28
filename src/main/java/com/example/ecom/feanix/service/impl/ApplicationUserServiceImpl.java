package com.example.ecom.feanix.service.impl;

import com.example.ecom.feanix.dto.requet.RequestUserDto;
import com.example.ecom.feanix.entity.ApplicationUser;
import com.example.ecom.feanix.entity.UserRole;
import com.example.ecom.feanix.exception.DuplicateEntryException;
import com.example.ecom.feanix.exception.EntryNotFoundException;
import com.example.ecom.feanix.repository.ApplicationUserRepository;
import com.example.ecom.feanix.repository.ApplicationUserRoleRepository;
import com.example.ecom.feanix.security.SupportAuthenticationApplicationUser;
import com.example.ecom.feanix.service.ApplicationUserService;
import com.example.ecom.feanix.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.example.ecom.feanix.security.ApplicationUserRole.ADMIN;
import static com.example.ecom.feanix.security.ApplicationUserRole.USER;

@Service
@RequiredArgsConstructor
public class ApplicationUserServiceImpl implements ApplicationUserService {
    private final ApplicationUserRepository userRepository;
    private final IdGenerator idGenerator;
    private final ApplicationUserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ApplicationUser> selectedUserData = userRepository.findByUserName(username);

        if (selectedUserData.isEmpty()) {
            throw new EntryNotFoundException(String.format("username %s not found", username));
        }
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();

        for (UserRole u : selectedUserData.get().getRoles()) {
            if (u.getRoleName().equals("ADMIN")) {
                grantedAuthorities.addAll(ADMIN.grantedAuthorities());
            }
            if (u.getRoleName().equals("USER")) {
                grantedAuthorities.addAll(USER.grantedAuthorities());
            }
        }
        return new SupportAuthenticationApplicationUser(
                selectedUserData.get().getUserName(),
                selectedUserData.get().getPassword(),
                selectedUserData.get().isAccountNonExpired(),
                selectedUserData.get().isAccountNonLocked(),
                selectedUserData.get().isCredentialsNonExpired(),
                selectedUserData.get().isEnabled(),
                grantedAuthorities
        );
    }

    @Override
    public void create(RequestUserDto dto) {
        Optional<ApplicationUser> selectedUser = userRepository.findByUserName(dto.getUserName());
        if (selectedUser.isPresent()) {
            throw new DuplicateEntryException(String.format("User %s is exists", dto.getUserName()));
        }
        userRepository.save(createApplicationUser(dto));
    }

    public ApplicationUser createApplicationUser(RequestUserDto dto) {
        if (dto == null) {
            throw new RuntimeException("Something went wrong...");
        }

        Optional<UserRole> selectedUserRole = userRoleRepository.findByRoleName("USER");

        if (selectedUserRole.isEmpty()) {
            throw new EntryNotFoundException("Role not found");
        }

        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(selectedUserRole.get());

        return ApplicationUser.builder()
                .userId(idGenerator.generate())
                .userName(dto.getUserName())
                .password(passwordEncoder.encode(dto.getPassword()))
                .address(dto.getAddress())
                .roles(userRoles)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .build();
    }
}
