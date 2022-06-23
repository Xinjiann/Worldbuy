package com.mall.product.dao;

import com.mall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author lixinjian
 * @email xinjian.li1@outlook.com
 * @date 2022-02-08 01:09:17
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
