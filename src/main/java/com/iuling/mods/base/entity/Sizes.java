/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.entity;


import com.iuling.core.persistence.DataEntity;
import com.iuling.comm.utils.excel.annotation.ExcelField;

/**
 * 尺码信息管理Entity
 * @author 彭成
 * @version 2017-10-19
 */
public class Sizes extends DataEntity<Sizes> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 尺码代码
	private String name;		// 尺码名称
	
	public Sizes() {
		super();
	}

	public Sizes(String id){
		super(id);
	}

	@ExcelField(title="尺码名称", align=2, sort=6)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ExcelField(title="尺码代码", align=2, sort=7)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}