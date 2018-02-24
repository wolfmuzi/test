/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.clothingmanageformj.entity.KindclothingUnbindItem;
import com.iuling.mods.clothingmanageformj.mapper.KindclothingUnbindItemMapper;

/**
 * 样衣解绑详情Service
 * @author 潘俞再
 * @version 2017-10-19
 */
@Service
@Transactional(readOnly = true)
public class KindclothingUnbindItemService extends CrudService<KindclothingUnbindItemMapper, KindclothingUnbindItem> {

	public KindclothingUnbindItem get(String id) {
		return super.get(id);
	}
	
	public List<KindclothingUnbindItem> findList(KindclothingUnbindItem kindclothingUnbindItem) {
		return super.findList(kindclothingUnbindItem);
	}
	
	public Page<KindclothingUnbindItem> findPage(Page<KindclothingUnbindItem> page, KindclothingUnbindItem kindclothingUnbindItem) {
		return super.findPage(page, kindclothingUnbindItem);
	}
	
	@Transactional(readOnly = false)
	public void save(KindclothingUnbindItem kindclothingUnbindItem) {
		super.save(kindclothingUnbindItem);
	}
	
	@Transactional(readOnly = false)
	public void delete(KindclothingUnbindItem kindclothingUnbindItem) {
		super.delete(kindclothingUnbindItem);
	}
	
}