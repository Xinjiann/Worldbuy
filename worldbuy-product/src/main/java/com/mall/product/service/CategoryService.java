package com.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.common.utils.PageUtils;
import com.mall.product.entity.CategoryEntity;

import com.mall.product.vo.Catelog2Vo;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lixinjian
 * @email xinjian.li1@outlook.com
 * @date 2022-02-08 01:09:17
 */
public interface CategoryService extends IService<CategoryEntity> {

  PageUtils queryPage(Map<String, Object> params);

  List<CategoryEntity> listWithTree();

  void removeMenuByIds(List<Long> asList);

  Long[] findCateLogPath(Long catelogId);

  void updateCascade(CategoryEntity category);

  List<CategoryEntity> getLevel1Categorys();

  Map<String, List<Catelog2Vo>> getCatelogJson();
}

