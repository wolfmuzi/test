/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.service;

import com.iuling.comm.json.AjaxJsonResponse;
import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.base.entity.LabelInfo;
import com.iuling.mods.base.service.LabelInfoService;
import com.iuling.mods.clothingmanageformj.entity.KindclothingBind;
import com.iuling.mods.clothingmanageformj.entity.KindclothingStock;
import com.iuling.mods.clothingmanageformj.mapper.KindclothingShelvesMapper;
import com.iuling.mods.clothingmanageformj.mapper.KindclothingStockMapper;
import com.iuling.mods.clothingmanageformj.param.KindclothingBindParam;
import com.iuling.mods.fabric.entity.FabricStock;
import com.iuling.mods.fabric.param.FabricBindParam;
import com.iuling.mods.fabric.param.FabricInventoryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 样衣库存Service
 * @author 彭成
 * @version 2017-09-22
 */
@Service
@Transactional(readOnly = true)
public class KindclothingStockService extends CrudService<KindclothingStockMapper, KindclothingStock> {

	@Autowired
	private KindclothingBindService kindclothingBindService;
	@Autowired
	private KindclothingShelvesMapper kindclothingShelvesMapper;
	@Autowired
	private KindclothingStockMapper kindclothingStockMapper;
	@Autowired
	private LabelInfoService labelInfoService;

	public KindclothingStock get(String id) {
		return super.get(id);
	}
	
	public List<KindclothingStock> findList(KindclothingStock kindclothingStock) {
		return super.findList(kindclothingStock);
	}

	//查询所有样衣库存信息
	public List<KindclothingStock> findAllKindClothingStock() {
		return kindclothingStockMapper.findAllKindClothingStock();
	}

	public Page<KindclothingStock> findPage(Page<KindclothingStock> page, KindclothingStock kindclothingStock) {
		return super.findPage(page, kindclothingStock);
	}
	
	@Transactional(readOnly = false)
	public void save(KindclothingStock kindclothingStock) {
		super.save(kindclothingStock);
	}
	
	@Transactional(readOnly = false)
	public void delete(KindclothingStock kindclothingStock) {
		super.delete(kindclothingStock);
	}

	/**
	 * 根据样衣条码获取样衣信息
	 */
	public KindclothingStock findKindclothingByBarCode(KindclothingBindParam param){
		return kindclothingStockMapper.findKindclothingByBarCode(param);
	}


	/**
	 * 根据epc获取样衣信息
	 */
	public KindclothingStock findKindclothingByEpc(KindclothingBindParam param){
		return kindclothingStockMapper.findKindclothingByEpc(param);
	}

	/**
	 * epc查找
	 * @param param
	 * @return
	 */
	public AjaxJsonResponse checkKindclothing(KindclothingBindParam param){
		KindclothingStock stock = kindclothingStockMapper.checkKindclothing(param);
		if(stock == null){
			return new AjaxJsonResponse(false,"1","查询不到样衣信息",stock);
		}
		if(stock.getShelves() != null){
			//查询这个货架的上架数量
			Integer sumNum = kindclothingShelvesMapper.selectNumById(stock.getShelves().getId());
			if(sumNum != null){
				stock.getShelves().setSumNum(sumNum);
			}
			if("".equals(stock.getShelves().getId())) {
				stock.setShelves(null);
			}
		}
		return new AjaxJsonResponse(true,"-1","查找成功",stock);
	}

	/**
	 * 样衣绑定
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = false)
	public AjaxJsonResponse bind(KindclothingBindParam param){

		/**
		 * 判断条码是否已经绑定
		 */
		KindclothingStock stock = new KindclothingStock();
		stock.setBarCode(param.getBarCode());
		List<KindclothingStock> stockLIstanbul= this.findList(stock);
		if(stockLIstanbul == null || stockLIstanbul.size()== 0){
			return new AjaxJsonResponse(false,"4","找不到条码信息",null);
		}

		KindclothingStock kindclothingStock = stockLIstanbul.get(0);
		if(kindclothingStock.getBindStatus() == 2){
			return new AjaxJsonResponse(false,"1","条码已绑定，请扫描新的条码",null);
		}
		/**
		 * 判断标签是否已经绑定
		 */
		KindclothingStock stock2 = new KindclothingStock();
		stock2.setEpc(param.getEpc());
		List<KindclothingStock> stockList = this.findList(stock2);
		if(stockList != null && stockList.size()> 0){
			return new AjaxJsonResponse(false,"2","样衣标签已绑定",null);
		}
		/**
		 * 更新库存记录
		 */
		kindclothingStock.setBindStatus(2);
		kindclothingStock.setEpc(param.getEpc());
		this.save(kindclothingStock);
		/**
		 * 记录绑定信息
		 */
		KindclothingBind kindclothingBind = new KindclothingBind();
		kindclothingBind.setBarCode(param.getBarCode());
		kindclothingBind.setKindClothing(kindclothingStock.getKindClothing());
		kindclothingBind.setEpc(param.getEpc());
		kindclothingBind.setName(kindclothingStock.getName());
		kindclothingBind.setSupplier(kindclothingStock.getSupplier());
		kindclothingBind.setBindUser(kindclothingBind.getCurrentUser());
		kindclothingBind.setBindDate(new Date());
		kindclothingBindService.save(kindclothingBind);

		/**
		 * 添加或者更新标签状态
		 */
		LabelInfo labelInfo = new LabelInfo();
		labelInfo.setNumber(param.getEpc());
		labelInfo.setStatus("1");
		labelInfo.setType("1");
		labelInfoService.saveOrUpdate(labelInfo);

		return new AjaxJsonResponse(true,"-1","绑定成功",null);
	}

    public List<KindclothingStock> findListByParam1(FabricInventoryParam fabricInventoryParam) {
		return mapper.findListByParam1(fabricInventoryParam);
    }

    public List<KindclothingStock> findListSubmit(KindclothingStock kindclothingStock) {
		return mapper.findListSubmit(kindclothingStock);
    }


	public AjaxJsonResponse byEpcListData(FabricBindParam fabricBindParam) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<FabricStock> list = mapper.byEpcListData(fabricBindParam);
		map.put("list",list);
		return new AjaxJsonResponse(true,"-1","查找成功",map);
	}
}