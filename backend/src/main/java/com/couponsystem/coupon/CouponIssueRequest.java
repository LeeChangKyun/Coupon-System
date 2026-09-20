package com.couponsystem.coupon;

public record CouponIssueRequest(
        Long eventId,
        Long userId
) {

}