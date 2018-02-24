/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.mapper;

import com.iuling.core.persistence.BaseMapper;
import com.iuling.core.persistence.annotation.MyBatisMapper;
import com.iuling.mods.base.entity.KindClothing;
import com.iuling.mods.clothingmanageformj.entity.KindclothingLend;
import com.iuling.mods.clothingmanageformj.param.KindclothingLendParam;

import java.util.List;

/**
 * 样衣借用管理MAPPER接口
 * @author 彭成
 * @version 2017-09-22
 */
@MyBatisMapper
public interface KindclothingLendMapper extends BaseMapper<KindclothingLend> {

    //根据是否借出、上架状态获取样衣信息
    public List<KindClothing> findListByParam(KindclothingLendParam param);

}