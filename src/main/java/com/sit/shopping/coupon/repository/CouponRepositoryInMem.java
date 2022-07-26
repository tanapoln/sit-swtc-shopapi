package com.sit.shopping.coupon.repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import com.sit.shopping.coupon.model.Coupon;
import com.sit.shopping.exception.InvalidCouponException;

@Repository
public class CouponRepositoryInMem implements CouponRepository, InitializingBean {
	private final Map<String, Coupon> couponMap = new HashMap<>();

	public CouponRepositoryInMem() {
	}

	@Override
	public Coupon findByCoupon(String couponCode) {
		Coupon coupon = couponMap.get(couponCode);

		if (coupon == null) {
			throw new InvalidCouponException();
		}

		return coupon;
	}

	public void addCoupon(Coupon coupon) {
		couponMap.putIfAbsent(coupon.getCouponCode(), coupon);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Coupon coupon = Coupon.create("TGIF20", "Discount $20", "Get $20 discount when you order $60 minimum", "FIXED_AMOUNT",
				new BigDecimal("20"), new BigDecimal("60"));
		addCoupon(coupon);
	}
}
