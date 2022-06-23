package com.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.common.utils.PageUtils;
import com.mall.product.entity.AttrGroupEntity;

import com.mall.product.vo.AttrGroupWithAttrsVo;
import com.mall.product.vo.SpuItemAttrGroup;
import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author lixinjian
 * @email xinjian.li1@outlook.com
 * @date 2022-02-08 01:09:17
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

  PageUtils queryPage(Map<String, Object> params);

  PageUtils queryPage(Map<String, Object> params, Long catId);

  List<AttrGroupWithAttrsVo> getAttrGroupWithAttrByCatelogId(Long catelogId);

  List<SpuItemAttrGroup> getAttrGroupWithAttrsBySpuId(Long spuId, Long catalogId);
}

