package com.example.ecom.feanix.dto.requet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestProductDto {
    private String description;
    private double unitPrice;
    private int qtyOnHand;
}
