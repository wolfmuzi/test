/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.service;

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.base.entity.Colors;
import com.iuling.mods.base.mapper.ColorsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 颜色信息管理Service
 * @author 宋小雄
 * @version 2017-09-20
 */
@Service
@Transactional(readOnly = true)
public class ColorsService extends CrudService<ColorsMapper, Colors> {

	public Colors get(String id) {
		return super.get(id);
	}
	
	public List<Colors> findList(Colors colors) {
		return super.findList(colors);
	}
	
	public Page<Colors> findPage(Page<Colors> page, Colors colors) {
		return super.findPage(page, colors);
	}
	
	@Transactional(readOnly = false)
	public void save(Colors colors) {
		super.save(colors);
	}
	
	@Transactional(readOnly = false)
	public void delete(Colors colors) {
		super.delete(colors);
	}
	
}