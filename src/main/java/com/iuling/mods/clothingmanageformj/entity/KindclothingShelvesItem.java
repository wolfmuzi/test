/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.entity;

import com.iuling.mods.base.entity.Supplier;
import javax.validation.constraints.NotNull;

import com.iuling.core.persistence.DataEntity;
import com.iuling.comm.utils.excel.annotation.ExcelField;

/**
 * 样衣上架详情Entity
 * @author 彭成
 * @version 2017-09-20
 */
public class KindclothingShelvesItem extends DataEntity<KindclothingShelvesItem> {
	
	private static final long serialVersionUID = 1L;
	private String barCode;		// 样衣条码
	private String epc;		// 样衣标签号
	private String name;		// 样衣品名
	private Supplier supplier;		// 供应商
	private String style;		// 风格
	private String editionnumber;		// 版单号
	private KindclothingShelves kindclothingShelves;		// 样衣上架单id
	
	public KindclothingShelvesItem() {
		super();
	}

	public KindclothingShelvesItem(String id){
		super(id);
	}

	@ExcelField(title="样衣条码", align=2, sort=6)
	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	
	@ExcelField(title="样衣标签号", align=2, sort=7)
	public String getEpc() {
		return epc;
	}

	public void setEpc(String epc) {
		this.epc = epc;
	}
	
	@ExcelField(title="样衣品名", align=2, sort=8)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="供应商不能为空")
	@ExcelField(title="供应商", fieldType=Supplier.class, value="supplier.name", align=2, sort=9)
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	@ExcelField(title="风格", align=2, sort=10)
	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
	
	@ExcelField(title="版单号", align=2, sort=11)
	public String getEditionnumber() {
		return editionnumber;
	}

	public void setEditionnumber(String editionnumber) {
		this.editionnumber = editionnumber;
	}
	
	@NotNull(message="样衣上架单id不能为空")
	@ExcelField(title="样衣上架单id", fieldType=KindclothingShelves.class, value="kindclothingShelves.code", align=2, sort=12)
	public KindclothingShelves getKindclothingShelves() {
		return kindclothingShelves;
	}

	public void setKindclothingShelves(KindclothingShelves kindclothingShelves) {
		this.kindclothingShelves = kindclothingShelves;
	}
	
}