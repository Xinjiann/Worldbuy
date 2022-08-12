package com.mall.worldbuy.cart.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>Title: SkuInfoVo</p>
 * Description：sku object
 * date：2022/8/14 11:33
 */
@Data
public class SkuInfoVo {

	private Long skuId;
	/**
	 * spuId
	 */
	private Long spuId;
	/**
	 *sku name
	 */
	private String skuName;
	/**
	 * sku introduction description
	 */
	private String skuDesc;
	/**
	 * category id
	 */
	private Long catalogId;
	/**
	 * brand id
	 */
	private Long brandId;
	/**
	 * Default picture
	 */
	private String skuDefaultImg;
	/**
	 * title
	 */
	private String skuTitle;
	/**
	 * subtitle
	 */
	private String skuSubtitle;
	/**
	 * price
	 */
	private BigDecimal price;
	/**
	 * Sales
	 */
	private Long saleCount;
}
