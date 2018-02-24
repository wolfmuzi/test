/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.mapper;

import com.iuling.core.persistence.BaseMapper;
import com.iuling.core.persistence.annotation.MyBatisMapper;
import com.iuling.mods.clothingmanageformj.entity.KindclothingLendItem;

import java.util.List;

/**
 * 样衣借用详情MAPPER接口
 * @author 彭成
 * @version 2017-09-23
 */
@MyBatisMapper
public interface KindclothingLendItemMapper extends BaseMapper<KindclothingLendItem> {

    //统计当前用户的借用总数
    Integer sumByBaseCommonRoleId(KindclothingLendItem kindclothingLendItem);

    //根据借用单号查找未归还样衣数量
    Integer findByLendNo(KindclothingLendItem entity);

    //根据借用单号获得借用详情总数量
    Integer getTotalByLendNo(KindclothingLendItem entity);

    List<KindclothingLendItem> findByParam(KindclothingLendItem kindclothingLendItem);

    //查询借用详情、本次借用详情
    List<KindclothingLendItem> findByParam1(KindclothingLendItem kindclothingLendItem);
}