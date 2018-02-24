/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.service;

import com.iuling.comm.utils.StringUtils;
import com.iuling.core.service.TreeService;
import com.iuling.mods.base.entity.Warehouse;
import com.iuling.mods.base.mapper.WarehouseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 仓库管理Service
 * @author 宋小雄
 * @version 2017-09-19
 */
@Service
@Transactional(readOnly = true)
public class WarehouseService extends TreeService<WarehouseMapper, Warehouse> {

	@Autowired
	private WarehouseMapper warehouseMapper;

	public Warehouse get(String id) {
		return super.get(id);
	}

	//由名称获得仓库信息
	public Warehouse getByName(String name) {
		return warehouseMapper.getByName(name);
	}

	public List<Warehouse> findList(Warehouse warehouse) {
		if (StringUtils.isNotBlank(warehouse.getParentIds())){
			warehouse.setParentIds(","+warehouse.getParentIds()+",");
		}
		return super.findList(warehouse);
	}
	
	@Transactional(readOnly = false)
	public void save(Warehouse warehouse) {
		super.save(warehouse);
	}
	
	@Transactional(readOnly = false)
	public void delete(Warehouse warehouse) {
		super.delete(warehouse);
	}
	
}