package com.couponsystem.product;

import com.couponsystem.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<PageResponse<ProductResponse>> findAll(
            @RequestParam(required = false) String category,
            @PageableDefault(size = 15) Pageable pageable
    ) {
        Page<ProductResponse> products = productService.findAllProduct(category, pageable)
                .map(ProductResponse::from);
        return ResponseEntity.ok(PageResponse.from(products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ProductResponse.from(productService.findById(id)));
    }
}
