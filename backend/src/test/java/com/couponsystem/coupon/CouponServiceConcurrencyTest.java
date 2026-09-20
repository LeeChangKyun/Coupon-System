package com.couponsystem.coupon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class CouponServiceConcurrencyTest {

    private static final int TOTAL_QUANTITY = 100;
    private static final int REQUEST_COUNT = 1000;
    private static final Logger log = LoggerFactory.getLogger(CouponServiceConcurrencyTest.class);

    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private CouponEventRepository couponEventRepository;

    private Long eventId;

    // 테스트 시작 전 쿠폰 이벤트 객체 생성 후 리포지토리 저장
    @BeforeEach
    void setUp() {
        CouponEvent couponEvent = new CouponEvent("선착순 쿠폰", "설명", TOTAL_QUANTITY);
        couponEventRepository.save(couponEvent);
        eventId = couponEvent.getId();
    }

    @Test
    void 동시에_수량보다_많은_요청이_와도_정확히_수량만큼만_발급된다() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(REQUEST_COUNT);

        for (long userId = 1; userId <= REQUEST_COUNT; userId++) {
            long currentUserId = userId;
            executorService.submit(() -> {
                try {
                    couponService.issuedCoupon(eventId, currentUserId);
                } catch (Exception ignored) {
                    // 소진/중복 예외는 정상 케이스이므로 무시하고 결과만 검증
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        long issuedCount = couponRepository.count();
        assertThat(issuedCount).isEqualTo(TOTAL_QUANTITY);
        log.info("issuedCount is {}", issuedCount);
    }
}
