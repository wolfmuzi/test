/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iuling.mods.base.entity.Supplier;
import com.iuling.mods.fabric.entity.FabricUnbind;

import com.iuling.core.persistence.DataEntity;
import com.iuling.comm.utils.excel.annotation.ExcelField;

/**
 * 面料解绑详情Entity
 * @author 潘俞再
 * @version 2017-10-18
 */
@JsonIgnoreProperties({"createBy","updateBy"})
public class FabricUnbindItem extends DataEntity<FabricUnbindItem> {
	
	private static final long serialVersionUID = 1L;
	private String barCode;		// 面料条码
	private String epc;		// 面料标签号
	private String name;		// 面料名称
	private Supplier supplier;		// 供应商
	private FabricUnbind fabricUnbind;		// 解绑单号
	
	public FabricUnbindItem() {
		super();
	}

	public FabricUnbindItem(String id){
		super(id);
	}

	@ExcelField(title="面料条码", align=2, sort=6)
	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	
	@ExcelField(title="面料标签号", align=2, sort=7)
	public String getEpc() {
		return epc;
	}

	public void setEpc(String epc) {
		this.epc = epc;
	}
	
	@ExcelField(title="面料名称", align=2, sort=8)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="供应商", fieldType=Supplier.class, value="supplier.name", align=2, sort=9)
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	@ExcelField(title="解绑单号", fieldType=FabricUnbind.class, value="fabricUnbind.code", align=2, sort=10)
	public FabricUnbind getFabricUnbind() {
		return fabricUnbind;
	}

	public void setFabricUnbind(FabricUnbind fabricUnbind) {
		this.fabricUnbind = fabricUnbind;
	}
	
}