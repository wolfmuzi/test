/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iuling.mods.base.entity.Supplier;

import com.iuling.core.persistence.DataEntity;
import com.iuling.comm.utils.excel.annotation.ExcelField;

/**
 * 样衣销毁详情Entity
 * @author 潘俞再
 * @version 2017-10-19
 */
@JsonIgnoreProperties({"createBy","updateBy"})
public class KindclothingDeleteItem extends DataEntity<KindclothingDeleteItem> {
	
	private static final long serialVersionUID = 1L;
	private String barCode;		// 样衣条码
	private String epc;		// 样衣标签
	private String name;		// 样衣名称
	private Supplier supplier;		// 供应商
	private KindclothingDelete kindclothingDelete;		// 销毁单号
	
	public KindclothingDeleteItem() {
		super();
	}

	public KindclothingDeleteItem(String id){
		super(id);
	}

	@ExcelField(title="样衣条码", align=2, sort=7)
	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String bar_code) {
		this.barCode = bar_code;
	}
	
	@ExcelField(title="样衣标签", align=2, sort=8)
	public String getEpc() {
		return epc;
	}

	public void setEpc(String epc) {
		this.epc = epc;
	}
	
	@ExcelField(title="样衣名称", align=2, sort=9)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="供应商", fieldType=Supplier.class, value="supplier.name", align=2, sort=10)
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	@ExcelField(title="销毁单号", fieldType=KindclothingDelete.class, value="kindclothingDelete.code", align=2, sort=11)
	public KindclothingDelete getKindclothingDelete() {
		return kindclothingDelete;
	}

	public void setKindclothingDelete(KindclothingDelete kindclothingDelete) {
		this.kindclothingDelete = kindclothingDelete;
	}
	
}