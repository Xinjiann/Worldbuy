package com.mall.product.dao;

import com.mall.product.entity.AttrAttrgroupRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 属性&属性分组关联
 * 
 * @author lixinjian
 * @email xinjian.li1@outlook.com
 * @date 2022-02-08 01:09:18
 */
@Mapper
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelationEntity> {
  void deleteBatchRelation(@Param("entities") List<AttrAttrgroupRelationEntity> entities);
	
}
