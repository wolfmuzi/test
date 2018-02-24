/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.service;

import java.util.*;

import com.iuling.comm.config.OrderCodeKeys;
import com.iuling.comm.json.AjaxJsonResponse;
import com.iuling.mods.base.entity.Fabric;
import com.iuling.mods.base.service.FabricService;
import com.iuling.mods.base.service.OrderCodeService;
import com.iuling.mods.fabric.entity.FabricInventoryItme;
import com.iuling.mods.fabric.entity.FabricStock;
import com.iuling.mods.fabric.param.FabricInventoryParam;
import com.iuling.mods.fabric.param.FabricShelvesParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.fabric.entity.FabricInventory;
import com.iuling.mods.fabric.mapper.FabricInventoryMapper;

/**
 * 色卡盘点Service
 * @author 潘俞再
 * @version 2017-09-22
 */
@Service
@Transactional(readOnly = true)
public class FabricInventoryService extends CrudService<FabricInventoryMapper, FabricInventory> {

	@Autowired
	private FabricStockService fabricStockService;

	@Autowired
	private FabricInventoryItmeService fabricInventoryItmeServicen;

	@Autowired
	private FabricService fabricService;

	@Autowired
	private OrderCodeService orderCodeService;

	public FabricInventory get(String id) {
		return super.get(id);
	}
	
	public List<FabricInventory> findList(FabricInventory fabricInventory) {
		return super.findList(fabricInventory);
	}
	
	public Page<FabricInventory> findPage(Page<FabricInventory> page, FabricInventory fabricInventory) {
		return super.findPage(page, fabricInventory);
	}
	
	@Transactional(readOnly = false)
	public void save(FabricInventory fabricInventory) {
		super.save(fabricInventory);
	}
	
	@Transactional(readOnly = false)
	public void delete(FabricInventory fabricInventory) {
		super.delete(fabricInventory);
	}

	/**
	 * 盘点
	 * @param fabricInventoryParam
	 * @return
	 */
	@Transactional(readOnly = false)
    public AjaxJsonResponse inventory(FabricInventoryParam fabricInventoryParam) {

		//查询库存 已上架的
		FabricStock stockParam = new FabricStock();
		stockParam.setShelvesStatus(2);
		List<FabricStock> stockList = fabricStockService.findList(stockParam);
		int stockNum = stockList.size();
		//查询实盘
		List<FabricStock> invenoryList = fabricStockService.findListByParam1(fabricInventoryParam);
		int inventoryNum = invenoryList.size();
		int yNum = 0;
		int kNum = 0;
		List<FabricInventoryItme> yList = new ArrayList<>();
		List<FabricInventoryItme> kList = new ArrayList<>();
		List<FabricStock> temp = new ArrayList<>();//临时存
		for(FabricStock t :invenoryList){ temp.add(t);}

		invenoryList.removeAll(stockList);
		yNum=invenoryList.size();

		stockList.removeAll(temp);
		kNum=stockList.size();
		//保存盘点信息
		FabricInventory fabricInventory = new FabricInventory();
		fabricInventory.setCode(orderCodeService.getOrderCode(OrderCodeKeys.F_INVENTORY_KEY));
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
	private List<FabricInventoryItme> getItem(List<FabricStock> list,FabricInventory id,int type) {
		List<FabricInventoryItme> result = new ArrayList<FabricInventoryItme>();
		for(FabricStock stock :list){
			FabricInventoryItme item = new FabricInventoryItme();
			Fabric f = new Fabric();
			f.setBarcode(stock.getBarCode());
			List<Fabric> fs = fabricService.findList(f);
			Fabric fabricId = (fs.size()>0)?fs.get(0):null;
			item.setFabric(fabricId);
			item.setShelves(stock.getShelves());
			item.setFabricInventory(id);
			item.setType(type);
			fabricInventoryItmeServicen.save(item);
			result.add(item);
		}

		return result;
	}


	public static void main(String[] a){
		List<FabricStock> stockList = new ArrayList();
		stockList.add(new FabricStock("","a"));
		stockList.add(new FabricStock("","b"));
		stockList.add(new FabricStock("","c"));


		List<FabricStock> invenoryList = new ArrayList();
		stockList.add(new FabricStock("","d"));

		//invenoryList.add(new FabricStock("",""));

		invenoryList.removeAll(stockList);
		for(FabricStock s:stockList){
			System.out.print(s.getEpc());
		}
	}


    public FabricInventory getByCode(FabricInventory fabricInventory) {
		return mapper.getByCode(fabricInventory);
    }
}