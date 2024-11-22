package com.example.ecom.feanix.service.impl;

import com.example.ecom.feanix.dto.requet.RequestProductDto;
import com.example.ecom.feanix.dto.response.ResponseProductDto;
import com.example.ecom.feanix.entity.Product;
import com.example.ecom.feanix.reository.ProductRepository;
import com.example.ecom.feanix.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
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

    @Override
    public ResponseProductDto findProductById(String productId) {
        Optional<Product> selectedProduct = repository.findById(productId);

        if(selectedProduct.isEmpty()){
            return null;
        }
        return toResponseProductDto(selectedProduct.get());
    }

    public Product toProduct(RequestProductDto dto) {
        if(dto==null)return null;

        return Product.builder()
                .productId(UUID.randomUUID().toString())
                .description(dto.getDescription())
                .fileResource(null)
                .orderDetails(null)
                .qtyOnHand(dto.getQtyOnHand())
                .unitPrice(dto.getUnitPrice())
                .build();
    } // for use to this way it is necessary to use @Builder annotation on Product class

    public ResponseProductDto toResponseProductDto(Product product) {
        if(product==null)return null;

        return ResponseProductDto.builder()
                .productId(product.getProductId())
                .description(product.getDescription())
                .resourceUrl("")
                .qtyOnHand(product.getQtyOnHand())
                .unitPrice(product.getUnitPrice())
                .build();
    }
}
