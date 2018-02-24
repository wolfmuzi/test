/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.mapper;

import com.iuling.core.persistence.BaseMapper;
import com.iuling.core.persistence.annotation.MyBatisMapper;
import com.iuling.mods.base.entity.Shelves;
import org.apache.ibatis.annotations.Param;

/**
 * 货架管理MAPPER接口
 * @author 潘俞再
 * @version 2017-09-21
 */
@MyBatisMapper
public interface ShelvesMapper extends BaseMapper<Shelves> {

    int selectNumById(@Param(value = "id") String id);
}