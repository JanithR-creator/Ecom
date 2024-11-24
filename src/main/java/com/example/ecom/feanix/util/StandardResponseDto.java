package com.example.ecom.feanix.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StandardResponseDto {
    private String message;
    private int code;
    private Object object;
}
