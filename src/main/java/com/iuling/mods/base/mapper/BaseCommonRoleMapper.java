/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.mapper;

import com.iuling.core.persistence.BaseMapper;
import com.iuling.core.persistence.annotation.MyBatisMapper;
import com.iuling.mods.base.entity.BaseCommonRole;
import com.iuling.mods.clothingmanageformj.param.KindclothingLendParam;

import java.util.List;

/**
 * 设计师质检员管理MAPPER接口
 * @author 彭成
 * @version 2017-09-22
 */
@MyBatisMapper
public interface BaseCommonRoleMapper extends BaseMapper<BaseCommonRole> {

    //根据设计师、质检员查询人员
    public List<BaseCommonRole> findBaseCommonRoleList(BaseCommonRole entity);

    //根据姓名获得信息
    public BaseCommonRole selectByName(KindclothingLendParam param);
}