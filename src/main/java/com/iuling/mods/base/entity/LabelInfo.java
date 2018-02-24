/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.entity;


import com.iuling.comm.utils.excel.annotation.HiddenColums;
import com.iuling.core.persistence.DataEntity;
import com.iuling.comm.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

/**
 * 标签信息管理Entity
 * @author 宋小雄
 * @version 2017-09-19
 */
@HiddenColums(colums = {1})
public class LabelInfo extends DataEntity<LabelInfo> {
	
	private static final long serialVersionUID = 1L;
	private String number;		// 标签编码
	private String status;		// 状态
	private String type;		// 类型
	
	public LabelInfo() {
		super();
	}

	public LabelInfo(String id){
		super(id);
	}
	@ExcelField(title="ID", align=2, sort=1)
	@Override
	public String getId() {
		return super.getId();
	}
	@ExcelField(title="标签编码", align=2, sort=2)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@ExcelField(title="状态", dictType="label_info_status", align=2, sort=3)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title="类型", dictType="label_info_type", align=2, sort=4)
	public String getType() {
		return type;
	}

	public void setType(String type) {
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