package com.example.ecom.feanix.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
public class CustomerOrderHasProductKey {
    @Column(name = "product_id", length = 80)
    private String productId;

    @Column(name = "customer_order_id", length = 80)
    private String customerOrderId;
}
