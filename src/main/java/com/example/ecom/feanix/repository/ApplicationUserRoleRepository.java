package com.example.ecom.feanix.repository;

import com.example.ecom.feanix.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories
public interface ApplicationUserRoleRepository extends JpaRepository<UserRole, String> {
    @Query(value = "SELECT * FROM user_role WHERE role_name=?1 LIMIT 1", nativeQuery = true)
    public Optional<UserRole> findByRoleName(String role);
}
