/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iuling.mods.base.entity.Colors;
import com.iuling.mods.base.entity.Fabric;
import com.iuling.mods.base.entity.Supplier;
import javax.validation.constraints.NotNull;
import com.iuling.mods.base.entity.Shelves;

import com.iuling.core.persistence.DataEntity;
import com.iuling.comm.utils.excel.annotation.ExcelField;

/**
 * 面料色卡库存信息Entity
 * @author 潘俞再
 * @version 2017-09-19
 */
@JsonIgnoreProperties({"createBy","updateBy"})
public class FabricStock extends DataEntity<FabricStock> {
	
	private static final long serialVersionUID = 1L;
	private String barCode;		// 色卡货号
	private String name;		// 色卡名称
	private Supplier supplier;		// 供应商
	private Integer bindStatus;		// 绑定状态
	private String epc;		// 标签编码
	private Integer shelvesStatus;		// 上架状态
	private Shelves shelves;		// 货架

	private Fabric fabric;
	private String number;

	private String colorName;
	private String supplierName;
	private String warehouseName;

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public FabricStock(String id, String epc){
		this.id = id;
		this.epc = epc;
	}
	public FabricStock(String id){
		this.id = id;
	}

	public Fabric getFabric() {
		return fabric;
	}

	public void setFabric(Fabric fabric) {
		this.fabric = fabric;
	}

	public FabricStock() {
		super();
	}



	@ExcelField(title="色卡货号", align=2, sort=6)
	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	
	@ExcelField(title="色卡名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="供应商不能为空")
	@ExcelField(title="供应商", fieldType=Supplier.class, value="supplier.name", align=2, sort=8)
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	@NotNull(message="绑定状态不能为空")
	@ExcelField(title="绑定状态", dictType="stock_bind_status", align=2, sort=9)
	public Integer getBindStatus() {
		return bindStatus;
	}

	public void setBindStatus(Integer bindStatus) {
		this.bindStatus = bindStatus;
	}
	
	@ExcelField(title="标签编码", align=2, sort=10)
	public String getEpc() {
		return epc;
	}

	public void setEpc(String epc) {
		this.epc = epc;
	}
	
	@NotNull(message="上架状态不能为空")
	@ExcelField(title="上架状态", dictType="stock_shelves_status", align=2, sort=11)
	public Integer getShelvesStatus() {
		return shelvesStatus;
	}

	public void setShelvesStatus(Integer shelvesStatus) {
		this.shelvesStatus = shelvesStatus;
	}
	
	@NotNull(message="货架不能为空")
	@ExcelField(title="货架", fieldType=Shelves.class, value="shelves.name", align=2, sort=12)
	public Shelves getShelves() {
		return shelves;
	}

	public void setShelves(Shelves shelves) {
		this.shelves = shelves;
	}

	@Override
	public boolean equals(Object obj) {
		FabricStock s = (FabricStock)obj;
		return this.epc.equals(s.getEpc());
	}
}