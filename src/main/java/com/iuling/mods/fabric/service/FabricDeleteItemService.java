/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.fabric.entity.FabricDeleteItem;
import com.iuling.mods.fabric.mapper.FabricDeleteItemMapper;

/**
 * 面料销毁详情Service
 * @author 潘俞再
 * @version 2017-10-18
 */
@Service
@Transactional(readOnly = true)
public class FabricDeleteItemService extends CrudService<FabricDeleteItemMapper, FabricDeleteItem> {

	public FabricDeleteItem get(String id) {
		return super.get(id);
	}
	
	public List<FabricDeleteItem> findList(FabricDeleteItem fabricDeleteItem) {
		return super.findList(fabricDeleteItem);
	}
	
	public Page<FabricDeleteItem> findPage(Page<FabricDeleteItem> page, FabricDeleteItem fabricDeleteItem) {
		return super.findPage(page, fabricDeleteItem);
	}
	
	@Transactional(readOnly = false)
	public void save(FabricDeleteItem fabricDeleteItem) {
		super.save(fabricDeleteItem);
	}
	
	@Transactional(readOnly = false)
	public void delete(FabricDeleteItem fabricDeleteItem) {
		super.delete(fabricDeleteItem);
	}
	
}