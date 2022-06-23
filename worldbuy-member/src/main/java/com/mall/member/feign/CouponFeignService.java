package com.mall.member.feign;

import com.mall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;


@FeignClient("worldbuy-coupon")
public interface CouponFeignService {

	@RequestMapping("/coupon/coupon/member/list")
	R memberCoupons();
}
