package com.mall.product.dao;

import com.mall.product.entity.AttrGroupEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.product.vo.SpuItemAttrGroup;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 属性分组
 * 
 * @author lixinjian
 * @email xinjian.li1@outlook.com
 * @date 2022-02-08 01:09:17
 */
@Mapper
public interface AttrGroupDao extends BaseMapper<AttrGroupEntity> {

  List<SpuItemAttrGroup> getAttrGroupWithAttrsBySpuId(@Param("spuId") Long spuId, @Param("catalogId") Long catalogId);
	
}
