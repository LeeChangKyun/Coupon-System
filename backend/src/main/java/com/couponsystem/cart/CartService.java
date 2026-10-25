package com.couponsystem.cart;

import com.couponsystem.product.Product;
import com.couponsystem.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartService {

    private static final String CART_KEY_PREFIX = "cart:";

    private final StringRedisTemplate redisTemplate;
    private final ProductService productService;

    private String cartKey(Long userId) {
        return CART_KEY_PREFIX + userId;
    }

    public void addItem(Long userId, Long productId, int quantity) {
        productService.findById(productId);
        // HINCRBY: 이미 담긴 수량에 원자적으로 더한다
        redisTemplate.opsForHash().increment(cartKey(userId), String.valueOf(productId), quantity);
    }

    public void updateItem(Long userId, Long productId, int quantity) {
        String key = cartKey(userId);
        String field = String.valueOf(productId);
        if (!redisTemplate.opsForHash().hasKey(key, field)) {
            throw new NotExistsCartItemException("장바구니에 없는 상품입니다.");
        }
        redisTemplate.opsForHash().put(key, field, String.valueOf(quantity));
    }

    public void removeItem(Long userId, Long productId) {
        redisTemplate.opsForHash().delete(cartKey(userId), String.valueOf(productId));
    }

    public CartResponse getCart(Long userId) {
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(cartKey(userId));

        Map<Long, Integer> quantities = new HashMap<>();
        entries.forEach((productId, quantity) ->
                quantities.put(Long.valueOf(productId.toString()), Integer.valueOf(quantity.toString())));

        // 가격/이름은 Redis에 저장하지 않고, 조회 시점의 최신 값을 DB에서 한 번에 가져온다
        List<Product> products = productService.findAllByIds(quantities.keySet());

        List<CartItemResponse> items = products.stream()
                .map(product -> CartItemResponse.of(product, quantities.get(product.getId())))
                .sorted(Comparator.comparing(CartItemResponse::productId))
                .toList();

        return CartResponse.from(items);
    }
}
