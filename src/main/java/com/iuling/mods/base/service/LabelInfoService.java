/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.service;

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.base.entity.LabelInfo;
import com.iuling.mods.base.mapper.LabelInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 标签信息管理Service
 * @author 宋小雄
 * @version 2017-09-19
 */
@Service
@Transactional(readOnly = true)
public class LabelInfoService extends CrudService<LabelInfoMapper, LabelInfo> {

	public LabelInfo get(String id) {
		return super.get(id);
	}
	
	public List<LabelInfo> findList(LabelInfo labelInfo) {
		return super.findList(labelInfo);
	}
	
	public Page<LabelInfo> findPage(Page<LabelInfo> page, LabelInfo labelInfo) {
		return super.findPage(page, labelInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(LabelInfo labelInfo) {
		super.save(labelInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(LabelInfo labelInfo) {
		super.delete(labelInfo);
	}

	public void saveOrUpdate(LabelInfo labelInfo) {
		LabelInfo temp = new LabelInfo();
		temp.setNumber(labelInfo.getNumber());
		List<LabelInfo> list = this.findList(temp);
		if(list != null && list.size() > 0){
			this.executeUpdateSql("update base_label_info set status = '"+labelInfo.getStatus()+"' where number = '"+labelInfo.getNumber()+"'");
		}else{
			this.save(labelInfo);
		}
	}
}