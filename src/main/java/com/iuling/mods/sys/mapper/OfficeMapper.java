/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Iuling</a> All rights reserved.
 */
package com.iuling.mods.sys.mapper;

import com.iuling.core.persistence.TreeMapper;
import com.iuling.core.persistence.annotation.MyBatisMapper;
import com.iuling.mods.sys.entity.Office;

/**
 * 机构MAPPER接口
 * @author iuling
 * @version 2017-05-16
 */
@MyBatisMapper
public interface OfficeMapper extends TreeMapper<Office> {
	
	public Office getByCode(String code);
}
