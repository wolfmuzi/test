/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.mapper;

import com.iuling.core.persistence.BaseMapper;
import com.iuling.core.persistence.annotation.MyBatisMapper;
import com.iuling.mods.fabric.entity.FabricOrderItem;

import java.util.List;

/**
 * 调布订单详情MAPPER接口
 * @author 潘俞再
 * @version 2017-09-23
 */
@MyBatisMapper
public interface FabricOrderItemMapper extends BaseMapper<FabricOrderItem> {

    List<FabricOrderItem> findByParam(FabricOrderItem fabricOrderItem);

    List<FabricOrderItem> findListByDate(FabricOrderItem daoParam);
}