package com.mall.product.feign;


import com.mall.common.to.SkuReductionTO;
import com.mall.common.to.SpuBoundTO;
import com.mall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>Title: CouponFeignService</p>
 * Description：Remote call coupon service
 * date：2022/7/23 17:06
 */
@FeignClient("worldbuy-coupon")
public interface CouponFeignService {

	/**
	 * @RequestBody Convert the object to json Put the json from the previous step in the request body position Send the request
	 *
	 * The peer service receives the request. What is received is the json data in the request body, and the SpuBoundsEntity is encapsulated with @RequestBody
	 *
	 * Both services do not need to use the same TO object as long as the JSON data model is compatible
	 */
	@PostMapping("/coupon/spubounds/save")
	R saveSpuBounds(@RequestBody SpuBoundTO spuBoundTo);

	@PostMapping("/coupon/skufullreduction/saveinfo")
	R saveSkuReduction(@RequestBody SkuReductionTO skuReductionTo);
}
