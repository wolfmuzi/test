/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.mapper;

import com.iuling.core.persistence.BaseMapper;
import com.iuling.core.persistence.annotation.MyBatisMapper;
import com.iuling.mods.base.entity.SerialCode;

import java.util.Date;

/**
 * 流水号编码信息MAPPER接口
 * @author 宋小雄
 * @version 2017-09-18
 */
@MyBatisMapper
public interface SerialCodeMapper extends BaseMapper<SerialCode> {

    int findCountBetween(Date startTime, Date endTime);
}