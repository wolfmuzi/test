/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Iuling</a> All rights reserved.
 */
package com.iuling.comm.utils.excel.fieldtype;

import java.util.List;

import com.google.common.collect.Lists;
import com.iuling.comm.utils.Collections3;
import com.iuling.comm.utils.SpringContextHolder;
import com.iuling.comm.utils.StringUtils;
import com.iuling.mods.sys.entity.Role;
import com.iuling.mods.sys.service.SystemService;

/**
 * 字段类型转换
 * @author iuling
 * @version 2016-5-29
 */
public class RoleListType {

	private static SystemService systemService = SpringContextHolder.getBean(SystemService.class);
	
	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		List<Role> roleList = Lists.newArrayList();
		List<Role> allRoleList = systemService.findAllRole();
		for (String s : StringUtils.split(val, ",")){
			for (Role e : allRoleList){
				if (StringUtils.trimToEmpty(s).equals(e.getName())){
					roleList.add(e);
				}
			}
		}
		return roleList.size()>0?roleList:null;
	}

	/**
	 * 设置对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null){
			@SuppressWarnings("unchecked")
			List<Role> roleList = (List<Role>)val;
			return Collections3.extractToString(roleList, "name", ", ");
		}
		return "";
	}
	
}
