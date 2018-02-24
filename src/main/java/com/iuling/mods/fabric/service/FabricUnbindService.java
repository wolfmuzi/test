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
import com.iuling.mods.base.service.LabelInfoService;
import com.iuling.mods.base.service.OrderCodeService;
import com.iuling.mods.fabric.entity.FabricStock;
import com.iuling.mods.fabric.entity.FabricUnbindItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.fabric.entity.FabricUnbind;
import com.iuling.mods.fabric.mapper.FabricUnbindMapper;

/**
 * 解绑色卡记录Service
 * @author 潘俞再
 * @version 2017-10-18
 */
@Service
@Transactional(readOnly = true)
public class FabricUnbindService extends CrudService<FabricUnbindMapper, FabricUnbind> {


	@Autowired
	private FabricStockService fabricStockService;
	@Autowired
	private OrderCodeService orderCodeService;

	@Autowired
	private LabelInfoService labelInfoService;
	@Autowired
	private FabricUnbindItemService fabricUnbindItemService;




	public FabricUnbind get(String id) {
		return super.get(id);
	}

	public List<FabricUnbind> findList(FabricUnbind fabricUnbind) {
		return super.findList(fabricUnbind);
	}

	public Page<FabricUnbind> findPage(Page<FabricUnbind> page, FabricUnbind fabricUnbind) {
		return super.findPage(page, fabricUnbind);
	}

	@Transactional(readOnly = false)
	public void save(FabricUnbind fabricUnbind) {
		super.save(fabricUnbind);
	}

	@Transactional(readOnly = false)
	public void delete(FabricUnbind fabricUnbind) {
		super.delete(fabricUnbind);
	}

	/**
	 * 根据epc解绑
	 * @param shelvesParam
	 * @return
	 */
	@Transactional(readOnly = false)
	public AjaxJsonResponse unbindFabric(ShelvesParam shelvesParam){
		List<String> epcList = shelvesParam.getEpcList();
		//记录解绑信息
		FabricUnbind fabricUnbind = new FabricUnbind();
		fabricUnbind.setCode(orderCodeService.getOrderCode(OrderCodeKeys.F_UNBIND_KEY));
		fabricUnbind.setUnbindUser(fabricUnbind.getCurrentUser());
		fabricUnbind.setUnbindDate(new Date());
		fabricUnbind.setNum(epcList.size());
		this.save(fabricUnbind);

		for(String epc : epcList) {
			FabricStock stock = new FabricStock();
			stock.setEpc(epc);
			List<FabricStock> stockList = fabricStockService.findList(stock);
			FabricStock fabricStock = (stockList.size() > 0) ? stockList.get(0) : null;
			FabricUnbindItem item = new FabricUnbindItem();
			if (fabricStock == null) {
				item.setEpc(epc);
			}else{
				//记录详情
				item.setBarCode(fabricStock.getBarCode());
				item.setEpc(fabricStock.getEpc());
				item.setFabricUnbind(fabricUnbind);
				item.setName(fabricStock.getName());
				item.setSupplier(fabricStock.getSupplier());
			}

			fabricUnbindItemService.save(item);
			if(fabricStock != null) {
				/**
				 * 添加或者更新标签状态
				 */
				LabelInfo labelInfo = new LabelInfo();
				labelInfo.setNumber(fabricStock.getEpc());
				labelInfo.setStatus("4");
				labelInfo.setType("0");
				labelInfoService.saveOrUpdate(labelInfo);

				//修改库存为未绑定
				fabricStock.setBindStatus(1);
				fabricStock.setEpc(null);
				fabricStock.setShelvesStatus(1);
				fabricStock.setShelves(null);
				fabricStock.setShelvesStatus(1);
				fabricStockService.save(fabricStock);
			}
		}
		return  new AjaxJsonResponse(true, "-1", "解绑成功", fabricUnbind);
	}

}