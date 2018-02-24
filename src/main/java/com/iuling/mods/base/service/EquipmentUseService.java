/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.service;

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.base.entity.EquipmentUse;
import com.iuling.mods.base.mapper.EquipmentUseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 设备使用记录Service
 * @author 宋小雄
 * @version 2017-09-19
 */
@Service
@Transactional(readOnly = true)
public class EquipmentUseService extends CrudService<EquipmentUseMapper, EquipmentUse> {

	public EquipmentUse get(String id) {
		return super.get(id);
	}
	
	public List<EquipmentUse> findList(EquipmentUse equipmentUse) {
		return super.findList(equipmentUse);
	}
	
	public Page<EquipmentUse> findPage(Page<EquipmentUse> page, EquipmentUse equipmentUse) {
		return super.findPage(page, equipmentUse);
	}
	
	@Transactional(readOnly = false)
	public void save(EquipmentUse equipmentUse) {
		super.save(equipmentUse);
	}
	
	@Transactional(readOnly = false)
	public void delete(EquipmentUse equipmentUse) {
		super.delete(equipmentUse);
	}
	
}