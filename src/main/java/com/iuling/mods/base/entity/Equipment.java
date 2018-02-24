/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.entity;


import com.iuling.comm.utils.excel.annotation.HiddenColums;
import com.iuling.core.persistence.DataEntity;
import com.iuling.comm.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

/**
 * 设备管理Entity
 * @author 宋小雄
 * @version 2017-09-19
 */
@HiddenColums(colums = {1})
public class Equipment extends DataEntity<Equipment> {
	
	private static final long serialVersionUID = 1L;
	private String number;		// 设备编码
	private String name;		// 设备名称
	private String status;		// 设备状态
	
	public Equipment() {
		super();
	}

	public Equipment(String id){
		super(id);
	}
	@ExcelField(title="ID", align=2, sort=1)
	@Override
	public String getId() {
		return super.getId();
	}
	@ExcelField(title="设备编码", align=2, sort=2)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@ExcelField(title="设备名称", align=2, sort=3)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="设备状态", dictType="equipment_status", align=2, sort=4)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@ExcelField(title="备注信息", align=2, sort=5)
	@Length(min=0, max=255)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}