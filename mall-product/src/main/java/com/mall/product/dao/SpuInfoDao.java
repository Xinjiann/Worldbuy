package com.mall.product.dao;

import com.mall.product.entity.SpuInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * spu信息
 * 
 * @author lixinjian
 * @email xinjian.li1@outlook.com
 * @date 2022-02-08 01:09:17
 */
@Mapper
public interface SpuInfoDao extends BaseMapper<SpuInfoEntity> {

  void updateSpuStatus(Long spuId, int code);
}
