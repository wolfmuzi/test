/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;

import com.iuling.comm.utils.excel.annotation.HiddenColums;
import com.iuling.core.persistence.TreeEntity;

/**
 * 仓库管理Entity
 * @author 宋小雄
 * @version 2017-09-19
 */

public class Warehouse extends TreeEntity<Warehouse> {
	
	private static final long serialVersionUID = 1L;
	private String number;		// 仓库编码
	
	
	public Warehouse() {
		super();
	}

	public Warehouse(String id){
		super(id);
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	public  Warehouse getParent() {
			return parent;
	}
	
	@Override
	public void setParent(Warehouse parent) {
		this.parent = parent;
		
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}