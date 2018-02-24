
package com.iuling.mods.fabric.service;

import com.iuling.comm.json.AjaxJsonResponse;
import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.base.entity.Fabric;
import com.iuling.mods.base.entity.LabelInfo;
import com.iuling.mods.base.mapper.ShelvesMapper;
import com.iuling.mods.base.service.LabelInfoService;
import com.iuling.mods.fabric.entity.FabricBind;
import com.iuling.mods.fabric.entity.FabricStock;
import com.iuling.mods.fabric.mapper.FabricStockMapper;
import com.iuling.mods.fabric.param.FabricBindParam;
import com.iuling.mods.fabric.param.FabricInventoryParam;
import com.iuling.mods.fabric.param.FabricShelvesParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 面料色卡库存信息Service
 * @author 潘俞再
 * @version 2017-09-19
 */
@Service
@Transactional(readOnly = true)
public class FabricStockService extends CrudService<FabricStockMapper, FabricStock> {


	@Autowired
	public FabricBindService fabricBindService;
	@Autowired
	public FabricStockMapper fabricStockMapper;
	@Autowired
	public ShelvesMapper shelvesMapper;

	@Autowired
	public LabelInfoService LabelInfoService;

	public List<FabricStock> findAllFabricStockList(FabricStock entity){
		return fabricStockMapper.findAllList(entity);
	}

	public FabricStock get(String id) {
		return super.get(id);
	}
	
	public List<FabricStock> findList(FabricStock fabricStock) {
		return super.findList(fabricStock);
	}
	
	public Page<FabricStock> findPage(Page<FabricStock> page, FabricStock fabricStock) {
		return super.findPage(page, fabricStock);
	}
	
	@Transactional(readOnly = false)
	public void save(FabricStock fabricStock) {
		super.save(fabricStock);
	}
	
	@Transactional(readOnly = false)
	public void delete(FabricStock fabricStock) {
		super.delete(fabricStock);
	}
	@Transactional(readOnly = false)
	public AjaxJsonResponse bind(FabricBindParam fabricBindParam){
		//根据条码获取信息
		FabricStock stock = new FabricStock();
		stock.setBarCode(fabricBindParam.getBarCode());
		List<FabricStock> stockLIstanbul= this.findList(stock);
		if(stockLIstanbul == null || stockLIstanbul.size()== 0){
			return new AjaxJsonResponse(false,"2","根据条码查询不到相关信息",null);
		}

		FabricStock fabricStock = stockLIstanbul.get(0);
		if(fabricStock.getBindStatus() == 2){
			return new AjaxJsonResponse(false,"1","该面料已绑定,请扫描其他面料",null);
		}

		FabricStock stock1 = new FabricStock();
		stock1.setEpc(fabricBindParam.getEpc());
		stock1.setBindStatus(2);
		List<FabricStock> stockLIstanbul1= this.findList(stock1);
		if(stockLIstanbul1 != null && stockLIstanbul1.size() > 0){

			return new AjaxJsonResponse(false,"1","该标签已绑定,请扫描其他标签",null);
		}



		//更新库存记录

		fabricStock.setBindStatus(2);
		fabricStock.setEpc(fabricBindParam.getEpc());
		this.save(fabricStock);
		//fabricStockService.executeUpdateSql("update fabric_stock set bind_status = '2',epc = '"+fabricBindParam.getEpc()+"' where id = '"+fabricStock.getId()+"'");
		//记录绑定信息
		FabricBind bind = new FabricBind();
		bind.setBarCode(fabricStock.getBarCode());
		bind.setEpc(fabricStock.getEpc());
		bind.setName(fabricStock.getName());
		bind.setSupplier(fabricStock.getSupplier());
		bind.setUser(bind.getCurrentUser());
		bind.setBindDate(new Date());
		bind.setFabric(fabricStock.getFabric());
		fabricBindService.save(bind);


		/**
		 * 添加或者更新标签状态
		 */
		LabelInfo labelInfo = new LabelInfo();
		labelInfo.setNumber(fabricStock.getEpc());
		labelInfo.setStatus("1");
		labelInfo.setType("0");
		LabelInfoService.saveOrUpdate(labelInfo);

		return new AjaxJsonResponse(true,"-1","绑定成功",null);
	}


	public List<Fabric> findListByParam(FabricShelvesParam fabricShelvesParam) {
		return fabricStockMapper.findListByParam(fabricShelvesParam);
	}
	public List<FabricStock> findListByParam1(FabricInventoryParam fabricInventoryParam) {
		return fabricStockMapper.findListByParam1(fabricInventoryParam);
	}

	/**
	 * 根据epc查找
	 * @param fabricBindParam
	 * @return
	 */
	public AjaxJsonResponse checkFabric(FabricBindParam fabricBindParam){
		FabricStock stock  = mapper.checkFabric(fabricBindParam);
		int sumNum = 0;
		if(stock == null){
			return new AjaxJsonResponse(false,"1","查询不到面料信息",stock);
		}
		if(stock .getShelves() != null) {
			//查询这个货架的上架数量
			 sumNum = shelvesMapper.selectNumById(stock.getShelves().getId());
			stock .getShelves().setSumNum(sumNum);
		}

		return new AjaxJsonResponse(true,"-1","查找成功",stock);
	}

    public List<FabricStock> findListSubmit(FabricStock fabricStock) {
		return mapper.findListSubmit(fabricStock);
    }

	public List<Fabric> findListByParam2(FabricShelvesParam fabricInventoryParam) {
		return mapper.findListByParam2(fabricInventoryParam);
	}

    public AjaxJsonResponse checkFabricByBarCode(FabricBindParam fabricBindParam) {
		FabricStock stock  = mapper.checkFabricByBarCode(fabricBindParam);
		if(stock == null){
			return new AjaxJsonResponse(false,"1","查询不到面料信息",stock);
		}


		return new AjaxJsonResponse(true,"-1","查找成功",stock);
    }

	public AjaxJsonResponse checkFabricByEpcList(FabricBindParam fabricBindParam) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<FabricStock> list = mapper.checkFabricByEpcList(fabricBindParam);
		map.put("list",list);
		return new AjaxJsonResponse(true,"-1","查找成功",map);
	}
}