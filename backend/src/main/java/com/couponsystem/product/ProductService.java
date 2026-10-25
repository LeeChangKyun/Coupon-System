package com.couponsystem.product;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 전체 상품 조회
    public Page<Product> findAllProduct(String category, Pageable pageable) {
        if (category == null || category.isBlank()) {
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

    // 여러 상품을 쿼리 한 번으로 조회 (없는 ID는 결과에서 빠짐)
    public List<Product> findAllByIds(Collection<Long> ids) {
        return productRepository.findAllById(ids);
    }

}
