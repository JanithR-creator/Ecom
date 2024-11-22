package com.example.ecom.feanix.api;


import com.example.ecom.feanix.dto.requet.RequestProductDto;
import com.example.ecom.feanix.dto.response.ResponseProductDto;
import com.example.ecom.feanix.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{productId}")
    public ResponseProductDto findById(@PathVariable String productId) {
        return productService.findProductById(productId);
    }

    @PutMapping("/{productId}")
    public String update(@RequestBody RequestProductDto dto, @PathVariable String productId){
        productService.update(dto, productId);
        return "Updated";
    }

    @DeleteMapping("/{productId}")
    public String delete(@PathVariable String productId){
        productService.delete(productId);
        return "Deleted";
    }
}
