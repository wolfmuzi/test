/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.entity;

import com.iuling.comm.utils.excel.annotation.ExcelField;
import com.iuling.core.persistence.DataEntity;

import javax.validation.constraints.NotNull;

/**
 * 大类信息管理Entity
 * @author 彭成
 * @version 2017-10-19
 */
public class MaxClass extends DataEntity<MaxClass> {
	
	private static final long serialVersionUID = 1L;
	private Integer type;		// 大类类别
	private String code;		// 大类代码
	private String name;		// 大类名称
	
	public MaxClass() {
		super();
	}

	public MaxClass(String id){
		super(id);
	}

	@NotNull(message="大类类别不能为空")
	@ExcelField(title="类别", dictType="max_class_type", align=2, sort=6)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@ExcelField(title="大类名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ExcelField(title="大类代码", align=2, sort=8)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}