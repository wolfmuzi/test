/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.service;

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.clothingmanageformj.entity.KindclothingLendItem;
import com.iuling.mods.clothingmanageformj.entity.KindclothingStock;
import com.iuling.mods.clothingmanageformj.mapper.KindclothingLendItemMapper;
import com.iuling.mods.clothingmanageformj.mapper.KindclothingStockMapper;
import com.iuling.mods.clothingmanageformj.param.KindclothingBindParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 样衣借用详情Service
 * @author 彭成
 * @version 2017-09-23
 */
@Service
@Transactional(readOnly = true)
public class KindclothingLendItemService extends CrudService<KindclothingLendItemMapper, KindclothingLendItem> {

	@Autowired
	private KindclothingLendItemMapper kindclothingLendItemMapper;
	@Autowired
	private KindclothingStockMapper kindclothingStockMapper;

	public KindclothingLendItem get(String id) {
		return super.get(id);
	}
	
	public List<KindclothingLendItem> findList(KindclothingLendItem kindclothingLendItem) {
		return super.findList(kindclothingLendItem);
	}
	
	public Page<KindclothingLendItem> findPage(Page<KindclothingLendItem> page, KindclothingLendItem kindclothingLendItem) {
		dataRuleFilter(kindclothingLendItem);
		kindclothingLendItem.setPage(page);
		List<KindclothingLendItem> list = kindclothingLendItemMapper.findByParam1(kindclothingLendItem);
		KindclothingBindParam param = new KindclothingBindParam();
		KindclothingStock stock = null;
		for(KindclothingLendItem item :list){
			param.setEpc(item.getBarCode());
			stock = kindclothingStockMapper.findKindclothingByEpc(param);
			item.setBarCode1(stock.getBarCode());
		}
		page.setList(list);
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(KindclothingLendItem kindclothingLendItem) {
		super.save(kindclothingLendItem);
	}
	
	@Transactional(readOnly = false)
	public void delete(KindclothingLendItem kindclothingLendItem) {
		super.delete(kindclothingLendItem);
	}

	//统计当前用户的借用总数
	public int sumByBaseCommonRoleId(KindclothingLendItem kindclothingLendItem) {

		Integer num = kindclothingLendItemMapper.sumByBaseCommonRoleId(kindclothingLendItem);
		int number= num==null? 0 : num.intValue();
		return number;
	}

	//根据借用单号查找未归还样衣数量
	Integer findByLendNo(KindclothingLendItem entity) {
		Integer count = kindclothingLendItemMapper.findByLendNo(entity);
		int num = count==null? 0 : count.intValue();
		return num;
	}

	//根据借用单号获得借用详情总数量
	Integer getTotalByLendNo(KindclothingLendItem entity) {
		Integer count = kindclothingLendItemMapper.getTotalByLendNo(entity);
		int num = count==null? 0 : count.intValue();
		return num;
	}

    public List<KindclothingLendItem> findByParam(KindclothingLendItem kindclothingLendItem) {
		return mapper.findByParam(kindclothingLendItem);
    }
}