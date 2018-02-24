
package com.iuling.mods.fabric.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.fabric.entity.FabricShelvesItem;
import com.iuling.mods.fabric.mapper.FabricShelvesItemMapper;

/**
 * 面料上架详情Service
 * @author 潘俞再
 * @version 2017-09-20
 */
@Service
@Transactional(readOnly = true)
public class FabricShelvesItemService extends CrudService<FabricShelvesItemMapper, FabricShelvesItem> {

	public FabricShelvesItem get(String id) {
		return super.get(id);
	}
	
	public List<FabricShelvesItem> findList(FabricShelvesItem fabricShelvesItem) {
		return super.findList(fabricShelvesItem);
	}
	
	public Page<FabricShelvesItem> findPage(Page<FabricShelvesItem> page, FabricShelvesItem fabricShelvesItem) {
		return super.findPage(page, fabricShelvesItem);
	}
	
	@Transactional(readOnly = false)
	public void save(FabricShelvesItem fabricShelvesItem) {
		super.save(fabricShelvesItem);
	}
	
	@Transactional(readOnly = false)
	public void delete(FabricShelvesItem fabricShelvesItem) {
		super.delete(fabricShelvesItem);
	}
	
}