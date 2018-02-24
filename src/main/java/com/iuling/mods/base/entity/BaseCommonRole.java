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
 * 员工管理Entity
 * @author 彭成
 * @version 2017-09-22
 */
@HiddenColums(colums = {1})
public class BaseCommonRole extends DataEntity<BaseCommonRole> {
	
	private static final long serialVersionUID = 1L;

	private String code;		// 编码
	private Integer type;		// 类型
	private String yue;
	private String name;
	private Integer getNum;
	private Integer orderNum;

	public String getYue() {
		return yue;
	}

	public void setYue(String yue) {
		this.yue = yue;
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

	public BaseCommonRole() {
		super();
	}

	public BaseCommonRole(String id){
		super(id);
	}

	@ExcelField(title="ID", align=2, sort=1)
	@Override
	public String getId() {
		return super.getId();
	}

	@ExcelField(title="名字", align=2, sort=2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="编码", align=2, sort=3)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@NotNull(message="类型不能为空")
	@ExcelField(title="类型", dictType="role_type", align=2, sort=4)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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