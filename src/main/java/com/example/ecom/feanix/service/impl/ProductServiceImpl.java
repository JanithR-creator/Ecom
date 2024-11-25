package com.example.ecom.feanix.service.impl;

import com.example.ecom.feanix.dto.paginate.ResponseProductPaginateDto;
import com.example.ecom.feanix.dto.requet.RequestProductDto;
import com.example.ecom.feanix.dto.response.ResponseProductDto;
import com.example.ecom.feanix.entity.Product;
import com.example.ecom.feanix.exception.EntryNotFoundException;
import com.example.ecom.feanix.reository.ProductRepository;
import com.example.ecom.feanix.service.ProductService;
import com.example.ecom.feanix.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final IdGenerator idGenerator;

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

        if (selectedProduct.isEmpty()) {
            //throw new RuntimeException("Product not found"); with exception handle
            throw new EntryNotFoundException("Product not found");
        }
        return toResponseProductDto(selectedProduct.get());
    }

    @Override
    public void update(RequestProductDto requestProductDto, String productId) {
        Optional<Product> selectedProduct = repository.findById(productId);

        if (selectedProduct.isEmpty()) {
            //throw new RuntimeException("Product not found");
            throw new EntryNotFoundException("Product not found");
        }

        Product product = selectedProduct.get();
        product.setDescription(requestProductDto.getDescription());
        product.setUnitPrice(requestProductDto.getUnitPrice());
        product.setQtyOnHand(requestProductDto.getQtyOnHand());

        repository.save(product);
    }

    @Override
    public void delete(String productId) {
        repository.deleteById(productId);
    }

    @Override
    public ResponseProductPaginateDto search(String searchText, int page, int size) {


        return ResponseProductPaginateDto.builder()
                .count(
                        repository.searchCount(searchText)
                )
                .dataList(
                        repository.searchAll(searchText, PageRequest.of(page, size)).
                                stream().map(this::toResponseProductDto).toList()
                ).build();
    }

    public Product toProduct(RequestProductDto dto) {
        if (dto == null) return null;

        return Product.builder()
                .productId(idGenerator.generate())
                .description(dto.getDescription())
                .fileResource(null)
                .orderDetails(null)
                .qtyOnHand(dto.getQtyOnHand())
                .unitPrice(dto.getUnitPrice())
                .build();
    } // for use to this way it is necessary to use @Builder annotation on Product class

    public ResponseProductDto toResponseProductDto(Product product) {
        if (product == null) return null;

        return ResponseProductDto.builder()
                .productId(product.getProductId())
                .description(product.getDescription())
                .resourceUrl("")
                .qtyOnHand(product.getQtyOnHand())
                .unitPrice(product.getUnitPrice())
                .build();
    }
}
