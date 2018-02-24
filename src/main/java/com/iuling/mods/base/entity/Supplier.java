/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.entity;


import com.iuling.comm.utils.excel.annotation.ExcelField;
import com.iuling.comm.utils.excel.annotation.HiddenColums;
import com.iuling.core.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 供应商管理Entity
 * @author 宋小雄
 * @version 2017-09-20
 */
@HiddenColums(colums = {1,7})
public class Supplier extends DataEntity<Supplier> {
	
	private static final long serialVersionUID = 1L;
	private String number;		// 供应商编码
	private String name;		// 供应商名称
	private String address;		// 供应商地址
	private String contacts;		// 联系人
	private String phone;		// 联系电话
	private Integer type;		// 供应商类型
	private String yue;         //月份

	private Integer updateNum;
	private Integer getNum;
	private Integer orderNum;

	public String getYue() {
		return yue;
	}

	public void setYue(String yue) {
		this.yue = yue;
	}

	public Integer getUpdateNum() {
		return updateNum;
	}

	public void setUpdateNum(Integer updateNum) {
		this.updateNum = updateNum;
	}

	public Integer getGetNum() {
		return getNum;
	}

	public void setGetNum(Integer getNum) {
		this.getNum = getNum;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Supplier() {
		super();
	}

	public Supplier(String id){
		super(id);
	}
	@ExcelField(title="ID", align=2, sort=1)
	@Override
	public String getId() {
		return super.getId();
	}
	@ExcelField(title="供应商编码", align=2, sort=2)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@ExcelField(title="供应商名称", align=2, sort=3)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotNull(message="供应商类型不能为空")
	@ExcelField(title="供应商类型", align=2, sort=4)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="供应商地址", align=2, sort=5)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="联系人", align=2, sort=6)
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	
	@ExcelField(title="联系电话", align=2, sort=7)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@ExcelField(title="备注信息", align=2, sort=8)
	@Length(min=0, max=255)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}