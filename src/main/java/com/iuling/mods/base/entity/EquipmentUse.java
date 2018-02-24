/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iuling.comm.utils.excel.annotation.ExcelField;
import com.iuling.comm.utils.excel.annotation.HiddenColums;
import com.iuling.core.persistence.DataEntity;
import com.iuling.mods.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 设备使用记录Entity
 * @author 宋小雄
 * @version 2017-09-19
 */
@HiddenColums(colums = {1})
public class EquipmentUse extends DataEntity<EquipmentUse> {
	
	private static final long serialVersionUID = 1L;
	private Equipment equipment;		// 设备编码
	private User user;		// 使用者
	private Date startTime;		// 开始使用时间
	private Date endTime;		// 结束使用时间
	
	public EquipmentUse() {
		super();
	}

	public EquipmentUse(String id){
		super(id);
	}
	@ExcelField(title="ID", align=2, sort=1)
	@Override
	public String getId() {
		return super.getId();
	}
	@ExcelField(title="设备编码", fieldType=Equipment.class, value="equipment.number", align=2, sort=2)
	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
	
	@ExcelField(title="使用者", fieldType=User.class, value="user.name", align=2, sort=3)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="开始使用时间", align=2, sort=4)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="结束使用时间", align=2, sort=5)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@ExcelField(title="备注信息", align=2, sort=6)
	@Length(min=0, max=255)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}