package com.mall.worldbuysearch.controller;


import com.mall.common.exception.BizCodeEnum;
import com.mall.common.to.es.SkuEsModel;
import com.mall.common.utils.R;
import com.mall.worldbuysearch.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * <p>Title: ElasticSaveController</p>
 * Description：goods on Controller
 * date：2022/7/3
 */
@Slf4j
@RequestMapping("/search/save")
@RestController
public class ElasticSaveController {

	@Autowired
	private ProductSaveService productSaveService;

	/**
	 * goods on
	 */
	@PostMapping("/product")
	public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels){

		boolean status;
		try {
			status = productSaveService.productStatusUp(skuEsModels);
		} catch (IOException e) {
			log.error("ElasticSaveController: {}", e);
			return R.error(BizCodeEnum.PRODUCT_UP_EXCEPTION.getCode(), BizCodeEnum.PRODUCT_UP_EXCEPTION.getMsg());
		}
		if(!status){
			return R.ok();
		}
		return R.error(BizCodeEnum.PRODUCT_UP_EXCEPTION.getCode(), BizCodeEnum.PRODUCT_UP_EXCEPTION.getMsg());
	}
}
