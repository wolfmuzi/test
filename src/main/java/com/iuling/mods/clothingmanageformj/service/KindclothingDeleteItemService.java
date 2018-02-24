/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.clothingmanageformj.entity.KindclothingDeleteItem;
import com.iuling.mods.clothingmanageformj.mapper.KindclothingDeleteItemMapper;

/**
 * 样衣销毁详情Service
 * @author 潘俞再
 * @version 2017-10-19
 */
@Service
@Transactional(readOnly = true)
public class KindclothingDeleteItemService extends CrudService<KindclothingDeleteItemMapper, KindclothingDeleteItem> {

	public KindclothingDeleteItem get(String id) {
		return super.get(id);
	}
	
	public List<KindclothingDeleteItem> findList(KindclothingDeleteItem kindclothingDeleteItem) {
		return super.findList(kindclothingDeleteItem);
	}
	
	public Page<KindclothingDeleteItem> findPage(Page<KindclothingDeleteItem> page, KindclothingDeleteItem kindclothingDeleteItem) {
		return super.findPage(page, kindclothingDeleteItem);
	}
	
	@Transactional(readOnly = false)
	public void save(KindclothingDeleteItem kindclothingDeleteItem) {
		super.save(kindclothingDeleteItem);
	}
	
	@Transactional(readOnly = false)
	public void delete(KindclothingDeleteItem kindclothingDeleteItem) {
		super.delete(kindclothingDeleteItem);
	}
	
}