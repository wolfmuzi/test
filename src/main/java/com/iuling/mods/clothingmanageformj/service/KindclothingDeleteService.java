/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.service;

import com.iuling.comm.config.OrderCodeKeys;
import com.iuling.comm.json.AjaxJsonResponse;
import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.base.entity.LabelInfo;
import com.iuling.mods.base.service.KindClothingService;
import com.iuling.mods.base.service.LabelInfoService;
import com.iuling.mods.base.service.OrderCodeService;
import com.iuling.mods.clothingmanageformj.entity.KindclothingDelete;
import com.iuling.mods.clothingmanageformj.entity.KindclothingDeleteItem;
import com.iuling.mods.clothingmanageformj.entity.KindclothingStock;
import com.iuling.mods.clothingmanageformj.mapper.KindclothingDeleteMapper;
import com.iuling.mods.clothingmanageformj.param.KindclothingBindParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 样衣销毁记录Service
 * @author 彭成
 * @version 2017-09-26
 */
@Service
@Transactional(readOnly = true)
public class KindclothingDeleteService extends CrudService<KindclothingDeleteMapper, KindclothingDelete> {

	@Autowired
	private KindclothingStockService kindclothingStockService;
	@Autowired
	private KindclothingDeleteItemService kindclothingDeleteItemService;

	@Autowired
	private OrderCodeService orderCodeService;
	@Autowired
	private KindClothingService kindClothingService;

	@Autowired
	private LabelInfoService labelInfoService;



	public KindclothingDelete get(String id) {
		return super.get(id);
	}
	
	public List<KindclothingDelete> findList(KindclothingDelete kindclothingDelete) {
		return super.findList(kindclothingDelete);
	}
	
	public Page<KindclothingDelete> findPage(Page<KindclothingDelete> page, KindclothingDelete kindclothingDelete) {
		return super.findPage(page, kindclothingDelete);
	}
	
	@Transactional(readOnly = false)
	public void save(KindclothingDelete kindclothingDelete) {
		super.save(kindclothingDelete);
	}
	
	@Transactional(readOnly = false)
	public void delete(KindclothingDelete kindclothingDelete) {
		super.delete(kindclothingDelete);
	}


	/**
	 * 样衣销毁
	 * @return
	 */
	@Transactional(readOnly = false)
	public AjaxJsonResponse kindclothingDelete(KindclothingBindParam param){
		List<String> list = param.getEpcList();
		//记录销毁信息
		KindclothingDelete kindclothingDelete = new KindclothingDelete();
		kindclothingDelete.setNum(list.size());
		kindclothingDelete.setDeleteDate(new Date());
		kindclothingDelete.setDeleteUser(kindclothingDelete.getCurrentUser());
		kindclothingDelete.setCode(orderCodeService.getOrderCode(OrderCodeKeys.K_DELETE_KEY));
		this.save(kindclothingDelete);
		for (String epc:list){
			param.setEpc(epc);
			KindclothingStock kindclothingStock = kindclothingStockService.findKindclothingByEpc(param);
			KindclothingDeleteItem item = new KindclothingDeleteItem();
			if(kindclothingStock == null){
				item.setEpc(epc);
			}else{
				item.setBarCode(kindclothingStock.getBarCode());
				item.setEpc(kindclothingStock.getEpc());
				item.setKindclothingDelete(kindclothingDelete);
				item.setName(kindclothingStock.getName());
				item.setSupplier(kindclothingStock.getSupplier());
			}

			kindclothingDeleteItemService.save(item);
			if(kindclothingStock != null) {
				String barCode = kindclothingStock.getKindClothing().getNumber();  //样衣条码

				/**
				 * 添加或者更新标签状态
				 */
				LabelInfo labelInfo = new LabelInfo();
				labelInfo.setNumber(param.getEpc());
				labelInfo.setStatus("5");
				labelInfo.setType("1");
				labelInfoService.saveOrUpdate(labelInfo);

				//删除库存
				kindclothingStockService.executeUpdateSql("DELETE FROM kindclothing_stock where bar_code = '" + barCode + "'");
				//删除基础信息
				kindClothingService.executeUpdateSql("DELETE FROM base_kindclothing WHERE number = '" + barCode + "'");
			}
		}
		return  new AjaxJsonResponse(true, "-1", "销毁成功", kindclothingDelete);
	}

}