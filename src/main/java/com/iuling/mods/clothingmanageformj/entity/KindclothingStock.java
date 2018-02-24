/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.entity;

import com.iuling.comm.utils.excel.annotation.ExcelField;
import com.iuling.core.persistence.DataEntity;
import com.iuling.mods.base.entity.KindClothing;
import com.iuling.mods.base.entity.Shelves;
import com.iuling.mods.base.entity.Supplier;
import com.iuling.mods.base.entity.Warehouse;

import javax.validation.constraints.NotNull;

/**
 * 样衣库存Entity
 * @author 彭成
 * @version 2017-09-22
 */
public class KindclothingStock extends DataEntity<KindclothingStock> {
	
	private static final long serialVersionUID = 1L;
	private String barCode;		// 样衣条码
	private String name;		// 样衣品名
	private Supplier supplier;		// 供应商
	private Integer bindStatus;		// 绑定状态 1、未绑定   2、已绑定
	private String epc;		// 样衣标签
	private Integer shelvesStatus;		// 上架状态 1、未上架   2、已上架
	private Shelves shelves;		// 货架
	private String isLend;		// 是否借出 1、在库   2、借出

	private Warehouse warehouse;   //仓位
	private KindClothing kindClothing;  //样衣

	private String panCode;      //版单号
	private String colorName;     //样衣颜色
	private String style;        //风格
	private String img;          //样衣图片
	private String supplierName;
	private String warehouseName;
	private String shelvesName;
	private String editionnumber;
	private String sizesName;           //样衣尺码
	private String baseCommonRole;		//借用人

//	private Date shelvesDate;		// 上架时间
//	private Date beginShelvesDate;		// 开始 上架时间
//	private Date endShelvesDate;		// 结束 上架时间

	public String getEditionnumber() {
		return editionnumber;
	}

	public void setEditionnumber(String editionnumber) {
		this.editionnumber = editionnumber;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getPanCode() {
		return panCode;
	}

	public void setPanCode(String panCode) {
		this.panCode = panCode;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public KindclothingStock() {
		super();
	}

	public KindclothingStock(String id){
		super(id);
	}

	@ExcelField(title="样衣条码", align=2, sort=6)
	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	
	@ExcelField(title="样衣品名", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="供应商不能为空")
	@ExcelField(title="供应商", fieldType=Supplier.class, value="supplier.name", align=2, sort=8)
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	@NotNull(message="绑定状态不能为空")
	@ExcelField(title="绑定状态", dictType="stock_bind_status", align=2, sort=9)
	public Integer getBindStatus() {
		return bindStatus;
	}

	public void setBindStatus(Integer bindStatus) {
		this.bindStatus = bindStatus;
	}
	
	@ExcelField(title="样衣标签", align=2, sort=10)
	public String getEpc() {
		return epc;
	}

	public void setEpc(String epc) {
		this.epc = epc;
	}
	
	@NotNull(message="上架状态不能为空")
	@ExcelField(title="上架状态", dictType="stock_shelves_status", align=2, sort=11)
	public Integer getShelvesStatus() {
		return shelvesStatus;
	}

	public void setShelvesStatus(Integer shelvesStatus) {
		this.shelvesStatus = shelvesStatus;
	}
	
	@NotNull(message="货架不能为空")
	@ExcelField(title="货架", fieldType=Shelves.class, value="shelves.name", align=2, sort=12)
	public Shelves getShelves() {
		return shelves;
	}

	public void setShelves(Shelves shelves) {
		this.shelves = shelves;
	}
	
	@ExcelField(title="是否借出", dictType="lend_status", align=2, sort=13)
	public String getIsLend() {
		return isLend;
	}

	public void setIsLend(String isLend) {
		this.isLend = isLend;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public KindClothing getKindClothing() {
		return kindClothing;
	}

	public void setKindClothing(KindClothing kindClothing) {
		this.kindClothing = kindClothing;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public String getShelvesName() {
		return shelvesName;
	}

	public void setShelvesName(String shelvesName) {
		this.shelvesName = shelvesName;
	}

	public String getSizesName() {
		return sizesName;
	}

	public void setSizesName(String sizesName) {
		this.sizesName = sizesName;
	}

	public String getBaseCommonRole() {
		return baseCommonRole;
	}

	public void setBaseCommonRole(String baseCommonRole) {
		this.baseCommonRole = baseCommonRole;
	}

	/*public Date getBeginShelvesDate() {
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

	public Date getShelvesDate() {
		return shelvesDate;
	}

	public void setShelvesDate(Date shelvesDate) {
		this.shelvesDate = shelvesDate;
	}*/
}