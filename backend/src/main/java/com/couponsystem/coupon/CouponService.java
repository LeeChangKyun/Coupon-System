package com.couponsystem.coupon;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponEventRepository couponEventRepository;

    @Transactional
    public void issuedCoupon(Long eventId, Long userId) {
        boolean issuedCoupon = couponRepository.existsByCouponEventIdAndUserId(eventId, userId);
        if (issuedCoupon) {
            throw new AlreadyIssuedException("이미 쿠폰을 발급받았습니다.");
        }
        checkCoupon(eventId, userId);
    }

    private void checkCoupon(Long eventId, Long userId) {
        CouponEvent couponEvent = couponEventRepository.findByIdForUpdate(eventId).
                orElseThrow(() -> new NotExistCouponEventException("존재 하지 않는 이벤트입니다."));

        if (couponEvent.getTotalQuantity() == couponEvent.getIssuedQuantity()) {
            throw new CouponSoldOutException("더 이상 쿠폰을 발급 받을 수 없습니다.");
        }
        createCoupon(couponEvent, userId);
    }

    private void createCoupon(CouponEvent couponEvent, Long userId) {
        // 쿠폰 발급 수량 증가
        couponEvent.createCouponEvent();
        couponEventRepository.save(couponEvent);
        couponRepository.save(new Coupon(couponEvent.getId(), userId));
    }
}
