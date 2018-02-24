/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.mapper;

import com.iuling.core.persistence.BaseMapper;
import com.iuling.core.persistence.annotation.MyBatisMapper;
import com.iuling.mods.base.entity.Fabric;
import com.iuling.mods.fabric.entity.FabricStock;
import com.iuling.mods.fabric.param.FabricBindParam;
import com.iuling.mods.fabric.param.FabricInventoryParam;
import com.iuling.mods.fabric.param.FabricShelvesParam;

import java.util.List;

/**
 * 面料色卡库存信息MAPPER接口
 * @author 潘俞再
 * @version 2017-09-19
 */
@MyBatisMapper
public interface FabricStockMapper extends BaseMapper<FabricStock> {

    public List<Fabric> findListByParam(FabricShelvesParam fabricShelvesParam);

    public List<FabricStock> findListByParam1(FabricInventoryParam fabricShelvesParam);

    FabricStock checkFabric(FabricBindParam fabricBindParam);

    List<FabricStock> findListSubmit(FabricStock fabricStock);

    public List<Fabric>  findListByParam2(FabricShelvesParam fabricInventoryParam);

    FabricStock checkFabricByBarCode(FabricBindParam fabricBindParam);

    List<FabricStock> checkFabricByEpcList(FabricBindParam fabricBindParam);
}