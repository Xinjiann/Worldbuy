package com.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.common.utils.PageUtils;
import com.mall.product.entity.BrandEntity;
import com.mall.product.entity.CategoryBrandRelationEntity;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author lixinjian
 * @email xinjian.li1@outlook.com
 * @date 2022-02-13 23:36:01
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

  PageUtils queryPage(Map<String, Object> params);

  void saveDetail(CategoryBrandRelationEntity categoryBrandRelation);

  void updateBrand(Long brandId, String name);

  void updateCategory(Long catId, String name);

  List<BrandEntity> getBrandsByCatId(Long catId);
}

