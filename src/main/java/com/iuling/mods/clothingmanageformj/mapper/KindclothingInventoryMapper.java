/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.mapper;

import com.iuling.core.persistence.BaseMapper;
import com.iuling.core.persistence.annotation.MyBatisMapper;
import com.iuling.mods.clothingmanageformj.entity.KindclothingInventory;

/**
 * 样衣盘点MAPPER接口
 * @author 彭成
 * @version 2017-09-26
 */
@MyBatisMapper
public interface KindclothingInventoryMapper extends BaseMapper<KindclothingInventory> {

    //根据盘点单号获得信息
    KindclothingInventory getByCode(KindclothingInventory entity);

}