/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.service;

import com.iuling.comm.config.OrderCodeKeys;
import com.iuling.comm.json.AjaxJsonResponse;
import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.base.entity.KindClothing;
import com.iuling.mods.base.service.KindClothingService;
import com.iuling.mods.base.service.OrderCodeService;
import com.iuling.mods.fabric.param.FabricInventoryParam;
import com.iuling.mods.clothingmanageformj.entity.KindclothingInventory;
import com.iuling.mods.clothingmanageformj.entity.KindclothingInventoryItem;
import com.iuling.mods.clothingmanageformj.entity.KindclothingStock;
import com.iuling.mods.clothingmanageformj.mapper.KindclothingInventoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 样衣盘点Service
 * @author 彭成
 * @version 2017-09-26
 */
@Service
@Transactional(readOnly = true)
public class KindclothingInventoryService extends CrudService<KindclothingInventoryMapper, KindclothingInventory> {

	@Autowired
	private KindclothingStockService KindclothingStockService;

	@Autowired
	private KindclothingInventoryItemService kindclothingInventoryItemService;


	@Autowired
	private KindClothingService kindClothingService;

	@Autowired
	private OrderCodeService orderCodeService;

	public KindclothingInventory get(String id) {
		return super.get(id);
	}

	public List<KindclothingInventory> findList(KindclothingInventory kindclothingInventory) {
		return super.findList(kindclothingInventory);
	}

	public Page<KindclothingInventory> findPage(Page<KindclothingInventory> page, KindclothingInventory kindclothingInventory) {
		return super.findPage(page, kindclothingInventory);
	}

	@Transactional(readOnly = false)
	public void save(KindclothingInventory kindclothingInventory) {
		super.save(kindclothingInventory);
	}

	@Transactional(readOnly = false)
	public void delete(KindclothingInventory kindclothingInventory) {
		super.delete(kindclothingInventory);
	}



	/**
	 * 盘点
	 * @param fabricInventoryParam
	 * @return
	 */
	@Transactional(readOnly = false)
	public AjaxJsonResponse inventory(FabricInventoryParam fabricInventoryParam) {

		//查询库存 已上架的
		KindclothingStock stockParam = new KindclothingStock();
		stockParam.setShelvesStatus(2);
		List<KindclothingStock> stockList = KindclothingStockService.findList(stockParam);
		int stockNum = stockList.size();
		//查询实盘
		List<KindclothingStock> invenoryList = KindclothingStockService.findListByParam1(fabricInventoryParam);
		int inventoryNum = invenoryList.size();
		int yNum = 0;
		int kNum = 0;
		List<KindclothingInventoryItem> yList = new ArrayList<>();
		List<KindclothingInventoryItem> kList = new ArrayList<>();
		List<KindclothingStock> temp = new ArrayList<>();//临时存
		for(KindclothingStock t :invenoryList){ temp.add(t);}

		invenoryList.removeAll(stockList);
		yNum=invenoryList.size();

		stockList.removeAll(temp);
		kNum=stockList.size();
		//保存盘点信息
		KindclothingInventory fabricInventory = new KindclothingInventory();
		fabricInventory.setCode(orderCodeService.getOrderCode(OrderCodeKeys.Y_INVENTORY_KEY));
		fabricInventory.setInventoryNum(inventoryNum);
		fabricInventory.setStatus(1);
		fabricInventory.setStockNum(stockNum);
		fabricInventory.setYNum(yNum);
		fabricInventory.setKNum(kNum);
		fabricInventory.setInventoryDate(new Date());
		fabricInventory.setDelFlag("1");
		fabricInventory.setInventoryUser(fabricInventory.getCurrentUser());
		this.save(fabricInventory);
		//保存并获取详情信息
		yList = getItem(invenoryList,fabricInventory,2);
		kList = getItem(stockList,fabricInventory,1);
		fabricInventory.setyList(yList);
		fabricInventory.setkList(kList);
		return new AjaxJsonResponse(true,"-1","操作成功",fabricInventory);
	}

	/**
	 * 生成盘点详情
	 * @param
	 * @return
	 */
	private List<KindclothingInventoryItem> getItem(List<KindclothingStock> list,KindclothingInventory id,int type) {
		List<KindclothingInventoryItem> result = new ArrayList<KindclothingInventoryItem>();
		for(KindclothingStock stock :list){
			KindclothingInventoryItem item = new KindclothingInventoryItem();
			KindClothing f = new KindClothing();
			f.setNumber(stock.getBarCode());
			List<KindClothing> fs = kindClothingService.findList(f);
			KindClothing fabricId = (fs.size()>0)?fs.get(0):null;
			item.setKindclothingInventory(id);
			item.setKindclothing(fabricId);
			item.setType(type);
			item.setShelves(stock.getShelves());
			kindclothingInventoryItemService.save(item);
			result.add(item);
		}

		return result;
	}


	//根据盘点单号获得信息
	public KindclothingInventory getByCode(KindclothingInventory entity) {
		return mapper.getByCode(entity);
	}
}