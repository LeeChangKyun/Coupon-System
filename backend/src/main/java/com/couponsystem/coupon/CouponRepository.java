package com.couponsystem.coupon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    boolean existsByCouponEventIdAndUserId(Long couponEventId, Long userId);

}
