package com.example.ecom.feanix.service;

import com.example.ecom.feanix.dto.requet.RequestProductDto;
import com.example.ecom.feanix.dto.response.ResponseProductDto;

public interface ProductService {
    void create(RequestProductDto requestProductDto);
    ResponseProductDto findProductById(String productId);
    void update(RequestProductDto requestProductDto, String productId);
    void delete(String productId);
}
