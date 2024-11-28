package com.example.ecom.feanix.dto.requet;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestApplicationUserLoginDto {
    private String username;
    private String password;
}
