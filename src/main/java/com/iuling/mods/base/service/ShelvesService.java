
package com.iuling.mods.base.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.base.entity.Shelves;
import com.iuling.mods.base.mapper.ShelvesMapper;

/**
 * 货架管理Service
 * @author 潘俞再
 * @version 2017-09-21
 */
@Service
@Transactional(readOnly = true)
public class ShelvesService extends CrudService<ShelvesMapper, Shelves> {

	public Shelves get(String id) {
		return super.get(id);
	}
	
	public List<Shelves> findList(Shelves shelves) {
		return super.findList(shelves);
	}
	
	public Page<Shelves> findPage(Page<Shelves> page, Shelves shelves) {
		return super.findPage(page, shelves);
	}
	
	@Transactional(readOnly = false)
	public void save(Shelves shelves) {
		super.save(shelves);
	}
	
	@Transactional(readOnly = false)
	public void delete(Shelves shelves) {
		super.delete(shelves);
	}
	
}