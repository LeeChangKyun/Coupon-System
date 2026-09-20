package com.couponsystem.product;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    // 상품 조회
//    public ResponseEntity<Product> findById(Long id) {
//
//    }
}
