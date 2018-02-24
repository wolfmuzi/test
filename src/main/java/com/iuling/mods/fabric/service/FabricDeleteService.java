/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.service;

import java.util.Date;
import java.util.List;

import com.iuling.comm.config.OrderCodeKeys;
import com.iuling.comm.json.AjaxJsonResponse;
import com.iuling.mods.base.entity.LabelInfo;
import com.iuling.mods.base.param.ShelvesParam;
import com.iuling.mods.base.service.FabricService;
import com.iuling.mods.base.service.LabelInfoService;
import com.iuling.mods.base.service.OrderCodeService;
import com.iuling.mods.fabric.entity.FabricDeleteItem;
import com.iuling.mods.fabric.entity.FabricStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.fabric.entity.FabricDelete;
import com.iuling.mods.fabric.mapper.FabricDeleteMapper;

/**
 * 面料销毁记录Service
 * @author 潘俞再
 * @version 2017-10-18
 */
@Service
@Transactional(readOnly = true)
public class FabricDeleteService extends CrudService<FabricDeleteMapper, FabricDelete> {


	@Autowired
	private FabricStockService fabricStockService;

	@Autowired
	private OrderCodeService orderCodeService;
	@Autowired
	private LabelInfoService labelInfoService;
	@Autowired
	private FabricService fabricService;
	@Autowired
	private FabricDeleteItemService fabricDeleteItemService;


	public FabricDelete get(String id) {
		return super.get(id);
	}
	
	public List<FabricDelete> findList(FabricDelete fabricDelete) {
		return super.findList(fabricDelete);
	}
	
	public Page<FabricDelete> findPage(Page<FabricDelete> page, FabricDelete fabricDelete) {
		return super.findPage(page, fabricDelete);
	}
	
	@Transactional(readOnly = false)
	public void save(FabricDelete fabricDelete) {
		super.save(fabricDelete);
	}
	
	@Transactional(readOnly = false)
	public void delete(FabricDelete fabricDelete) {
		super.delete(fabricDelete);
	}






	/**
	 * 根据epc销毁色卡
	 * @param shelvesParam
	 * @return
	 */
	@Transactional(readOnly = false)
	public AjaxJsonResponse deleteFabric(ShelvesParam shelvesParam){
		List<String> epcList = shelvesParam.getEpcList();
		//记录销毁信息
		FabricDelete fabricDelete = new FabricDelete();
		fabricDelete.setCode(orderCodeService.getOrderCode(OrderCodeKeys.F_DELETE_KEY));
		fabricDelete.setNum(epcList.size());
		fabricDelete.setDeleteUser(fabricDelete.getCurrentUser());
		fabricDelete.setDeleteDate(new Date());
		this.save(fabricDelete);
		//记录详情
		for(String epc:epcList){

			FabricStock stock = new FabricStock();
			stock.setEpc(epc);
			List<FabricStock> stockList = fabricStockService.findList(stock);
			FabricStock fabricStock = (stockList.size() > 0)?stockList.get(0):null;
			FabricDeleteItem item = new FabricDeleteItem();
			if(fabricStock == null){
				item.setEpc(epc);
			}else{
				item.setBarCode(fabricStock.getBarCode());
				item.setEpc(fabricStock.getEpc());
				item.setFabricDelete(fabricDelete);
				item.setName(fabricStock.getName());
				item.setSupplier(fabricStock.getSupplier());
			}



			fabricDeleteItemService.save(item);

			if(fabricStock != null) {
				/**
				 * 添加或者更新标签状态
				 */
				LabelInfo labelInfo = new LabelInfo();
				labelInfo.setNumber(fabricStock.getEpc());
				labelInfo.setStatus("5");
				labelInfo.setType("0");
				labelInfoService.saveOrUpdate(labelInfo);


				//删除库存

				fabricStockService.delete(fabricStock);
				//删除基础信息
				fabricService.executeUpdateSql("delete from base_fabric where barcode = '" + fabricStock.getBarCode() + "'");
			}
		}

		return  new AjaxJsonResponse(true, "-1", "销毁成功", fabricDelete);
	}

}
