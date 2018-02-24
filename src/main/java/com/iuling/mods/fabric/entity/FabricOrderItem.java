/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.entity;

import com.iuling.mods.base.entity.Supplier;
import com.iuling.mods.base.entity.Colors;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.iuling.mods.fabric.entity.FabricOrder;

import com.iuling.core.persistence.DataEntity;
import com.iuling.comm.utils.excel.annotation.ExcelField;

/**
 * 调布订单详情Entity
 * @author 潘俞再
 * @version 2017-09-23
 */
public class FabricOrderItem extends DataEntity<FabricOrderItem> {
	
	private static final long serialVersionUID = 1L;
	private Supplier supplier;		// 供应商
	private String name;		// 名称
	private Integer num;		// 数量
	private String epc;		// 标签编码
	private Colors color;		// 颜色
	private Double price;		// 下单价格
	private Double price01;		// 价格（含税）
	private Double price02;		// 价格（不含税）
	private Date sendDate;		// 寄版时间
	private Date returnDate;		// 回版时间
	private Integer fstatus;		// 发版状态
	private Date fdate;		// 发版时间
	private String code;		// 版单号
	private String ferror;		// 未发版原因
	private Integer gstatus;		// 过版状态
	private String gerror;		// 未过版原因
	private Date orderDate;		// 下单时间
	private FabricOrder fabricOrder;		// 订单id

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FabricOrderItem() {
		super();
	}

	public FabricOrderItem(String id){
		super(id);
	}

	@ExcelField(title="供应商", fieldType=Supplier.class, value="supplier.name", align=2, sort=6)
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	@ExcelField(title="数量", align=2, sort=7)
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	@ExcelField(title="标签编码", align=2, sort=8)
	public String getEpc() {
		return epc;
	}

	public void setEpc(String epc) {
		this.epc = epc;
	}
	
	@ExcelField(title="颜色", fieldType=Colors.class, value="color.name", align=2, sort=9)
	public Colors getColor() {
		return color;
	}

	public void setColor(Colors color) {
		this.color = color;
	}
	
	@ExcelField(title="价格（含税）", align=2, sort=10)
	public Double getPrice01() {
		return price01;
	}

	public void setPrice01(Double price01) {
		this.price01 = price01;
	}
	
	@ExcelField(title="价格（不含税）", align=2, sort=11)
	public Double getPrice02() {
		return price02;
	}

	public void setPrice02(Double price02) {
		this.price02 = price02;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="寄版时间", align=2, sort=12)
	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="回版时间", align=2, sort=13)
	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	
	@ExcelField(title="发版状态", dictType="fabric_order_f_status", align=2, sort=14)
	public Integer getFstatus() {
		return fstatus;
	}

	public void setFstatus(Integer fstatus) {
		this.fstatus = fstatus;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="发版时间", align=2, sort=15)
	public Date getFdate() {
		return fdate;
	}

	public void setFdate(Date fdate) {
		this.fdate = fdate;
	}


	@ExcelField(title="版单号", align=2, sort=16)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="未发版原因", align=2, sort=17)
	public String getFerror() {
		return ferror;
	}

	public void setFerror(String ferror) {
		this.ferror = ferror;
	}
	
	@ExcelField(title="过版状态", dictType="fabric_order_g_status", align=2, sort=18)
	public Integer getGstatus() {
		return gstatus;
	}

	public void setGstatus(Integer gstatus) {
		this.gstatus = gstatus;
	}
	
	@ExcelField(title="未过版原因", align=2, sort=19)
	public String getGerror() {
		return gerror;
	}

	public void setGerror(String gerror) {
		this.gerror = gerror;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="下单时间", align=2, sort=20)
	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	@ExcelField(title="订单id", fieldType=FabricOrder.class, value="fabricOrder.code", align=2, sort=21)
	public FabricOrder getFabricOrder() {
		return fabricOrder;
	}

	public void setFabricOrder(FabricOrder fabricOrder) {
		this.fabricOrder = fabricOrder;
	}
	
}