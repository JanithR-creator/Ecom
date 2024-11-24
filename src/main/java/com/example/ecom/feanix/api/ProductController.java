package com.example.ecom.feanix.api;


import com.example.ecom.feanix.dto.requet.RequestProductDto;
import com.example.ecom.feanix.service.ProductService;
import com.example.ecom.feanix.util.StandardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<StandardResponseDto> save(@RequestBody RequestProductDto dto) {
        productService.create(dto);
        return new ResponseEntity<>(
                new StandardResponseDto("Product has been saved...", 201, null),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{productId}")
    public ResponseEntity<StandardResponseDto> findById(@PathVariable String productId) {

        return new ResponseEntity<>(
                new StandardResponseDto("Selected product", 200, productService.findProductById(productId)),
                HttpStatus.OK
        );
    }

    @PutMapping("/{productId}")
    public ResponseEntity<StandardResponseDto> update(@RequestBody RequestProductDto dto, @PathVariable String productId) {
        productService.update(dto, productId);
        return new ResponseEntity<>(
                new StandardResponseDto("Product has been modified..", 201, null),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<StandardResponseDto> delete(@PathVariable String productId) {
        productService.delete(productId);
        return new ResponseEntity<>(
                new StandardResponseDto("Product has been deleted..", 204, null),
                HttpStatus.OK
        );
    }

    @GetMapping("/search")
    public ResponseEntity<StandardResponseDto> search(
            @RequestParam String searchText,
            @RequestParam int page,
            @RequestParam int size
    ) {
        return new ResponseEntity<>(
                new StandardResponseDto("Product List", 200, productService.search(searchText, page, size)),
                HttpStatus.OK
        );
    }
}
