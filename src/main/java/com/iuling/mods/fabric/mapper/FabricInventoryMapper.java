/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.mapper;

import com.iuling.core.persistence.BaseMapper;
import com.iuling.core.persistence.annotation.MyBatisMapper;
import com.iuling.mods.fabric.entity.FabricInventory;

/**
 * 色卡盘点MAPPER接口
 * @author 潘俞再
 * @version 2017-09-22
 */
@MyBatisMapper
public interface FabricInventoryMapper extends BaseMapper<FabricInventory> {

    FabricInventory getByCode(FabricInventory fabricInventory);
}