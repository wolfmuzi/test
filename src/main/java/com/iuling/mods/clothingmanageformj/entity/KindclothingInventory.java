/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iuling.comm.utils.excel.annotation.ExcelField;
import com.iuling.core.persistence.DataEntity;
import com.iuling.mods.sys.entity.User;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 样衣盘点Entity
 * @author 彭成
 * @version 2017-09-26
 */
@JsonIgnoreProperties({"createBy","updateBy"})
public class KindclothingInventory extends DataEntity<KindclothingInventory> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 盘点单号
	private Integer inventoryNum;		// 实盘数量
	private Integer stockNum;		// 账面数量
	private Integer kNum;		// 盘盈
	private Integer yNum;		// 盘盈
	private Date inventoryDate;		// 盘点时间
	private Date beginInventoryDate;		// 开始 盘点时间
	private Date endInventoryDate;		// 结束 盘点时间
	private User inventoryUser;		// 盘点人员
	private Integer status;		// 处理状态

	private List<KindclothingInventoryItem> kList;
	private List<KindclothingInventoryItem> yList;

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

	public List<KindclothingInventoryItem> getkList() {
		return kList;
	}

	public void setkList(List<KindclothingInventoryItem> kList) {
		this.kList = kList;
	}

	public List<KindclothingInventoryItem> getyList() {
		return yList;
	}

	public void setyList(List<KindclothingInventoryItem> yList) {
		this.yList = yList;
	}

	public KindclothingInventory() {
		super();
	}

	public KindclothingInventory(String id){
		super(id);
	}

	@ExcelField(title="盘点单号", align=2, sort=6)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@NotNull(message="实盘数量不能为空")
	@ExcelField(title="实盘数量", align=2, sort=7)
	public Integer getInventoryNum() {
		return inventoryNum;
	}

	public void setInventoryNum(Integer inventoryNum) {
		this.inventoryNum = inventoryNum;
	}
	
	@NotNull(message="账面数量不能为空")
	@ExcelField(title="账面数量", align=2, sort=8)
	public Integer getStockNum() {
		return stockNum;
	}

	public void setStockNum(Integer stockNum) {
		this.stockNum = stockNum;
	}
	
	@NotNull(message="盘盈不能为空")
	@ExcelField(title="盘盈", align=2, sort=9)
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
	@NotNull(message="盘点时间不能为空")
	@ExcelField(title="盘点时间", align=2, sort=11)
	public Date getInventoryDate() {
		return inventoryDate;
	}

	public void setInventoryDate(Date inventoryDate) {
		this.inventoryDate = inventoryDate;
	}
	
	@NotNull(message="盘点人员不能为空")
	@ExcelField(title="盘点人员", fieldType=User.class, value="", align=2, sort=12)
	public User getInventoryUser() {
		return inventoryUser;
	}

	public void setInventoryUser(User inventoryUser) {
		this.inventoryUser = inventoryUser;
	}
	
	@ExcelField(title="处理状态", dictType="inventory_status", align=2, sort=13)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getBeginInventoryDate() {
		return beginInventoryDate;
	}

	public void setBeginInventoryDate(Date beginInventoryDate) {
		this.beginInventoryDate = beginInventoryDate;
	}

	public Date getEndInventoryDate() {
		return endInventoryDate;
	}

	public void setEndInventoryDate(Date endInventoryDate) {
		this.endInventoryDate = endInventoryDate;
	}
}