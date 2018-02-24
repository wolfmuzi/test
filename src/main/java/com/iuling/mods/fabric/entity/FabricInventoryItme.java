/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iuling.comm.utils.excel.annotation.ExcelField;
import com.iuling.core.persistence.DataEntity;
import com.iuling.mods.base.entity.Fabric;
import com.iuling.mods.base.entity.Shelves;

/**
 * 色卡盘点详情Entity
 * @author 潘俞再
 * @version 2017-09-22
 */
@JsonIgnoreProperties({"fabricInventory","createBy","updateBy"})
public class FabricInventoryItme extends DataEntity<FabricInventoryItme> {
	
	private static final long serialVersionUID = 1L;
	private Fabric fabric;		// 色卡基础信息id
	private Integer type;		// 类型 盘盈盘亏
	private String code;
	private Integer status;
	private Shelves shelves;		// 货架
	private String types;
	private String codes;

	public Shelves getShelves() {
		return shelves;
	}

	public void setShelves(Shelves shelves) {
		this.shelves = shelves;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private FabricInventory fabricInventory;		// 盘点订单id
	
	public FabricInventoryItme() {
		super();
	}

	public FabricInventoryItme(String id){
		super(id);
	}

	@ExcelField(title="色卡基础信息id", fieldType=Fabric.class, value="fabric.name", align=2, sort=6)
	public Fabric getFabric() {
		return fabric;
	}

	public void setFabric(Fabric fabric) {
		this.fabric = fabric;
	}
	
	@ExcelField(title="类型 盘盈盘亏", dictType="inventory_item_status", align=2, sort=7)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="盘点订单id", fieldType=FabricInventory.class, value="fabricInventory.code", align=2, sort=8)
	public FabricInventory getFabricInventory() {
		return fabricInventory;
	}

	public void setFabricInventory(FabricInventory fabricInventory) {
		this.fabricInventory = fabricInventory;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getCodes() {
		return codes;
	}

	public void setCodes(String codes) {
		this.codes = codes;
	}
}