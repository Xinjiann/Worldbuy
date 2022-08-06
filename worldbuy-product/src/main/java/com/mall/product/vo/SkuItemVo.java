package com.mall.product.vo;

import com.mall.product.entity.SkuImagesEntity;
import com.mall.product.entity.SkuInfoEntity;
import com.mall.product.entity.SpuInfoDescEntity;
import lombok.Data;

import java.util.List;

/**
 * <p>Title: SkuItemVo</p>
 * Description：
 * date：2020/6/24 13:33
 */
@Data
public class SkuItemVo {

	/**
	 * Basic Information
	 */
	SkuInfoEntity info;

	boolean hasStock = true;

	/**
	 * image information
	 */
	List<SkuImagesEntity> images;

	/**
	 * Sales attribute combination
	 */
	List<ItemSaleAttrVo> saleAttr;

	/**
	 * introduce
	 */
	SpuInfoDescEntity desc;

	/**
	 * Parameter specification information
	 */
	List<SpuItemAttrGroup> groupAttrs;

	/**
	 *Seckill information
	 */
	SeckillInfoVo seckillInfoVo;
}
