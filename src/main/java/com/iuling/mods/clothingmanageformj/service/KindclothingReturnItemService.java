/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.service;

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.clothingmanageformj.entity.KindclothingReturnItem;
import com.iuling.mods.clothingmanageformj.mapper.KindclothingReturnItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 样衣归还详情Service
 * @author 彭成
 * @version 2017-09-23
 */
@Service
@Transactional(readOnly = true)
public class KindclothingReturnItemService extends CrudService<KindclothingReturnItemMapper, KindclothingReturnItem> {

	@Autowired
	private KindclothingReturnItemMapper kindclothingReturnItemMapper;

	public KindclothingReturnItem get(String id) {
		return super.get(id);
	}
	
	public List<KindclothingReturnItem> findList(KindclothingReturnItem kindclothingReturnItem) {
		return super.findList(kindclothingReturnItem);
	}
	
	public Page<KindclothingReturnItem> findPage(Page<KindclothingReturnItem> page, KindclothingReturnItem kindclothingReturnItem) {
		dataRuleFilter(kindclothingReturnItem);
		kindclothingReturnItem.setPage(page);
		page.setList(kindclothingReturnItemMapper.findList1(kindclothingReturnItem));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(KindclothingReturnItem kindclothingReturnItem) {
		super.save(kindclothingReturnItem);
	}
	
	@Transactional(readOnly = false)
	public void delete(KindclothingReturnItem kindclothingReturnItem) {
		super.delete(kindclothingReturnItem);
	}
	
}