/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.service;

import com.iuling.comm.config.OrderCodeKeys;
import com.iuling.comm.json.AjaxJsonResponse;
import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.base.entity.BaseCommonRole;
import com.iuling.mods.base.entity.Fabric;
import com.iuling.mods.base.service.BaseCommonRoleService;
import com.iuling.mods.base.service.OrderCodeService;
import com.iuling.mods.clothingmanageformj.param.KindclothingLendParam;
import com.iuling.mods.fabric.entity.FabricOrder;
import com.iuling.mods.fabric.entity.FabricOrderItem;
import com.iuling.mods.fabric.mapper.FabricOrderMapper;
import com.iuling.mods.fabric.param.FabricInventoryParam;
import com.iuling.mods.fabric.param.FabricShelvesParam;
import com.iuling.mods.fabric.param.OrderMapping;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 色卡调布Service
 * @author 潘俞再
 * @version 2017-09-23
 */
@Service
@Transactional(readOnly = true)
public class FabricOrderService extends CrudService<FabricOrderMapper, FabricOrder> {
	@Autowired
	private BaseCommonRoleService baseCommonRoleService;

	@Autowired
	private OrderCodeService orderCodeService;

	@Autowired
	private FabricOrderItemService fabricOrderItemService;

	@Autowired
	private FabricStockService fabricStockService;



	public FabricOrder get(String id) {
		return super.get(id);
	}
	
	public List<FabricOrder> findList(FabricOrder fabricOrder) {
		return super.findList(fabricOrder);
	}
	
	public Page<FabricOrder> findPage(Page<FabricOrder> page, FabricOrder fabricOrder) {
		return super.findPage(page, fabricOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(FabricOrder fabricOrder) {
		super.save(fabricOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(FabricOrder fabricOrder) {
		super.delete(fabricOrder);
	}

	/**
	 * 提交调布订单
	 * @param fabricInventoryParam
	 * @return
	 */
	@Transactional(readOnly = false)
	public  AjaxJsonResponse submit(FabricInventoryParam fabricInventoryParam){
		KindclothingLendParam p = new KindclothingLendParam();
		p.setName(fabricInventoryParam.getName());
		BaseCommonRole role =  baseCommonRoleService.selectByName(p);
		if(role == null){
			return new AjaxJsonResponse(false,"1","查询不到设计师信息,名称有误",null);
		}
		/**
		 * 添加订单
		 */
		FabricOrder fabricOrder = new FabricOrder();
		fabricOrder.setCode(orderCodeService.getOrderCode(OrderCodeKeys.DB_FABRIC_KEY));
		fabricOrder.setCommonRole(role);
		fabricOrder.setNum(fabricInventoryParam.getMappings().size());
		fabricOrder.setOrderDate(new Date());
		fabricOrder.setStatus(1);
		this.save(fabricOrder);
		/**
		 * 添加详情
		 */
		for(OrderMapping map :fabricInventoryParam.getMappings() ){
			FabricShelvesParam param = new FabricShelvesParam();
			List<String> temp = new ArrayList<>();
			temp.add(map.getEpc());
			param.setEpcList(temp);
			List<Fabric> list = fabricStockService.findListByParam2(param);
			Fabric fabric = (list.size() > 0)?list.get(0):null;
			if(fabric == null){
				return new AjaxJsonResponse(false,"1","查询不到面料信息,epc["+map.getEpc()+"]有误",null);
			}

			FabricOrderItem fabricOrderItem = new FabricOrderItem();
			fabricOrderItem.setNum(map.getNum());
			fabricOrderItem.setFabricOrder(fabricOrder);
			fabricOrderItem.setSupplier(fabric.getSupplier());
			fabricOrderItem.setEpc(map.getEpc());
			fabricOrderItem.setColor(fabric.getColor());
			if (!TextUtils.isEmpty(fabric.getPrice01())) {
				fabricOrderItem.setPrice01(Double.parseDouble(fabric.getPrice01()));
			}
			if(fabric.getPrice02() != null) {
				fabricOrderItem.setPrice02(Double.parseDouble(fabric.getPrice02()));
			}
			fabricOrderItem.setFstatus(1);
			fabricOrderItem.setGstatus(1);
			fabricOrderItemService.save(fabricOrderItem);
		}


		return new AjaxJsonResponse(true,"-1","提交成功",fabricOrder);
	}
}