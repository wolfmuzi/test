/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Iuling</a> All rights reserved.
 */
package com.iuling.mods.echarts.service.other;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.echarts.entity.other.TestPieClass;
import com.iuling.mods.echarts.mapper.other.TestPieClassMapper;

/**
 * 学生Service
 * @author Sincere
 * @version 2017-06-04
 */
@Service
@Transactional(readOnly = true)
public class TestPieClassService extends CrudService<TestPieClassMapper, TestPieClass> {

	public TestPieClass get(String id) {
		return super.get(id);
	}
	
	public List<TestPieClass> findList(TestPieClass testPieClass) {
		return super.findList(testPieClass);
	}
	
	public Page<TestPieClass> findPage(Page<TestPieClass> page, TestPieClass testPieClass) {
		return super.findPage(page, testPieClass);
	}
	
	@Transactional(readOnly = false)
	public void save(TestPieClass testPieClass) {
		super.save(testPieClass);
	}
	
	@Transactional(readOnly = false)
	public void delete(TestPieClass testPieClass) {
		super.delete(testPieClass);
	}
	
}