package com.example.ecom.feanix.service;

import com.example.ecom.feanix.dto.requet.RequestUserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface ApplicationUserService extends UserDetailsService {
    void create(RequestUserDto dto);
    void initializeAdmin();
}
