/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.service;

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.base.entity.BaseCommonRole;
import com.iuling.mods.base.mapper.BaseCommonRoleMapper;
import com.iuling.mods.clothingmanageformj.param.KindclothingLendParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 设计师质检员管理Service
 * @author 彭成
 * @version 2017-09-22
 */
@Service
@Transactional(readOnly = true)
public class BaseCommonRoleService extends CrudService<BaseCommonRoleMapper, BaseCommonRole> {

	@Autowired
	private BaseCommonRoleMapper baseCommonRoleMapper;

	public BaseCommonRole get(String id) {
		return super.get(id);
	}
	
	public List<BaseCommonRole> findList(BaseCommonRole baseCommonRole) {
		return super.findList(baseCommonRole);
	}
	
	public Page<BaseCommonRole> findPage(Page<BaseCommonRole> page, BaseCommonRole baseCommonRole) {
		return super.findPage(page, baseCommonRole);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseCommonRole baseCommonRole) {
		super.save(baseCommonRole);
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseCommonRole baseCommonRole) {
		super.delete(baseCommonRole);
	}

	//根据设计师、质检员查询人员
	public List<BaseCommonRole> findBaseCommonRoleList(BaseCommonRole entity) {
		return baseCommonRoleMapper.findBaseCommonRoleList(entity);
	}

	//根据姓名获得信息
	public BaseCommonRole selectByName(KindclothingLendParam param){
		return baseCommonRoleMapper.selectByName(param);
	}
}