/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.service;

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.clothingmanageformj.entity.KindclothingInventoryItem;
import com.iuling.mods.clothingmanageformj.mapper.KindclothingInventoryItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 样衣盘点详情Service
 * @author 潘俞再
 * @version 2017-09-26
 */
@Service
@Transactional(readOnly = true)
public class KindclothingInventoryItemService extends CrudService<KindclothingInventoryItemMapper, KindclothingInventoryItem> {

	@Autowired
	private KindclothingInventoryItemMapper kindclothingInventoryItemMapper;

	public KindclothingInventoryItem get(String id) {
		return super.get(id);
	}
	
	public List<KindclothingInventoryItem> findList(KindclothingInventoryItem kindclothingInventoryItem) {
		return super.findList(kindclothingInventoryItem);
	}
	
	public Page<KindclothingInventoryItem> findPage(Page<KindclothingInventoryItem> page, KindclothingInventoryItem kindclothingInventoryItem) {
		dataRuleFilter(kindclothingInventoryItem);
		kindclothingInventoryItem.setPage(page);
		page.setList(kindclothingInventoryItemMapper.findList1(kindclothingInventoryItem));
		return page;
	}

	@Transactional(readOnly = false)
	public void save(KindclothingInventoryItem kindclothingInventoryItem) {
		super.save(kindclothingInventoryItem);
	}
	
	@Transactional(readOnly = false)
	public void delete(KindclothingInventoryItem kindclothingInventoryItem) {
		super.delete(kindclothingInventoryItem);
	}



	//查询盘盈还有未上架的信息
	public List<KindclothingInventoryItem> findByParam(KindclothingInventoryItem entity){
		return kindclothingInventoryItemMapper.findByParam(entity);
	}
}