
package com.iuling.mods.base.service;

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.base.entity.Fabric;
import com.iuling.mods.base.mapper.FabricMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 面料色卡管理Service
 * @author 宋小雄
 * @version 2017-09-21
 */
@Service
@Transactional(readOnly = true)
public class FabricService extends CrudService<FabricMapper, Fabric> {

	@Autowired
	private FabricMapper fabricMapper;

	public List<Fabric> findAllFabricList(Fabric entity){
		return fabricMapper.findAllList(entity);
	}

	public Fabric get(String id) {
		return super.get(id);
	}

	public List<Fabric> findList(Fabric fabric) {
		return super.findList(fabric);
	}

	public List<Fabric> findListJoin(Fabric fabric) {
		return mapper.findListJoin(fabric);
	}
	
	public Page<Fabric> findPage(Page<Fabric> page, Fabric fabric) {
		return super.findPage(page, fabric);
	}
	
	@Transactional(readOnly = false)
	public void save(Fabric fabric) {
		super.save(fabric);
	}
	
	@Transactional(readOnly = false)
	public void delete(Fabric fabric) {
		super.delete(fabric);
	}
	
}