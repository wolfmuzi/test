/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.mapper;

import com.iuling.core.persistence.BaseMapper;
import com.iuling.core.persistence.annotation.MyBatisMapper;
import com.iuling.mods.clothingmanageformj.entity.KindclothingReturnItem;

import java.util.List;

/**
 * 样衣归还详情MAPPER接口
 * @author 彭成
 * @version 2017-09-23
 */
@MyBatisMapper
public interface KindclothingReturnItemMapper extends BaseMapper<KindclothingReturnItem> {

    //本次归还详情
    List<KindclothingReturnItem> findList1(KindclothingReturnItem entity);
}