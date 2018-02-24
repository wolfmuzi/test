
package com.iuling.mods.fabric.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.fabric.entity.FabricBind;
import com.iuling.mods.fabric.mapper.FabricBindMapper;

/**
 * 面料色卡绑定记录Service
 * @author 潘俞再
 * @version 2017-09-21
 */
@Service
@Transactional(readOnly = true)
public class FabricBindService extends CrudService<FabricBindMapper, FabricBind> {

	public FabricBind get(String id) {
		return super.get(id);
	}
	
	public List<FabricBind> findList(FabricBind fabricBind) {
		return super.findList(fabricBind);
	}
	
	public Page<FabricBind> findPage(Page<FabricBind> page, FabricBind fabricBind) {
		return super.findPage(page, fabricBind);
	}
	
	@Transactional(readOnly = false)
	public void save(FabricBind fabricBind) {
		super.save(fabricBind);
	}
	
	@Transactional(readOnly = false)
	public void delete(FabricBind fabricBind) {
		super.delete(fabricBind);
	}
	
}