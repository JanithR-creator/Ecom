package com.example.ecom.feanix.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "customer_order_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrderDetails {
    @EmbeddedId
    private CustomerOrderHasProductKey hasProductKey = new CustomerOrderHasProductKey();

    @Column(name = "qty", nullable = false)
    private int qty;

    @Column(name = "unit_price", precision = 2)
    private int unitPrice;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(nullable = false, name = "prouct_id")
    private Product product;

    @ManyToOne
    @MapsId("customerOrderId")
    @JoinColumn(nullable = false, name = "customer_order_id")
    private CustomerOrder customerOrder;
}
