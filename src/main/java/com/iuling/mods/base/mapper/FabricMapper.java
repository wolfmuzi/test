/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.mapper;

import com.iuling.core.persistence.BaseMapper;
import com.iuling.core.persistence.annotation.MyBatisMapper;
import com.iuling.mods.base.entity.Fabric;

import java.util.List;

/**
 * 面料色卡管理MAPPER接口
 * @author 宋小雄
 * @version 2017-09-21
 */
@MyBatisMapper
public interface FabricMapper extends BaseMapper<Fabric> {
    public List<Fabric> findListJoin(Fabric fabric);
}