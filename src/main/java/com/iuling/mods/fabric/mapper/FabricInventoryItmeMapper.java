/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.mapper;

import com.iuling.core.persistence.BaseMapper;
import com.iuling.core.persistence.annotation.MyBatisMapper;
import com.iuling.mods.base.entity.Fabric;
import com.iuling.mods.fabric.entity.FabricInventoryItme;

import java.util.List;

/**
 * 色卡盘点详情MAPPER接口
 * @author 潘俞再
 * @version 2017-09-22
 */
@MyBatisMapper
public interface FabricInventoryItmeMapper extends BaseMapper<FabricInventoryItme> {

    List<Fabric> findByParam1(Fabric fabricInventoryItme);
    List<Fabric> findByParam(FabricInventoryItme fabricInventoryItme);
}