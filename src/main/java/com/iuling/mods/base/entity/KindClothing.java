/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iuling.comm.utils.excel.annotation.ExcelField;
import com.iuling.comm.utils.excel.annotation.HiddenColums;
import com.iuling.core.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 样衣基础信息管理Entity
 * @author 宋小雄
 * @version 2017-09-19
 */
@HiddenColums(colums = {2,15,16,16})
public class KindClothing extends DataEntity<KindClothing> {
	
	private static final long serialVersionUID = 1L;
	private String pictures;		// 样衣图片
	private String number;		// 样衣条码
	private String name;		// 品名
	private String style;		// 风格
	private String editionNumber;		// 版单号
	private Supplier supplier;		// 供应商
	private Warehouse warehouse;		// 仓位
	private Colors colour;		// 颜色
	private MaxClass maxClass;    //大类
	private Sizes sizes;      //尺码
	private String season;    //季节
	private String year;     //年份

	private Date shelvesDate;		//上架时间
	private Date beginShelvesDate;		// 开始 上架时间
	private Date endShelvesDate;		// 结束 上架时间

	private Date beginCreateDate;		// 开始 创建时间
	private Date endCreateDate;		// 结束 创建时间

	private String epc;
	private String supplierName;

	private Integer shelvesStatus; //上架状态

	public KindClothing() {
		super();
	}

	public KindClothing(String id){
		super(id);
	}

	@Override
	public String getId() {
		return super.getId();
	}

	@ExcelField(title="样衣图片", align=2, sort=2)
	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}
	
	@ExcelField(title="样衣条码", align=2, sort=3)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@ExcelField(title="品名", align=2, sort=4)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotNull(message="大类不能为空")
	@ExcelField(title="大类代码", fieldType=MaxClass.class, value="maxClass.code", align=2, sort=5)
	public MaxClass getMaxClass() {
		return maxClass;
	}

	public void setMaxClass(MaxClass maxClass) {
		this.maxClass = maxClass;
	}

	@NotNull(message="颜色不能为空")
	@ExcelField(title="颜色编码", fieldType=Colors.class, value="colour.number", align=2, sort=6)
	public Colors getColourNumber() {
		return colour;
	}

	@NotNull(message="尺码不能为空")
	@ExcelField(title="尺码代码", fieldType=Sizes.class, value="sizes.code", align=2, sort=7)
	public Sizes getSizes() {
		return sizes;
	}
	
	@ExcelField(title="版单号", align=2, sort=8)
	public String getEditionNumber() {
		return editionNumber;
	}

	@ExcelField(title="季节", align=2, sort=9)
	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	@ExcelField(title="年份", align=2, sort=10)
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@ExcelField(title="风格", align=2, sort=11)
	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	@NotNull(message="大类不能为空")
	@ExcelField(title="大类名称", fieldType=MaxClass.class, value="maxClass.name", align=2, sort=12)
	public MaxClass getMaxClassName() {
		return maxClass;
	}

	public void setEditionNumber(String editionNumber) {
		this.editionNumber = editionNumber;
	}

	@NotNull(message="颜色不能为空")
	@ExcelField(title="颜色", fieldType=Colors.class, value="colour.name", align=2, sort=13)
	public Colors getColour() {
		return colour;
	}

	@NotNull(message="尺码不能为空")
	@ExcelField(title="尺码名称", fieldType=Sizes.class, value="sizes.name", align=2, sort=14)
	public Sizes getSizesName() {
		return sizes;
	}

	public void setSizes(Sizes sizes) {
		this.sizes = sizes;
	}
	
	@ExcelField(title="供应商名称", fieldType=Supplier.class, value="supplier.name", align=2, sort=15)
	public Supplier getSupplier() {
		return supplier;
	}

	@ExcelField(title="供应商编码", fieldType=Supplier.class, value="supplier.number", align=2, sort=16)
	public Supplier getSupplierNumber() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	

	@ExcelField(title="仓位编码", fieldType=Warehouse.class, value="warehouse.number", align=2, sort=17)
	public Warehouse getWarehouseNumber() {
		return warehouse;
	}

	@ExcelField(title="仓位", fieldType=Warehouse.class, value="warehouse.name", align=2, sort=18)
	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public String getEpc() {
		return epc;
	}

	public void setEpc(String epc) {
		this.epc = epc;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public void setColour(Colors colour) {
		this.colour = colour;
	}
	@ExcelField(title="创建时间", align=2, sort=19)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@ExcelField(title="备注信息", align=2, sort=20)
	@Length(min=0, max=255)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getShelvesStatus() {
		return shelvesStatus;
	}

	public void setShelvesStatus(Integer shelvesStatus) {
		this.shelvesStatus = shelvesStatus;
	}

	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}

	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
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

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getShelvesDate() {
		return shelvesDate;
	}

	public void setShelvesDate(Date shelvesDate) {
		this.shelvesDate = shelvesDate;
	}
}