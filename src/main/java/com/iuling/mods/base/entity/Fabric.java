/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iuling.comm.utils.excel.annotation.ExcelField;
import com.iuling.comm.utils.excel.annotation.HiddenColums;
import com.iuling.core.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 面料色卡管理Entity
 * @author 宋小雄
 * @version 2017-09-21
 */
@HiddenColums(colums = {3,7,15,16,17})
public class Fabric extends DataEntity<Fabric> {
	
	private static final long serialVersionUID = 1L;
	private String barcode;		// 面料条码
	private String name;		// 品名
	private String number;		// 货号
	private Supplier supplier;		// 供应商
	private Colors color;		// 颜色
	private String weight;		// 克重
	private String width;		// 幅宽
	private String spec;		// 纱织规格
	private String ingredients;		// 成分
	private String price01;		// 价格(含税)
	private String price02;		// 价格(不含税)
	private String shrinkage;		// 缩水率
	private String kg;		// 公斤量化
	private Warehouse warehouse;		// 仓位
	private Date purchaseTime;		// 采购时间
	private Date beginPurchaseTime;		// 开始 采购时间
	private Date endPurchaseTime;		// 结束 采购时间
	private String epc;
	private Shelves shelves;		// 货架
	private String code;
	private String type;
	private String shelvesStatus;

	public String getShelvesStatus() {
		return shelvesStatus;
	}

	public void setShelvesStatus(String shelvesStatus) {
		this.shelvesStatus = shelvesStatus;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Shelves getShelves() {
		return shelves;
	}

	public void setShelves(Shelves shelves) {
		this.shelves = shelves;
	}

	public String getEpc() {
		return epc;
	}

	public void setEpc(String epc) {
		this.epc = epc;
	}

	public Fabric() {
		super();
	}

	public Fabric(String id){
		super(id);
	}

	@Override
	public String getId() {
		return super.getId();
	}

	@ExcelField(title="面料条码", align=2, sort=2)
	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	@ExcelField(title="供应商", fieldType=Supplier.class, value="supplier.name", align=2, sort=3)
	public Supplier getSupplier() {
		return supplier;
	}

	@ExcelField(title="供应商编码", fieldType=Supplier.class, value="supplier.number", align=2, sort=4)
	public Supplier getSupplierNumber() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	@ExcelField(title="面料名称", align=2, sort=5)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="编号", align=2, sort=6)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@ExcelField(title="颜色", fieldType=Colors.class, value="color.name", align=2, sort=7)
	public Colors getColor() {
		return color;
	}

	@ExcelField(title="颜色编码", fieldType=Colors.class, value="color.number", align=2, sort=8)
	public Colors getColorNumber() {
		return color;
	}

	public void setColor(Colors color) {
		this.color = color;
	}

	@ExcelField(title="纱织规格", align=2, sort=9)
	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	@ExcelField(title="成分", align=2, sort=10)
	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}
	
	@ExcelField(title="克重", align=2, sort=11)
	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	@ExcelField(title="幅宽", align=2, sort=12)
	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	@ExcelField(title="缩水率", align=2, sort=13)
	public String getShrinkage() {
		return shrinkage;
	}

	public void setShrinkage(String shrinkage) {
		this.shrinkage = shrinkage;
	}

	@ExcelField(title="价格(不含税)", align=2, sort=14)
	public String getPrice02() {
		return price02;
	}

	public void setPrice02(String price02) {
		this.price02 = price02;
	}

	@ExcelField(title="价格(含税)", align=2, sort=15)
	public String getPrice01() {
		return price01;
	}

	public void setPrice01(String price01) {
		this.price01 = price01;
	}

	@ExcelField(title="公斤量化", align=2, sort=16)
	public String getKg() {
		return kg;
	}

	public void setKg(String kg) {
		this.kg = kg;
	}

	@ExcelField(title="仓位", fieldType=Warehouse.class, value="warehouse.name", align=2, sort=17)
	public Warehouse getWarehouse() {
		return warehouse;
	}

	@ExcelField(title="仓位编码", fieldType=Warehouse.class, value="warehouse.number", align=2, sort=18)
	public Warehouse getWarehouseNumber() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="采购时间", align=2, sort=19)
	public Date getPurchaseTime() {
		return purchaseTime;
	}


	@ExcelField(title="备注信息", align=2, sort=20)
	@Length(min=0, max=255)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setPurchaseTime(Date purchaseTime) {
		this.purchaseTime = purchaseTime;
	}
	
	public Date getBeginPurchaseTime() {
		return beginPurchaseTime;
	}

	public void setBeginPurchaseTime(Date beginPurchaseTime) {
		this.beginPurchaseTime = beginPurchaseTime;
	}
	
	public Date getEndPurchaseTime() {
		return endPurchaseTime;
	}

	public void setEndPurchaseTime(Date endPurchaseTime) {
		this.endPurchaseTime = endPurchaseTime;
	}
		
}