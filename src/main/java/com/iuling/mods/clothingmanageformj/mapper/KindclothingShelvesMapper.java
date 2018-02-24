/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.mapper;

import com.iuling.core.persistence.BaseMapper;
import com.iuling.core.persistence.annotation.MyBatisMapper;
import com.iuling.mods.base.entity.KindClothing;
import com.iuling.mods.clothingmanageformj.entity.KindclothingShelves;
import com.iuling.mods.clothingmanageformj.param.KindclothingShelvesParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 样衣上架列表MAPPER接口
 * @author 彭成
 * @version 2017-09-20
 */
@MyBatisMapper
public interface KindclothingShelvesMapper extends BaseMapper<KindclothingShelves> {

    //根据绑定、上架状态获取样衣信息
    public List<KindClothing> findListByParam (KindclothingShelvesParam param);

    //查询某个货架的上架数量
    public Integer selectNumById(@Param(value="id") String id);
}