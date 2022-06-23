package com.mall.product.dao;

import com.mall.product.entity.AttrEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品属性
 * 
 * @author lixinjian
 * @email xinjian.li1@outlook.com
 * @date 2022-02-08 01:09:18
 */
@Mapper
public interface AttrDao extends BaseMapper<AttrEntity> {

  List<Long> selectSearchAttrIds(List<Long> attrIds);
}
