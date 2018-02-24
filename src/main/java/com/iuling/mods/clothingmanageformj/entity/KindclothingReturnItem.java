/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.entity;

import com.iuling.comm.utils.excel.annotation.ExcelField;
import com.iuling.core.persistence.DataEntity;
import com.iuling.mods.base.entity.BaseCommonRole;
import com.iuling.mods.base.entity.Supplier;

import javax.validation.constraints.NotNull;

/**
 * 样衣归还详情Entity
 * @author 彭成
 * @version 2017-09-23
 */
public class KindclothingReturnItem extends DataEntity<KindclothingReturnItem> {
	
	private static final long serialVersionUID = 1L;
	private BaseCommonRole baseCommonRole;		// 归还人
	private String returnNo;		// 归还单号
	private String name;		// 样衣品名
	private String epc;		// 样衣标签
	private String style;		// 风格
	private String editionnumber;		// 版单号
	private Supplier supplier;		// 供应商


	private String lendNo;   //借用单号
	
	public KindclothingReturnItem() {
		super();
	}

	public KindclothingReturnItem(String id){
		super(id);
	}

	@NotNull(message="归还人不能为空")
	@ExcelField(title="归还人", fieldType=BaseCommonRole.class, value="baseCommonRole.name", align=2, sort=6)
	public BaseCommonRole getBaseCommonRole() {
		return baseCommonRole;
	}

	public void setBaseCommonRole(BaseCommonRole baseCommonRole) {
		this.baseCommonRole = baseCommonRole;
	}
	
	@ExcelField(title="归还单号", align=2, sort=7)
	public String getReturnNo() {
		return returnNo;
	}

	public void setReturnNo(String returnNo) {
		this.returnNo = returnNo;
	}
	
	@ExcelField(title="样衣品名", align=2, sort=8)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="样衣标签", align=2, sort=9)
	public String getEpc() {
		return epc;
	}

	public void setEpc(String epc) {
		this.epc = epc;
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

	@NotNull(message="供应商不能为空")
	@ExcelField(title="供应商", fieldType=Supplier.class, value="supplier.name", align=2, sort=12)
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public String getLendNo() {
		return lendNo;
	}

	public void setLendNo(String lendNo) {
		this.lendNo = lendNo;
	}


}