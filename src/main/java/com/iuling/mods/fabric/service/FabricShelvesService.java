
package com.iuling.mods.fabric.service;

import java.util.Date;
import java.util.List;

import com.iuling.comm.config.OrderCodeKeys;
import com.iuling.comm.json.AjaxJsonResponse;
import com.iuling.mods.base.entity.Fabric;
import com.iuling.mods.base.entity.LabelInfo;
import com.iuling.mods.base.entity.Shelves;
import com.iuling.mods.base.service.LabelInfoService;
import com.iuling.mods.base.service.OrderCodeService;
import com.iuling.mods.fabric.entity.FabricShelvesItem;
import com.iuling.mods.fabric.param.FabricShelvesParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.fabric.entity.FabricShelves;
import com.iuling.mods.fabric.mapper.FabricShelvesMapper;

/**
 * 上架信息Service
 * @author 潘俞再
 * @version 2017-09-20
 */
@Service
@Transactional(readOnly = true)
public class FabricShelvesService extends CrudService<FabricShelvesMapper, FabricShelves> {
	@Autowired
	private  FabricStockService fabricStockService;
	@Autowired
	private OrderCodeService orderCodeService;
	@Autowired
	private LabelInfoService labelInfoService;

	@Autowired
	private  FabricShelvesItemService fabricShelvesItemService;
	public FabricShelves get(String id) {
		return super.get(id);
	}
	
	public List<FabricShelves> findList(FabricShelves fabricShelves) {
		return super.findList(fabricShelves);
	}
	
	public Page<FabricShelves> findPage(Page<FabricShelves> page, FabricShelves fabricShelves) {
		return super.findPage(page, fabricShelves);
	}
	
	@Transactional(readOnly = false)
	public void save(FabricShelves fabricShelves) {
		super.save(fabricShelves);
	}
	
	@Transactional(readOnly = false)
	public void delete(FabricShelves fabricShelves) {
		super.delete(fabricShelves);
	}

	//上架
	@Transactional(readOnly = false)
	public AjaxJsonResponse shelves(FabricShelvesParam fabricShelvesParam){
		fabricShelvesParam.setBindStatus(2);
		fabricShelvesParam.setShelvesStatus(1);
		List<Fabric> list= fabricStockService.findListByParam(fabricShelvesParam);
		if(list == null || list.size() == 0 ){
			return new AjaxJsonResponse(false,"1","查询不到面料信息",null);
		}
		Shelves shelves = new Shelves();
		shelves.setId(fabricShelvesParam.getShelvesId());
		FabricShelves fabricShelves = new FabricShelves();
		//生成单号
		fabricShelves.setCode(orderCodeService.getOrderCode(OrderCodeKeys.SJ_SHELVES_KEY));
		fabricShelves.setNum(list.size());
		fabricShelves.setShelves(shelves);
		fabricShelves.setShelvesDate(new Date());
		fabricShelves.setUser(fabricShelves.getCurrentUser());
		this.save(fabricShelves);
		//记录详情
		for(Fabric fabric:list){
			FabricShelvesItem shelvesItem = new FabricShelvesItem();
			shelvesItem.setCode(fabric.getBarcode());
			shelvesItem.setColors(fabric.getColor());
			shelvesItem.setEpc(fabric.getEpc());
			shelvesItem.setFabricShelves(fabricShelves);
			shelvesItem.setIngredients(fabric.getIngredients());
			shelvesItem.setName(fabric.getName());
			shelvesItem.setSupplier(fabric.getSupplier());
			fabricShelvesItemService.save(shelvesItem);
			//更新库存状态
			fabricStockService.executeUpdateSql("update fabric_stock set shelves_status = '2',shelves_id = '"+fabricShelvesParam.getShelvesId()+"' where bar_code = '"+fabric.getBarcode()+"'");
			/**
			 * 添加或者更新标签状态
			 */
			LabelInfo labelInfo = new LabelInfo();
			labelInfo.setNumber(fabric.getEpc());
			labelInfo.setStatus("2");
			labelInfo.setType("0");
			labelInfoService.saveOrUpdate(labelInfo);
		}
		return new AjaxJsonResponse(true,"-1","上架成功 ",null);
	}
}