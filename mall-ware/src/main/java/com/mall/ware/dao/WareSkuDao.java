package com.mall.ware.dao;

import com.mall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 * 
 * @author lixinjian
 * @email xinjian.li1@outlook.com
 * @date 2022-02-08 02:12:32
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {
	
}
