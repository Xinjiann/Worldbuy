package com.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.common.to.es.SkuHasStockVo;
import com.mall.common.utils.PageUtils;
import com.mall.ware.entity.WareSkuEntity;

import java.util.List;
import java.util.Map;

/**
 * εεεΊε­
 *
 * @author lixinjian
 * @email xinjian.li1@outlook.com
 * @date 2022-02-08 02:12:32
 */
public interface WareSkuService extends IService<WareSkuEntity> {
    PageUtils queryPage(Map<String, Object> params);
//
//    /**
//     * Check product prices while keeping inventory
//     */
//    double addStock(Long skuId, Long wareId, Integer skuNum);

    /**
     * check if there is stock
     */
    List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds);
}

