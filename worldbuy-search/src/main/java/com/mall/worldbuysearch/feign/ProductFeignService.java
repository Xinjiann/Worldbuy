package com.mall.worldbuysearch.feign;

import com.mall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>Title: ProductFeignService</p>
 * Description：
 * date：2022/7/22 23:25
 */
@FeignClient("worldbuy-product")
public interface ProductFeignService {

	@GetMapping("/product/attr/info/{attrId}")
	R getAttrsInfo(@PathVariable("attrId") Long attrId);

	@GetMapping("/product/brand/infos")
	R brandInfo(@RequestParam("brandIds") List<Long> brandIds);
}
