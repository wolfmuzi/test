/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iuling.comm.utils.excel.annotation.ExcelField;
import com.iuling.core.persistence.DataEntity;
import com.iuling.mods.base.entity.BaseCommonRole;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 样衣归还管理Entity
 * @author 彭成
 * @version 2017-09-23
 */
@JsonIgnoreProperties({"createBy","updateBy"})
public class KindclothingReturn extends DataEntity<KindclothingReturn> {
	
	private static final long serialVersionUID = 1L;
	private BaseCommonRole baseCommonRole;		// 归还人
	private String returnNo;		// 归还单号
	private String roleType;		// 类别
	private Integer surplusNum;		// 剩余未归还总数
	private Integer returnNum;		// 本次归还
	private Date returnDate;		// 归还时间
	private Date beginReturnDate;		// 开始 归还时间
	private Date endReturnDate;		// 结束 归还时间
	private String baseCommonRoleName;  //归还人名称


	public KindclothingReturn() {
		super();
	}

	public KindclothingReturn(String id){
		super(id);
	}

	@NotNull(message="归还人不能为空")
	@ExcelField(title="归还人", fieldType=BaseCommonRole.class, value="baseCommonRole.name", align=2, sort=5)
	public BaseCommonRole getBaseCommonRole() {
		return baseCommonRole;
	}

	public void setBaseCommonRole(BaseCommonRole baseCommonRole) {
		this.baseCommonRole = baseCommonRole;
	}
	
	@ExcelField(title="归还单号", align=2, sort=6)
	public String getReturnNo() {
		return returnNo;
	}

	public void setReturnNo(String returnNo) {
		this.returnNo = returnNo;
	}
	
	@ExcelField(title="类别", align=2, sort=7)
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	@NotNull(message="剩余未归还总数不能为空")
	@ExcelField(title="剩余未归还总数", align=2, sort=8)
	public Integer getSurplusNum() {
		return surplusNum;
	}

	public void setSurplusNum(Integer surplusNum) {
		this.surplusNum = surplusNum;
	}
	
	@NotNull(message="本次归还不能为空")
	@ExcelField(title="本次归还", align=2, sort=9)
	public Integer getReturnNum() {
		return returnNum;
	}

	public void setReturnNum(Integer returnNum) {
		this.returnNum = returnNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="归还时间不能为空")
	@ExcelField(title="归还时间", align=2, sort=10)
	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	
	public Date getBeginReturnDate() {
		return beginReturnDate;
	}

	public void setBeginReturnDate(Date beginReturnDate) {
		this.beginReturnDate = beginReturnDate;
	}
	
	public Date getEndReturnDate() {
		return endReturnDate;
	}

	public void setEndReturnDate(Date endReturnDate) {
		this.endReturnDate = endReturnDate;
	}

	public String getBaseCommonRoleName() {
		return baseCommonRoleName;
	}

	public void setBaseCommonRoleName(String baseCommonRoleName) {
		this.baseCommonRoleName = baseCommonRoleName;
	}
}