/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Iuling</a> All rights reserved.
 */
package com.iuling.mods.echarts.entity.other;


import com.iuling.core.persistence.DataEntity;
import com.iuling.comm.utils.excel.annotation.ExcelField;

/**
 * 学生Entity
 * @author Sincere
 * @version 2017-06-04
 */
public class TestPieClass extends DataEntity<TestPieClass> {
	
	private static final long serialVersionUID = 1L;
	private String className;		// 班级
	private String num;		// 人数
	
	public TestPieClass() {
		super();
	}

	public TestPieClass(String id){
		super(id);
	}

	@ExcelField(title="班级", align=2, sort=6)
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	@ExcelField(title="人数", align=2, sort=7)
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
}