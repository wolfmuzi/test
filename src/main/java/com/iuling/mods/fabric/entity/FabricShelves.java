/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.entity;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iuling.mods.base.entity.Shelves;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.iuling.mods.sys.entity.User;

import com.iuling.core.persistence.DataEntity;
import com.iuling.comm.utils.excel.annotation.ExcelField;

/**
 * 上架信息Entity
 * @author 潘俞再
 * @version 2017-09-20
 */
@JsonIgnoreProperties({"createBy","updateBy"})
public class FabricShelves extends DataEntity<FabricShelves> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 上架单号
	private Integer num;		// 上架数量
	private Shelves shelves;		// 对应货架
	private Date shelvesDate;		// 上架时间
	private User user;		// 上架人员
	private Date beginShelvesDate;		// 开始 上架时间
	private Date endShelvesDate;		// 结束 上架时间
	
	public FabricShelves() {
		super();
	}

	public FabricShelves(String id){
		super(id);
	}

	@ExcelField(title="上架单号", align=2, sort=6)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@NotNull(message="上架数量不能为空")
	@ExcelField(title="上架数量", align=2, sort=7)
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	@NotNull(message="对应货架不能为空")
	@ExcelField(title="对应货架", fieldType=Shelves.class, value="shelves.name", align=2, sort=8)
	public Shelves getShelves() {
		return shelves;
	}

	public void setShelves(Shelves shelves) {
		this.shelves = shelves;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="上架时间", align=2, sort=9)
	public Date getShelvesDate() {
		return shelvesDate;
	}

	public void setShelvesDate(Date shelvesDate) {
		this.shelvesDate = shelvesDate;
	}
	
	@ExcelField(title="上架人员", fieldType=User.class, value="user.name", align=2, sort=10)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Date getBeginShelvesDate() {
		return beginShelvesDate;
	}

	public void setBeginShelvesDate(Date beginShelvesDate) {
		this.beginShelvesDate = beginShelvesDate;
	}
	
	public Date getEndShelvesDate() {
		return endShelvesDate;
	}

	public void setEndShelvesDate(Date endShelvesDate) {
		this.endShelvesDate = endShelvesDate;
	}
		
}