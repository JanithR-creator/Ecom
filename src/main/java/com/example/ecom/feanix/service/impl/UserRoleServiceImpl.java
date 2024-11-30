package com.example.ecom.feanix.service.impl;

import com.example.ecom.feanix.entity.UserRole;
import com.example.ecom.feanix.repository.ApplicationUserRoleRepository;
import com.example.ecom.feanix.service.UserRoleService;
import com.example.ecom.feanix.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {
    private final ApplicationUserRoleRepository userRoleRepository;
    private final IdGenerator idGenerator;

    @Override
    public void initializeRoles() {
        List<UserRole> selectedUserRoles = userRoleRepository.findAll();
        if (selectedUserRoles.isEmpty()) {
            UserRole admin = UserRole
                    .builder()
                    .roleId(idGenerator.generate())
                    .roleName("ADMIN")
                    .build();

            UserRole user = UserRole
                    .builder()
                    .roleId(idGenerator.generate())
                    .roleName("USER")
                    .build();

            userRoleRepository.saveAll(List.of(admin, user));
        }
    }
}
