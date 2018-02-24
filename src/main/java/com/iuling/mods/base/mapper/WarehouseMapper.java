/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.mapper;

import com.iuling.core.persistence.TreeMapper;
import com.iuling.core.persistence.annotation.MyBatisMapper;
import com.iuling.mods.base.entity.Warehouse;

/**
 * 仓库管理MAPPER接口
 * @author 宋小雄
 * @version 2017-09-19
 */
@MyBatisMapper
public interface WarehouseMapper extends TreeMapper<Warehouse> {

    //由名称获得仓库信息
    public Warehouse getByName(String name);
}