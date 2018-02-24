/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.mapper;

import com.iuling.core.persistence.BaseMapper;
import com.iuling.core.persistence.annotation.MyBatisMapper;
import com.iuling.mods.fabric.entity.FabricStock;
import com.iuling.mods.fabric.param.FabricBindParam;
import com.iuling.mods.fabric.param.FabricInventoryParam;
import com.iuling.mods.clothingmanageformj.entity.KindclothingStock;
import com.iuling.mods.clothingmanageformj.param.KindclothingBindParam;

import java.util.List;

/**
 * 样衣库存MAPPER接口
 * @author 彭成
 * @version 2017-09-22
 */
@MyBatisMapper
public interface KindclothingStockMapper extends BaseMapper<KindclothingStock> {

    //查找
    KindclothingStock checkKindclothing(KindclothingBindParam param);

    List<KindclothingStock> findListByParam1(FabricInventoryParam fabricInventoryParam);

    //根据epc获取样衣信息
    KindclothingStock findKindclothingByEpc(KindclothingBindParam param);

    List<KindclothingStock> findListSubmit(KindclothingStock kindclothingStock);

    //根据样衣条码获取样衣信息
    KindclothingStock findKindclothingByBarCode(KindclothingBindParam param);

    List<FabricStock> byEpcListData(FabricBindParam fabricBindParam);

    List<KindclothingStock> findAllKindClothingStock();
}