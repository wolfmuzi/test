/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.service;

import com.iuling.comm.config.OrderCodeKeys;
import com.iuling.comm.json.AjaxJsonResponse;
import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.base.entity.KindClothing;
import com.iuling.mods.base.entity.LabelInfo;
import com.iuling.mods.base.entity.Shelves;
import com.iuling.mods.base.service.LabelInfoService;
import com.iuling.mods.base.service.OrderCodeService;
import com.iuling.mods.clothingmanageformj.entity.KindclothingShelves;
import com.iuling.mods.clothingmanageformj.entity.KindclothingShelvesItem;
import com.iuling.mods.clothingmanageformj.mapper.KindclothingShelvesMapper;
import com.iuling.mods.clothingmanageformj.param.KindclothingShelvesParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 样衣上架列表Service
 * @author 彭成
 * @version 2017-09-20
 */
@Service
@Transactional(readOnly = true)
public class KindclothingShelvesService extends CrudService<KindclothingShelvesMapper, KindclothingShelves> {

	@Autowired
	private KindclothingShelvesMapper kindclothingShelvesMapper;

	@Autowired
	private KindclothingShelvesItemService kindclothingShelvesItemService;

	@Autowired
	private KindclothingStockService kindclothingStockService;

	@Autowired
	private OrderCodeService orderCodeService;

	@Autowired
	private LabelInfoService labelInfoService;

	public KindclothingShelves get(String id) {
		return super.get(id);
	}
	
	public List<KindclothingShelves> findList(KindclothingShelves kindclothingShelves) {
		return super.findList(kindclothingShelves);
	}
	
	public Page<KindclothingShelves> findPage(Page<KindclothingShelves> page, KindclothingShelves kindclothingShelves) {
		return super.findPage(page, kindclothingShelves);
	}
	
	@Transactional(readOnly = false)
	public void save(KindclothingShelves kindclothingShelves) {
		super.save(kindclothingShelves);
	}
	
	@Transactional(readOnly = false)
	public void delete(KindclothingShelves kindclothingShelves) {
		super.delete(kindclothingShelves);
	}

	//查询某个货架的上架数量
	public int selectNumById(String id){
		Integer num = kindclothingShelvesMapper.selectNumById(id);
		int i = num==null? 0:num.intValue();
		return i;
	}

	//根据绑定、上架状态获取样衣信息
	public List<KindClothing> findListByParam (KindclothingShelvesParam param){
		return kindclothingShelvesMapper.findListByParam(param);
	}

	//样衣上架
	@Transactional(readOnly = false)
	public AjaxJsonResponse shelves(KindclothingShelvesParam param){
		param.setBindStatus(2);
		param.setShelvesStatus(1);
		List<KindClothing> list= this.findListByParam(param);
		if(list == null || list.size() == 0 ){
			return new AjaxJsonResponse(false,"4","查询不到样衣信息",null);
		}
		Shelves shelves = new Shelves();  //货架
		shelves.setId(param.getShelvesId());

		//生成单号
		KindclothingShelves kindclothingShelves = new KindclothingShelves(); //样衣上架Entity
		kindclothingShelves.setCode(orderCodeService.getOrderCode(OrderCodeKeys.KSJ_SHELVES_KEY));
		kindclothingShelves.setNum(list.size());
		kindclothingShelves.setShelves(shelves);  //关联货架
		kindclothingShelves.setUser(kindclothingShelves.getCurrentUser());
		kindclothingShelves.setShelvesDate(new Date());
		this.save(kindclothingShelves);

		//记录详情
		for(KindClothing kindClothing:list){
			KindclothingShelvesItem shelvesItem = new KindclothingShelvesItem();
			shelvesItem.setBarCode(kindClothing.getNumber());  //样衣条码
			shelvesItem.setEpc(kindClothing.getEpc());
			shelvesItem.setName(kindClothing.getName());
			shelvesItem.setSupplier(kindClothing.getSupplier());
			shelvesItem.setStyle(kindClothing.getStyle());
			shelvesItem.setEditionnumber(kindClothing.getEditionNumber());
			shelvesItem.setKindclothingShelves(kindclothingShelves);  //关联样衣上架Entity
			kindclothingShelvesItemService.save(shelvesItem);
			//更新库存状态
			kindclothingStockService.executeUpdateSql("update kindclothing_stock set shelves_status = '2',shelves_id = '"+param.getShelvesId()+"' where bar_code = '"+kindClothing.getNumber()+"'");
			//更新上架时间
			SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			kindclothingStockService.executeUpdateSql("update base_kindclothing set shelves_date =  '"+ s.format(kindclothingShelves.getShelvesDate())+"' where number = '"+kindClothing.getNumber()+"'");

			/**
			 * 添加或者更新标签状态
			 */
			LabelInfo labelInfo = new LabelInfo();
			labelInfo.setNumber(kindClothing.getEpc());
			labelInfo.setStatus("2");
			labelInfo.setType("1");
			labelInfoService.saveOrUpdate(labelInfo);
		}
		return new AjaxJsonResponse(true,"-1","上架成功 ",null);
	}
}