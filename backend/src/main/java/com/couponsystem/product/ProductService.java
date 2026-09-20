package com.couponsystem.product;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 전체 상품 조회
    public Page<Product> findAllProduct(String category, Pageable pageable) {
        if (category == null) {
            return productRepository.findAll(pageable);
        }
        return findByCategory(category, pageable);
    }

    // 상품 상세 조회
    public Product findById(Long id) {
        return productRepository.findById(id).
                orElseThrow(() -> new NotExistsProductException("존재하지 않는 상품입니다."));
    }

    // 카테고리별 상품 조회
    public Page<Product> findByCategory(String category, Pageable pageable) {
        return productRepository.findByCategory(category, pageable);
    }

}
