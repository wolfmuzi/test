/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.entity;


import com.iuling.comm.utils.excel.annotation.HiddenColums;
import com.iuling.core.persistence.DataEntity;
import com.iuling.comm.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

/**
 * 颜色信息管理Entity
 * @author 宋小雄
 * @version 2017-09-20
 */
@HiddenColums(colums = {1})
public class Colors extends DataEntity<Colors> {
	
	private static final long serialVersionUID = 1L;
	private String number;		// 颜色编码
	private String name;		// 颜色名称
	
	public Colors() {
		super();
	}

	public Colors(String id){
		super(id);
	}

	@ExcelField(title="ID", align=2, sort=1)
	@Override
	public String getId() {
		return super.getId();
	}

	@ExcelField(title="颜色编码", align=2, sort=2)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@ExcelField(title="颜色名称", align=2, sort=3)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ExcelField(title="颜色描述", align=2, sort=4)
	@Length(min=0, max=255)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}