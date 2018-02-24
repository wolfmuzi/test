/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.mapper;

import com.iuling.core.persistence.BaseMapper;
import com.iuling.core.persistence.annotation.MyBatisMapper;
import com.iuling.mods.clothingmanageformj.entity.KindclothingInventoryItem;

import java.util.List;

/**
 * 样衣盘点详情MAPPER接口
 * @author 潘俞再
 * @version 2017-09-26
 */
@MyBatisMapper
public interface KindclothingInventoryItemMapper extends BaseMapper<KindclothingInventoryItem> {

    //根据盘点单号、类型查询盘亏、盘盈信息
    List<KindclothingInventoryItem> findList1(KindclothingInventoryItem entity);

    //查询盘盈还有未上架的信息
    List<KindclothingInventoryItem> findByParam(KindclothingInventoryItem entity);
}