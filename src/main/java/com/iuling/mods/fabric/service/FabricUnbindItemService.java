/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.fabric.entity.FabricUnbindItem;
import com.iuling.mods.fabric.mapper.FabricUnbindItemMapper;

/**
 * 面料解绑详情Service
 * @author 潘俞再
 * @version 2017-10-18
 */
@Service
@Transactional(readOnly = true)
public class FabricUnbindItemService extends CrudService<FabricUnbindItemMapper, FabricUnbindItem> {

	public FabricUnbindItem get(String id) {
		return super.get(id);
	}
	
	public List<FabricUnbindItem> findList(FabricUnbindItem fabricUnbindItem) {
		return super.findList(fabricUnbindItem);
	}
	
	public Page<FabricUnbindItem> findPage(Page<FabricUnbindItem> page, FabricUnbindItem fabricUnbindItem) {
		return super.findPage(page, fabricUnbindItem);
	}
	
	@Transactional(readOnly = false)
	public void save(FabricUnbindItem fabricUnbindItem) {
		super.save(fabricUnbindItem);
	}
	
	@Transactional(readOnly = false)
	public void delete(FabricUnbindItem fabricUnbindItem) {
		super.delete(fabricUnbindItem);
	}
	
}