/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.service;

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.base.entity.MaxClass;
import com.iuling.mods.base.mapper.MaxClassMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 大类信息管理Service
 * @author 彭成
 * @version 2017-10-19
 */
@Service
@Transactional(readOnly = true)
public class MaxClassService extends CrudService<MaxClassMapper, MaxClass> {

	public MaxClass get(String id) {
		return super.get(id);
	}
	
	public List<MaxClass> findList(MaxClass maxClass) {
		return super.findList(maxClass);
	}
	
	public Page<MaxClass> findPage(Page<MaxClass> page, MaxClass maxClass) {
		return super.findPage(page, maxClass);
	}
	
	@Transactional(readOnly = false)
	public void save(MaxClass maxClass) {
		super.save(maxClass);
	}
	
	@Transactional(readOnly = false)
	public void delete(MaxClass maxClass) {
		super.delete(maxClass);
	}
	
}