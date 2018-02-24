package com.iuling.mods.base.service;
/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.base.entity.KindClothing;
import com.iuling.mods.base.mapper.KindClothingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 样衣基础信息管理Service
 * @author 宋小雄
 * @version 2017-09-19
 */
@Service
@Transactional(readOnly = true)
public class KindClothingService extends CrudService<KindClothingMapper, KindClothing> {

	@Autowired
	private KindClothingMapper kindClothingMapper;

	public KindClothing get(String id) {
		return super.get(id);
	}
	
	public List<KindClothing> findList(KindClothing kindClothing) {
		return super.findList(kindClothing);
	}
	
	public Page<KindClothing> findPage(Page<KindClothing> page, KindClothing kindClothing) {
		return super.findPage(page, kindClothing);
	}

	//查询所有样衣信息
	public List<KindClothing> findAllKindClothing() {
		return kindClothingMapper.findAllKindClothing();
	}

	@Transactional(readOnly = false)
	public void save(KindClothing kindClothing) {
		super.save(kindClothing);
	}
	
	@Transactional(readOnly = false)
	public void delete(KindClothing kindClothing) {
		super.delete(kindClothing);
	}
	
}