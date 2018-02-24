/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iuling.mods.base.entity.BaseCommonRole;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.iuling.core.persistence.DataEntity;
import com.iuling.comm.utils.excel.annotation.ExcelField;

/**
 * 色卡调布Entity
 * @author 潘俞再
 * @version 2017-09-23
 */
@JsonIgnoreProperties({"createBy","updateBy"})
public class FabricOrder extends DataEntity<FabricOrder> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 调布单号
	private BaseCommonRole commonRole;		// 设计师
	private Date orderDate;		//   调布时间
	private Integer num;		// 数量
	private Integer status;		// 状态
	private Date beginOrderDate;		// 开始   调布时间
	private Date endOrderDate;		// 结束   调布时间
	
	public FabricOrder() {
		super();
	}

	public FabricOrder(String id){
		super(id);
	}

	@ExcelField(title="调布单号", align=2, sort=6)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="设计师", align=2, sort=7)
	public BaseCommonRole getCommonRole() {
		return commonRole;
	}

	public void setCommonRole(BaseCommonRole commonRole) {
		this.commonRole = commonRole;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="  调布时间", align=2, sort=8)
	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	@ExcelField(title="数量", align=2, sort=9)
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	@ExcelField(title="状态", dictType="fabeic_order_status", align=2, sort=10)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Date getBeginOrderDate() {
		return beginOrderDate;
	}

	public void setBeginOrderDate(Date beginOrderDate) {
		this.beginOrderDate = beginOrderDate;
	}
	
	public Date getEndOrderDate() {
		return endOrderDate;
	}

	public void setEndOrderDate(Date endOrderDate) {
		this.endOrderDate = endOrderDate;
	}
		
}