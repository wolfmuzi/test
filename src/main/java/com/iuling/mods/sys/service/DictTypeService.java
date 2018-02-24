/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Iuling</a> All rights reserved.
 */
package com.iuling.mods.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iuling.comm.utils.CacheUtils;
import com.iuling.comm.utils.StringUtils;
import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.sys.entity.DictType;
import com.iuling.mods.sys.entity.DictValue;
import com.iuling.mods.sys.mapper.DictTypeMapper;
import com.iuling.mods.sys.mapper.DictValueMapper;
import com.iuling.mods.sys.utils.DictUtils;

/**
 * 数据字典Service
 * @author Sincere
 * @version 2017-01-16
 */
@Service
@Transactional(readOnly = true)
public class DictTypeService extends CrudService<DictTypeMapper, DictType> {

	@Autowired
	private DictValueMapper dictValueMapper;
	
	public DictType get(String id) {
		DictType dictType = super.get(id);
		dictType.setDictValueList(dictValueMapper.findList(new DictValue(dictType)));
		return dictType;
	}
	
	public DictValue getDictValue(String id) {
		return dictValueMapper.get(id);
	}
	
	public List<DictType> findList(DictType dictType) {
		List<DictType> dictTypeList = super.findList(dictType);
		for(DictType temp:dictTypeList){
			temp.setDictValueList(dictValueMapper.findList(new DictValue(temp)));
		}

		return dictTypeList;
	}
	
	public Page<DictType> findPage(Page<DictType> page, DictType dictType) {
		return super.findPage(page, dictType);
	}
	
	@Transactional(readOnly = false)
	public void save(DictType dictType) {
		super.save(dictType);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}
	
	@Transactional(readOnly = false)
	public void saveDictValue(DictValue dictValue) {
		if (StringUtils.isBlank(dictValue.getId())){
			dictValue.preInsert();
			dictValueMapper.insert(dictValue);
		}else{
			dictValue.preUpdate();
			dictValueMapper.update(dictValue);
		}
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}
	
	@Transactional(readOnly = false)
	public void deleteDictValue(DictValue dictValue) {
		dictValueMapper.delete(dictValue);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}
	
	@Transactional(readOnly = false)
	public void delete(DictType dictType) {
		super.delete(dictType);
		dictValueMapper.delete(new DictValue(dictType));
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}
	
}