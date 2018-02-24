/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.entity;


import com.iuling.core.persistence.DataEntity;
import com.iuling.comm.utils.excel.annotation.ExcelField;

/**
 * 生成各类单号Entity
 * @author 潘俞再
 * @version 2017-09-21
 */
public class OrderCode extends DataEntity<OrderCode> {
	
	private static final long serialVersionUID = 1L;
	private String orderKey;		// 单号key
	private String orderVal;		// 累计值
	
	public OrderCode() {
		super();
	}

	public OrderCode(String id){
		super(id);
	}

	@ExcelField(title="单号key", align=2, sort=7)
	public String getOrderKey() {
		return orderKey;
	}

	public void setOrderKey(String orderKey) {
		this.orderKey = orderKey;
	}
	
	@ExcelField(title="累计值", align=2, sort=8)
	public String getOrderVal() {
		return orderVal;
	}

	public void setOrderVal(String orderVal) {
		this.orderVal = orderVal;
	}
	
}