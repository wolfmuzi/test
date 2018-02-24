/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.service;

import java.util.List;

import com.iuling.mods.base.entity.Fabric;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.fabric.entity.FabricInventoryItme;
import com.iuling.mods.fabric.mapper.FabricInventoryItmeMapper;

/**
 * 色卡盘点详情Service
 * @author 潘俞再
 * @version 2017-09-22
 */
@Service
@Transactional(readOnly = true)
public class FabricInventoryItmeService extends CrudService<FabricInventoryItmeMapper, FabricInventoryItme> {

	public FabricInventoryItme get(String id) {
		return super.get(id);
	}
	
	public List<FabricInventoryItme> findList(FabricInventoryItme fabricInventoryItme) {
		return super.findList(fabricInventoryItme);
	}

	public Page<FabricInventoryItme> findPage(Page<FabricInventoryItme> page, FabricInventoryItme fabricInventoryItme) {
		return super.findPage(page, fabricInventoryItme);
	}
	public Page<Fabric> findPage(Page<Fabric> page, Fabric fabricInventoryItme) {
		dataRuleFilter(fabricInventoryItme);
		fabricInventoryItme.setPage(page);
		page.setList(mapper.findByParam1(fabricInventoryItme));
		return page;
	}


	@Transactional(readOnly = false)
	public void save(FabricInventoryItme fabricInventoryItme) {
		super.save(fabricInventoryItme);
	}
	
	@Transactional(readOnly = false)
	public void delete(FabricInventoryItme fabricInventoryItme) {
		super.delete(fabricInventoryItme);
	}

	public List<Fabric> findByParam(FabricInventoryItme fabricInventoryItme) {

		return mapper.findByParam(fabricInventoryItme);

	}

	public List<Fabric> findByParam1(FabricInventoryItme fabricInventoryItme) {

		return mapper.findByParam(fabricInventoryItme);

	}
}