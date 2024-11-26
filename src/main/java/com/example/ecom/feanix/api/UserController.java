package com.example.ecom.feanix.api;

import com.example.ecom.feanix.dto.requet.RequestUserDto;
import com.example.ecom.feanix.service.ApplicationUserService;
import com.example.ecom.feanix.util.StandardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
    private final ApplicationUserService userService;

    @PostMapping("/signup")
    public ResponseEntity<StandardResponseDto> signup(@RequestBody RequestUserDto dto) {
        userService.create(dto);
        return new ResponseEntity<>(
                new StandardResponseDto("User created", 201, dto.getUserName()),
                HttpStatus.CREATED
        );
    }
}
