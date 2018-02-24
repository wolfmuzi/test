/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iuling.comm.utils.excel.annotation.ExcelField;
import com.iuling.core.persistence.DataEntity;
import com.iuling.mods.base.entity.BaseCommonRole;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 样衣借用管理Entity
 * @author 彭成
 * @version 2017-09-22
 */
public class KindclothingLend extends DataEntity<KindclothingLend> {
	
	private static final long serialVersionUID = 1L;
	private BaseCommonRole baseCommonRole;		// 借用人
	private String lendNo;		// 借出单号
	private Integer lendTotal;		// 借用总数
	private Integer lendCurrentNum;		// 本次借用
	private Date lendDate;		// 借用时间
	private Date expectedReturnDate;		// 预计归还时间
	private Integer lendReturnStatus;		// 归还状态   1未归还	2部分归还  3全部归还
	private Date beginLendDate;		// 开始 借用时间
	private Date endLendDate;		// 结束 借用时间

	private String lendName;

	public String getLendName() {
		return lendName;
	}

	public void setLendName(String lendName) {
		this.lendName = lendName;
	}

	public KindclothingLend() {
		super();
	}

	public KindclothingLend(String id){
		super(id);
	}

	@NotNull(message="借用人不能为空")
	@ExcelField(title="借用人", fieldType=BaseCommonRole.class, value="baseCommonRole.name", align=2, sort=6)
	public BaseCommonRole getBaseCommonRole() {
		return baseCommonRole;
	}

	public void setBaseCommonRole(BaseCommonRole baseCommonRole) {
		this.baseCommonRole = baseCommonRole;
	}
	
	@ExcelField(title="借出单号", align=2, sort=7)
	public String getLendNo() {
		return lendNo;
	}

	public void setLendNo(String lendNo) {
		this.lendNo = lendNo;
	}
	
	@NotNull(message="借用总数不能为空")
	@ExcelField(title="借用总数", align=2, sort=8)
	public Integer getLendTotal() {
		return lendTotal;
	}

	public void setLendTotal(Integer lendTotal) {
		this.lendTotal = lendTotal;
	}
	
	@NotNull(message="本次借用不能为空")
	@ExcelField(title="本次借用", align=2, sort=9)
	public Integer getLendCurrentNum() {
		return lendCurrentNum;
	}

	public void setLendCurrentNum(Integer lendCurrentNum) {
		this.lendCurrentNum = lendCurrentNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="借用时间不能为空")
	@ExcelField(title="借用时间", align=2, sort=10)
	public Date getLendDate() {
		return lendDate;
	}

	public void setLendDate(Date lendDate) {
		this.lendDate = lendDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="预计归还时间不能为空")
	@ExcelField(title="预计归还时间", align=2, sort=11)
	public Date getExpectedReturnDate() {
		return expectedReturnDate;
	}

	public void setExpectedReturnDate(Date expectedReturnDate) {
		this.expectedReturnDate = expectedReturnDate;
	}
	
	@NotNull(message="归还状态不能为空")
	@ExcelField(title="归还状态", align=2, sort=12)
	public Integer getLendReturnStatus() {
		return lendReturnStatus;
	}

	public void setLendReturnStatus(Integer lendReturnStatus) {
		this.lendReturnStatus = lendReturnStatus;
	}
	
	public Date getBeginLendDate() {
		return beginLendDate;
	}

	public void setBeginLendDate(Date beginLendDate) {
		this.beginLendDate = beginLendDate;
	}
	
	public Date getEndLendDate() {
		return endLendDate;
	}

	public void setEndLendDate(Date endLendDate) {
		this.endLendDate = endLendDate;
	}

}