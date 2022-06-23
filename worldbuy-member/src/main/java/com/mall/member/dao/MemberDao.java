package com.mall.member.dao;

import com.mall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author lixinjian
 * @email xinjian.li1@outlook.com
 * @date 2022-02-08 01:58:16
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
