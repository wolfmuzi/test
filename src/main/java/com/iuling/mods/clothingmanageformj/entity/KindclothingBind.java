/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.entity;

import com.iuling.mods.base.entity.KindClothing;
import com.iuling.mods.base.entity.Supplier;
import javax.validation.constraints.NotNull;
import com.iuling.mods.sys.entity.User;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.iuling.core.persistence.DataEntity;
import com.iuling.comm.utils.excel.annotation.ExcelField;

/**
 * 样衣绑定信息Entity
 * @author 彭成
 * @version 2017-09-20
 */
public class KindclothingBind extends DataEntity<KindclothingBind> {
	
	private static final long serialVersionUID = 1L;
	private String barCode;		// 样衣条码
	private String epc;		// 样衣标签
	private String name;		// 样衣名称
	private Supplier supplier;		// 供应商id
	private User bindUser;		// 绑定人员
	private Date bindDate;		// 绑定时间
	private Date beginBindDate;		// 开始 绑定时间
	private Date endBindDate;		// 结束 绑定时间
	private KindClothing kindClothing;

	public KindClothing getKindClothing() {
		return kindClothing;
	}

	public void setKindClothing(KindClothing kindClothing) {
		this.kindClothing = kindClothing;
	}

	public KindclothingBind() {
		super();
	}

	public KindclothingBind(String id){
		super(id);
	}

	@ExcelField(title="样衣条码", align=2, sort=6)
	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	
	@ExcelField(title="样衣标签", align=2, sort=7)
	public String getEpc() {
		return epc;
	}

	public void setEpc(String epc) {
		this.epc = epc;
	}
	
	@ExcelField(title="样衣名称", align=2, sort=8)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="供应商id不能为空")
	@ExcelField(title="供应商id", fieldType=Supplier.class, value="supplier.name", align=2, sort=9)
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	@NotNull(message="绑定人员不能为空")
	@ExcelField(title="绑定人员", fieldType=User.class, value="bindUser.name", align=2, sort=10)
	public User getBindUser() {
		return bindUser;
	}

	public void setBindUser(User bindUser) {
		this.bindUser = bindUser;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="绑定时间不能为空")
	@ExcelField(title="绑定时间", align=2, sort=11)
	public Date getBindDate() {
		return bindDate;
	}

	public void setBindDate(Date bindDate) {
		this.bindDate = bindDate;
	}
	
	public Date getBeginBindDate() {
		return beginBindDate;
	}

	public void setBeginBindDate(Date beginBindDate) {
		this.beginBindDate = beginBindDate;
	}
	
	public Date getEndBindDate() {
		return endBindDate;
	}

	public void setEndBindDate(Date endBindDate) {
		this.endBindDate = endBindDate;
	}
		
}