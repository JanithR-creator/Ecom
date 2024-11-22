package com.example.ecom.feanix.service.impl;

import com.example.ecom.feanix.dto.requet.RequestProductDto;
import com.example.ecom.feanix.entity.Product;
import com.example.ecom.feanix.reository.ProductRepository;
import com.example.ecom.feanix.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    @Override
    public void create(RequestProductDto requestProductDto) {

       /* Product product = new Product(
                UUID.randomUUID().toString(),
                requestProductDto.getDescription(),
                requestProductDto.getUnitPrice(),
                requestProductDto.getQtyOnHand(),
                null,
                null
        );

        repository.save(product);*/ // this is the typical way for save object.

        repository.save(toProduct(requestProductDto)); //but this is the suitable way
    }

    public Product toProduct(RequestProductDto dto) {
        return Product.builder()
                .productId(UUID.randomUUID().toString())
                .description(dto.getDescription())
                .fileResource(null)
                .orderDetails(null)
                .qtyOnHand(dto.getQtyOnHand())
                .unitPrice(dto.getUnitPrice())
                .build();
    } // for use to this way it is necessary to use @Builder annotation on Product class
}
