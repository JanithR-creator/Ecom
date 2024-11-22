package com.example.ecom.feanix.dto.response;

import com.example.ecom.feanix.entity.FileResource;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseProductDto {
    private String productId;
    private String description;
    private double unitPrice;
    private int qtyOnHand;
    private String resourceUrl;
}
