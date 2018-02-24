/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.clothingmanageformj.entity.KindclothingShelvesItem;
import com.iuling.mods.clothingmanageformj.mapper.KindclothingShelvesItemMapper;

/**
 * 样衣上架详情Service
 * @author 彭成
 * @version 2017-09-20
 */
@Service
@Transactional(readOnly = true)
public class KindclothingShelvesItemService extends CrudService<KindclothingShelvesItemMapper, KindclothingShelvesItem> {

	public KindclothingShelvesItem get(String id) {
		return super.get(id);
	}
	
	public List<KindclothingShelvesItem> findList(KindclothingShelvesItem kindclothingShelvesItem) {
		return super.findList(kindclothingShelvesItem);
	}
	
	public Page<KindclothingShelvesItem> findPage(Page<KindclothingShelvesItem> page, KindclothingShelvesItem kindclothingShelvesItem) {
		return super.findPage(page, kindclothingShelvesItem);
	}
	
	@Transactional(readOnly = false)
	public void save(KindclothingShelvesItem kindclothingShelvesItem) {
		super.save(kindclothingShelvesItem);
	}
	
	@Transactional(readOnly = false)
	public void delete(KindclothingShelvesItem kindclothingShelvesItem) {
		super.delete(kindclothingShelvesItem);
	}
	
}