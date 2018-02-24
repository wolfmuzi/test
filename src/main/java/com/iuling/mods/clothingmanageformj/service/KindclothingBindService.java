/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.clothingmanageformj.entity.KindclothingBind;
import com.iuling.mods.clothingmanageformj.mapper.KindclothingBindMapper;

/**
 * 样衣绑定信息Service
 * @author 彭成
 * @version 2017-09-20
 */
@Service
@Transactional(readOnly = true)
public class KindclothingBindService extends CrudService<KindclothingBindMapper, KindclothingBind> {

	public KindclothingBind get(String id) {
		return super.get(id);
	}
	
	public List<KindclothingBind> findList(KindclothingBind kindclothingBind) {
		return super.findList(kindclothingBind);
	}
	
	public Page<KindclothingBind> findPage(Page<KindclothingBind> page, KindclothingBind kindclothingBind) {
		return super.findPage(page, kindclothingBind);
	}
	
	@Transactional(readOnly = false)
	public void save(KindclothingBind kindclothingBind) {
		super.save(kindclothingBind);
	}
	
	@Transactional(readOnly = false)
	public void delete(KindclothingBind kindclothingBind) {
		super.delete(kindclothingBind);
	}
	
}