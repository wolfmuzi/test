/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iuling.mods.sys.entity.User;

import com.iuling.core.persistence.DataEntity;
import com.iuling.comm.utils.excel.annotation.ExcelField;

/**
 * 面料销毁记录Entity
 * @author 潘俞再
 * @version 2017-10-18
 */
@JsonIgnoreProperties({"createBy","updateBy"})
public class FabricDelete extends DataEntity<FabricDelete> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 销毁单号
	private Date deleteDate;		// 销毁时间
	private User deleteUser;		// 销毁人员
	private Integer num;		// 销毁数量
	private Date beginDeleteDate;		// 开始 销毁时间
	private Date endDeleteDate;		// 结束 销毁时间
	
	public FabricDelete() {
		super();
	}

	public FabricDelete(String id){
		super(id);
	}

	@ExcelField(title="销毁单号", align=2, sort=6)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="销毁时间", align=2, sort=7)
	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}
	
	@ExcelField(title="销毁人员", fieldType=User.class, value="deleteUser.name", align=2, sort=8)
	public User getDeleteUser() {
		return deleteUser;
	}

	public void setDeleteUser(User deleteUser) {
		this.deleteUser = deleteUser;
	}
	
	@ExcelField(title="销毁数量", align=2, sort=9)
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	public Date getBeginDeleteDate() {
		return beginDeleteDate;
	}

	public void setBeginDeleteDate(Date beginDeleteDate) {
		this.beginDeleteDate = beginDeleteDate;
	}
	
	public Date getEndDeleteDate() {
		return endDeleteDate;
	}

	public void setEndDeleteDate(Date endDeleteDate) {
		this.endDeleteDate = endDeleteDate;
	}
		
}