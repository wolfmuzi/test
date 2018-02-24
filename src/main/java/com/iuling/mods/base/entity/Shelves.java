/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.entity;

import com.iuling.comm.utils.excel.annotation.HiddenColums;
import com.iuling.mods.base.entity.Warehouse;
import javax.validation.constraints.NotNull;
import com.iuling.mods.base.entity.Supplier;

import com.iuling.core.persistence.DataEntity;
import com.iuling.comm.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

/**
 * 货架管理Entity
 * @author 潘俞再
 * @version 2017-09-21
 */
@HiddenColums(colums = {1})
public class Shelves extends DataEntity<Shelves> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 货架条码
	private String name;		// 货架名称
	private Warehouse warehouse;		// 仓库
	private Supplier supplier;		// 供应商
	private Integer isBind;		// 是否绑定条码
	private String type;		// 货架类型
	private Integer sumNum;//上架总数

	public Integer getSumNum() {
		return sumNum;
	}

	public void setSumNum(Integer sumNum) {
		this.sumNum = sumNum;
	}

	public Shelves() {
		super();
	}



	public Shelves(String id){
		super(id);
	}

	@ExcelField(title="ID", align=2, sort=1)
	@Override
	public String getId() {
		return super.getId();
	}

	@ExcelField(title="货架条码", align=2, sort=2)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="货架名称", align=2, sort=3)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="仓库不能为空")
	@ExcelField(title="仓库", fieldType=Warehouse.class, value="warehouse.name", align=2, sort=4)
	public Warehouse getWarehouse() {
		return warehouse;
	}

	@NotNull(message="仓库编码不能为空")
	@ExcelField(title="仓库编码", fieldType=Warehouse.class, value="warehouse.number", align=2, sort=5)
	public Warehouse getWarehouseNumber() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	
	@NotNull(message="供应商不能为空")
	@ExcelField(title="供应商", fieldType=Supplier.class, value="supplier.name", align=2, sort=6)
	public Supplier getSupplier() {
		return supplier;
	}

	@NotNull(message="供应商编码不能为空")
	@ExcelField(title="供应商编码", fieldType=Supplier.class, value="supplier.number", align=2, sort=7)
	public Supplier getSupplierNumber() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	@ExcelField(title="是否绑定条码", dictType="shelves_is_bind", align=2, sort=8)
	public Integer getIsBind() {
		return isBind;
	}

	public void setIsBind(Integer isBind) {
		this.isBind = isBind;
	}
	
	@ExcelField(title="货架类型", dictType="shelves_type", align=2, sort=9)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@ExcelField(title="备注信息", align=2, sort=10)
	@Length(min=0, max=255)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


}