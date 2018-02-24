/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iuling.comm.utils.excel.annotation.ExcelField;
import com.iuling.core.persistence.DataEntity;
import com.iuling.mods.base.entity.KindClothing;
import com.iuling.mods.base.entity.Shelves;

/**
 * 样衣盘点详情Entity
 * @author 潘俞再
 * @version 2017-09-26
 */
@JsonIgnoreProperties({"kindclothingInventory","createBy","updateBy"})
public class KindclothingInventoryItem extends DataEntity<KindclothingInventoryItem> {
	
	private static final long serialVersionUID = 1L;
	private KindClothing kindclothing;		// 样衣基础信息
	private Integer type;		// 类型  1:盘亏  2:盘盈
	private KindclothingInventory kindclothingInventory;		// 盘点订单
	private String code;       //盘点单号


	private Integer status;            //上架状态
	private Shelves shelves;		// 货架
	private KindclothingStock kindclothingStock; //样衣库存


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Shelves getShelves() {
		return shelves;
	}

	public void setShelves(Shelves shelves) {
		this.shelves = shelves;
	}

	public KindclothingInventoryItem() {
		super();
	}

	public KindclothingInventoryItem(String id){
		super(id);
	}

	@ExcelField(title="样衣基础信息", fieldType=KindClothing.class, value="kindclothing.name", align=2, sort=6)
	public KindClothing getKindclothing() {
		return kindclothing;
	}

	public void setKindclothing(KindClothing kindclothing) {
		this.kindclothing = kindclothing;
	}
	
	@ExcelField(title="类型", dictType="inventory_item_status", align=2, sort=7)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="盘点订单", fieldType=KindclothingInventory.class, value="kindclothingInventory.code", align=2, sort=8)
	public KindclothingInventory getKindclothingInventory() {
		return kindclothingInventory;
	}

	public void setKindclothingInventory(KindclothingInventory kindclothingInventory) {
		this.kindclothingInventory = kindclothingInventory;
	}

	public KindclothingStock getKindclothingStock() {
		return kindclothingStock;
	}

	public void setKindclothingStock(KindclothingStock kindclothingStock) {
		this.kindclothingStock = kindclothingStock;
	}
}