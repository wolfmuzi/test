/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.entity;


import com.iuling.core.persistence.DataEntity;
import com.iuling.comm.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

/**
 * 流水号编码信息Entity
 * @author 宋小雄
 * @version 2017-09-18
 */
public class SerialCode extends DataEntity<SerialCode> {
	
	private static final long serialVersionUID = 1L;
	private String serialCode;		// 流水号编码
	
	public SerialCode() {
		super();
	}
	public SerialCode(String id,String serialCode) {
		super();
		this.serialCode = serialCode;
	}

	public SerialCode(String id){
		super(id);
	}

	@ExcelField(title="流水号编码", align=2, sort=1)
	public String getSerialCode() {
		return serialCode;
	}

	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}

	@ExcelField(title="备注信息", align=2, sort=2)
	@Length(min=0, max=255)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}