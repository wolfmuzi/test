/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.service;

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.base.entity.Equipment;
import com.iuling.mods.base.mapper.EquipmentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 设备管理Service
 * @author 宋小雄
 * @version 2017-09-19
 */
@Service
@Transactional(readOnly = true)
public class EquipmentService extends CrudService<EquipmentMapper, Equipment> {

	public Equipment get(String id) {
		return super.get(id);
	}
	
	public List<Equipment> findList(Equipment equipment) {
		return super.findList(equipment);
	}
	
	public Page<Equipment> findPage(Page<Equipment> page, Equipment equipment) {
		return super.findPage(page, equipment);
	}
	
	@Transactional(readOnly = false)
	public void save(Equipment equipment) {
		super.save(equipment);
	}
	
	@Transactional(readOnly = false)
	public void delete(Equipment equipment) {
		super.delete(equipment);
	}
	
}