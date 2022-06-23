package com.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.common.utils.PageUtils;
import com.mall.product.entity.AttrEntity;

import com.mall.product.vo.AttrGroupRelationVo;
import com.mall.product.vo.AttrRespVo;
import com.mall.product.vo.AttrVo;
import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author lixinjian
 * @email xinjian.li1@outlook.com
 * @date 2022-02-08 01:09:18
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attrVo);

    /**
     * 规格参数的分页模糊查询
     */
    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String attrType);

    /**
     * 更改规格参数：参数名、参数id、参数、状态的一一对应
     */
    void updateAttr(AttrVo attrVo);

    List<AttrEntity> getRelationAttr(Long attrgroupId);

    void deleteRelation(AttrGroupRelationVo[] vos);

    PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId);

    AttrRespVo getAttrInfo(Long attrId);

    /**
     * 在指定的集合里面挑出可检索的属性
     */
    List<Long> selectSearchAttrIds(List<Long> attrIds);
}

