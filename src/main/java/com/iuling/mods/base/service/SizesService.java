/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.base.entity.Sizes;
import com.iuling.mods.base.mapper.SizesMapper;

/**
 * 尺码信息管理Service
 * @author 彭成
 * @version 2017-10-19
 */
@Service
@Transactional(readOnly = true)
public class SizesService extends CrudService<SizesMapper, Sizes> {

	public Sizes get(String id) {
		return super.get(id);
	}
	
	public List<Sizes> findList(Sizes sizes) {
		return super.findList(sizes);
	}
	
	public Page<Sizes> findPage(Page<Sizes> page, Sizes sizes) {
		return super.findPage(page, sizes);
	}
	
	@Transactional(readOnly = false)
	public void save(Sizes sizes) {
		super.save(sizes);
	}
	
	@Transactional(readOnly = false)
	public void delete(Sizes sizes) {
		super.delete(sizes);
	}
	
}