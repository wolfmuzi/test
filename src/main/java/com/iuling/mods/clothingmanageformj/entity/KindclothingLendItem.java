/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iuling.comm.utils.excel.annotation.ExcelField;
import com.iuling.core.persistence.DataEntity;
import com.iuling.mods.base.entity.BaseCommonRole;
import com.iuling.mods.base.entity.Supplier;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 样衣借用详情Entity
 * @author 彭成
 * @version 2017-09-23
 */
public class KindclothingLendItem extends DataEntity<KindclothingLendItem> {
	
	private static final long serialVersionUID = 1L;
	private BaseCommonRole baseCommonRole;		// 借用人
	private String lendNo;		// 借用单号
	private String name;		// 样衣品名
	private String barCode;		// 样衣标签
	private String style;		// 风格
	private String editionnumber;		// 版单号
	private Supplier supplier;		// 供应商
	private Integer lendIsReturn;		// 是否归还 1未归还	2已归还
	private String barCode1;		//样衣条形码

	private String deadline;   //借用期限
	private Date lendDate;           //借用时间
	private Date expectedReturnDate; //预计借用时间
	private Integer lendTotal;    //未归还总数
	private String surplusDate;    //剩余天数
	private String id;      //借用人id

	public KindclothingLendItem() {
		super();
	}

	public KindclothingLendItem(String id){
		super(id);
	}

	@NotNull(message="借用人不能为空")
	@ExcelField(title="借用人", fieldType=BaseCommonRole.class, value="baseCommonRole.name", align=2, sort=6)
	public BaseCommonRole getBaseCommonRole() {
		return baseCommonRole;
	}

	public void setBaseCommonRole(BaseCommonRole baseCommonRole) {
		this.baseCommonRole = baseCommonRole;
	}
	
	@ExcelField(title="借用单号", align=2, sort=7)
	public String getLendNo() {
		return lendNo;
	}

	public void setLendNo(String lendNo) {
		this.lendNo = lendNo;
	}
	
	@ExcelField(title="样衣品名", align=2, sort=8)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="样衣标签", align=2, sort=9)
	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
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
	
	@NotNull(message="是否归还不能为空")
	@ExcelField(title="是否归还", align=2, sort=13)
	public Integer getLendIsReturn() {
		return lendIsReturn;
	}

	public void setLendIsReturn(Integer lendIsReturn) {
		this.lendIsReturn = lendIsReturn;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	/*@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLendDate() {
		return lendDate;
	}

	public void setLendDate(Date lendDate) {
		this.lendDate = lendDate;
	}*/

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getExpectedReturnDate() {
		return expectedReturnDate;
	}

	public void setExpectedReturnDate(Date expectedReturnDate) {
		this.expectedReturnDate = expectedReturnDate;
	}

	public Integer getLendTotal() {
		return lendTotal;
	}

	public void setLendTotal(Integer lendTotal) {
		this.lendTotal = lendTotal;
	}

	public String getSurplusDate() {
		return surplusDate;
	}

	public void setSurplusDate(String surplusDate) {
		this.surplusDate = surplusDate;
	}

	/*public String getLendDate() {
		return lendDate;
	}

	public void setLendDate(String lendDate) {
		this.lendDate = lendDate;
	}*/

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLendDate() {
		return lendDate;
	}

	public void setLendDate(Date lendDate) {
		this.lendDate = lendDate;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getBarCode1() {
		return barCode1;
	}

	public void setBarCode1(String barCode1) {
		this.barCode1 = barCode1;
	}
}