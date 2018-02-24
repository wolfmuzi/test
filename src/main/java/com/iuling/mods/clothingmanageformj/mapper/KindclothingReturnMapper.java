/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.mapper;

import com.iuling.core.persistence.BaseMapper;
import com.iuling.core.persistence.annotation.MyBatisMapper;
import com.iuling.mods.clothingmanageformj.entity.KindclothingLendItem;
import com.iuling.mods.clothingmanageformj.entity.KindclothingReturn;
import com.iuling.mods.clothingmanageformj.param.KindclothingLendParam;

import java.util.List;

/**
 * 样衣归还管理MAPPER接口
 * @author 彭成
 * @version 2017-09-23
 */
@MyBatisMapper
public interface KindclothingReturnMapper extends BaseMapper<KindclothingReturn> {

    //根据用户的未归还获得信息
    List<KindclothingLendItem> findListByParam(KindclothingLendParam param);

    //根据条形码获得归还人信息
    List <KindclothingLendItem> findByBarCodeCommonRole(KindclothingLendParam param);

    //根据epc获得借用的详情数据
    List<KindclothingLendItem> selectByEpcDatas(KindclothingLendParam param);

}