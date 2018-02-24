/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iuling.mods.base.entity.Fabric;
import com.iuling.mods.base.entity.Supplier;
import javax.validation.constraints.NotNull;
import com.iuling.mods.sys.entity.User;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.iuling.core.persistence.DataEntity;
import com.iuling.comm.utils.excel.annotation.ExcelField;

/**
 * 面料色卡绑定记录Entity
 * @author 潘俞再
 * @version 2017-09-21
 */
@JsonIgnoreProperties({"createBy","updateBy"})
public class FabricBind extends DataEntity<FabricBind> {
	
	private static final long serialVersionUID = 1L;
	private String barCode;		// 色卡编码
	private String epc;		// 色卡标签号
	private Fabric fabric;
	private String name;		// 色卡名称
	private Supplier supplier;		//   供应商
	private User user;		// 绑定人员
	private Date bindDate;		// 绑定时间
	private Date beginBindDate;		// 开始 绑定时间
	private Date endBindDate;		// 结束 绑定时间
	
	public FabricBind() {
		super();
	}

	public FabricBind(String id){
		super(id);
	}

	@ExcelField(title="色卡编码", align=2, sort=6)
	public String getBarCode() {
		return barCode;
	}

	public Fabric getFabric() {
		return fabric;
	}

	public void setFabric(Fabric fabric) {
		this.fabric = fabric;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	
	@ExcelField(title="色卡标签号", align=2, sort=7)
	public String getEpc() {
		return epc;
	}

	public void setEpc(String epc) {
		this.epc = epc;
	}
	
	@ExcelField(title="色卡名称", align=2, sort=8)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="  供应商不能为空")
	@ExcelField(title="  供应商", fieldType=Supplier.class, value="supplier.name", align=2, sort=9)
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	@ExcelField(title="绑定人员", fieldType=User.class, value="user.name", align=2, sort=10)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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