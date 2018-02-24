/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iuling.mods.base.entity.Supplier;
import com.iuling.mods.base.entity.Colors;
import com.iuling.mods.fabric.entity.FabricShelves;

import com.iuling.core.persistence.DataEntity;
import com.iuling.comm.utils.excel.annotation.ExcelField;

/**
 * 面料上架详情Entity
 * @author 潘俞再
 * @version 2017-09-20
 */
@JsonIgnoreProperties({"createBy","updateBy"})
public class FabricShelvesItem extends DataEntity<FabricShelvesItem> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 色卡编码
	private String epc;		// 色卡标签号
	private String name;		// 色卡品名
	private Supplier supplier;		// 供应商
	private Colors colors;		// 颜色
	private String ingredients;		// 成分
	private FabricShelves fabricShelves;		// 上架订单id
	
	public FabricShelvesItem() {
		super();
	}

	public FabricShelvesItem(String id){
		super(id);
	}

	@ExcelField(title="色卡编码", align=2, sort=6)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="色卡标签号", align=2, sort=7)
	public String getEpc() {
		return epc;
	}

	public void setEpc(String epc) {
		this.epc = epc;
	}
	
	@ExcelField(title="色卡品名", align=2, sort=8)
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
	
	@ExcelField(title="颜色", fieldType=Colors.class, value="colors.name", align=2, sort=10)
	public Colors getColors() {
		return colors;
	}

	public void setColors(Colors colors) {
		this.colors = colors;
	}
	
	@ExcelField(title="成分", align=2, sort=11)
	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}
	
	@ExcelField(title="上架订单id", fieldType=FabricShelves.class, value="fabricShelves.code", align=2, sort=12)
	public FabricShelves getFabricShelves() {
		return fabricShelves;
	}

	public void setFabricShelves(FabricShelves fabricShelves) {
		this.fabricShelves = fabricShelves;
	}
	
}