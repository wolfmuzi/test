/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iuling.mods.sys.entity.User;

import com.iuling.core.persistence.DataEntity;
import com.iuling.comm.utils.excel.annotation.ExcelField;

/**
 * 色卡盘点Entity
 * @author 潘俞再
 * @version 2017-09-22
 */
@JsonIgnoreProperties({"createBy","updateBy"})
public class FabricInventory extends DataEntity<FabricInventory> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 盘点单号
	private Integer inventoryNum;		// 实盘数量
	private Integer stockNum;		// 账面数量
	private Integer kNum;		// 盘亏
	private Integer yNum;		// 盘盈
	private Date inventoryDate;		// 盘点时间
	private User inventoryUser;		//   盘点人员
	private Integer status;		//   处理状态
	private List<FabricInventoryItme> yList;//盘盈列表
	private List<FabricInventoryItme> kList;//盘亏列表
	public FabricInventory() {
		super();
	}

	public List<FabricInventoryItme> getyList() {
		return yList;
	}

	public void setyList(List<FabricInventoryItme> yList) {
		this.yList = yList;
	}

	public List<FabricInventoryItme> getkList() {
		return kList;
	}

	public void setkList(List<FabricInventoryItme> kList) {
		this.kList = kList;
	}

	public FabricInventory(String id){
		super(id);
	}

	public Integer getkNum() {
		return kNum;
	}

	public void setkNum(Integer kNum) {
		this.kNum = kNum;
	}

	public Integer getyNum() {
		return yNum;
	}

	public void setyNum(Integer yNum) {
		this.yNum = yNum;
	}

	@ExcelField(title="盘点单号", align=2, sort=6)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="实盘数量", align=2, sort=7)
	public Integer getInventoryNum() {
		return inventoryNum;
	}

	public void setInventoryNum(Integer inventoryNum) {
		this.inventoryNum = inventoryNum;
	}
	
	@ExcelField(title="账面数量", align=2, sort=8)
	public Integer getStockNum() {
		return stockNum;
	}

	public void setStockNum(Integer stockNum) {
		this.stockNum = stockNum;
	}
	
	@ExcelField(title="盘亏", align=2, sort=9)
	public Integer getKNum() {
		return kNum;
	}

	public void setKNum(Integer kNum) {
		this.kNum = kNum;
	}
	
	@ExcelField(title="盘盈", align=2, sort=10)
	public Integer getYNum() {
		return yNum;
	}

	public void setYNum(Integer yNum) {
		this.yNum = yNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="盘点时间", align=2, sort=11)
	public Date getInventoryDate() {
		return inventoryDate;
	}

	public void setInventoryDate(Date inventoryDate) {
		this.inventoryDate = inventoryDate;
	}
	
	@ExcelField(title="  盘点人员", fieldType=User.class, value="inventoryUser.name", align=2, sort=12)
	public User getInventoryUser() {
		return inventoryUser;
	}

	public void setInventoryUser(User inventoryUser) {
		this.inventoryUser = inventoryUser;
	}
	
	@ExcelField(title="  处理状态", dictType="inventory_status", align=2, sort=13)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}