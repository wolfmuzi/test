/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iuling.mods.sys.entity.User;

import com.iuling.core.persistence.DataEntity;
import com.iuling.comm.utils.excel.annotation.ExcelField;

/**
 * 样衣解绑记录Entity
 * @author 彭成
 * @version 2017-10-19
 */
@JsonIgnoreProperties({"createBy","updateBy"})
public class KindclothingUnbind extends DataEntity<KindclothingUnbind> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 解绑单号
	private Integer num;		// 解绑数量
	private Date unbindDate;		// 解绑时间
	private User unbindUser;		// 解绑人员
	private Date beginUnbindDate;		// 开始 解绑时间
	private Date endUnbindDate;		// 结束 解绑时间
	
	public KindclothingUnbind() {
		super();
	}

	public KindclothingUnbind(String id){
		super(id);
	}

	@ExcelField(title="解绑单号", align=2, sort=6)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@NotNull(message="解绑数量不能为空")
	@ExcelField(title="解绑数量", align=2, sort=7)
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="解绑时间不能为空")
	@ExcelField(title="解绑时间", align=2, sort=8)
	public Date getUnbindDate() {
		return unbindDate;
	}

	public void setUnbindDate(Date unbindDate) {
		this.unbindDate = unbindDate;
	}
	
	@NotNull(message="解绑人员不能为空")
	@ExcelField(title="解绑人员", fieldType=User.class, value="unbindUser.name", align=2, sort=9)
	public User getUnbindUser() {
		return unbindUser;
	}

	public void setUnbindUser(User unbindUser) {
		this.unbindUser = unbindUser;
	}
	
	public Date getBeginUnbindDate() {
		return beginUnbindDate;
	}

	public void setBeginUnbindDate(Date beginUnbindDate) {
		this.beginUnbindDate = beginUnbindDate;
	}
	
	public Date getEndUnbindDate() {
		return endUnbindDate;
	}

	public void setEndUnbindDate(Date endUnbindDate) {
		this.endUnbindDate = endUnbindDate;
	}
		
}