package com.example.ecom.feanix.api;


import com.example.ecom.feanix.dto.requet.RequestProductDto;
import com.example.ecom.feanix.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public String save(@RequestBody RequestProductDto dto) {
        productService.create(dto);
        return "Saved";
    }
}
