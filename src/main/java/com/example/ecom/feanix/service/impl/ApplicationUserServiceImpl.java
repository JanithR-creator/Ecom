package com.example.ecom.feanix.service.impl;

import com.example.ecom.feanix.entity.ApplicationUser;
import com.example.ecom.feanix.entity.UserRole;
import com.example.ecom.feanix.exception.EntryNotFoundException;
import com.example.ecom.feanix.reository.ApplicationUserRepository;
import com.example.ecom.feanix.security.SupportSpringApplicationUser;
import com.example.ecom.feanix.service.ApplicationUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        return new SupportSpringApplicationUser(
                selectedUserData.get().getUserName(),
                selectedUserData.get().getPassword(),
                selectedUserData.get().isAccountNonExpired(),
                selectedUserData.get().isAccountNonLocked(),
                selectedUserData.get().isCredentialsNonExpired(),
                selectedUserData.get().isEnabled(),
                grantedAuthorities
        );
    }
}