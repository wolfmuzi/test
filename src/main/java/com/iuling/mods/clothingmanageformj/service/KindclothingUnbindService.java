/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.service;

import com.iuling.comm.config.OrderCodeKeys;
import com.iuling.comm.json.AjaxJsonResponse;
import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.base.entity.LabelInfo;
import com.iuling.mods.base.service.LabelInfoService;
import com.iuling.mods.base.service.OrderCodeService;
import com.iuling.mods.clothingmanageformj.entity.KindclothingStock;
import com.iuling.mods.clothingmanageformj.entity.KindclothingUnbind;
import com.iuling.mods.clothingmanageformj.entity.KindclothingUnbindItem;
import com.iuling.mods.clothingmanageformj.mapper.KindclothingUnbindMapper;
import com.iuling.mods.clothingmanageformj.param.KindclothingBindParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 样衣解绑记录Service
 * @author 彭成
 * @version 2017-09-26
 */
@Service
@Transactional(readOnly = true)
public class KindclothingUnbindService extends CrudService<KindclothingUnbindMapper, KindclothingUnbind> {

	@Autowired
	private KindclothingStockService kindclothingStockService;

	@Autowired
	private KindclothingUnbindItemService kindclothingUnbindItemService;
	@Autowired
	private OrderCodeService orderCodeService;

	@Autowired
	private LabelInfoService labelInfoService;



	@Autowired
	private KindclothingUnbindService kindclothingUnbindService;

	public KindclothingUnbind get(String id) {
		return super.get(id);
	}
	
	public List<KindclothingUnbind> findList(KindclothingUnbind kindclothingUnbind) {
		return super.findList(kindclothingUnbind);
	}
	
	public Page<KindclothingUnbind> findPage(Page<KindclothingUnbind> page, KindclothingUnbind kindclothingUnbind) {
		return super.findPage(page, kindclothingUnbind);
	}
	
	@Transactional(readOnly = false)
	public void save(KindclothingUnbind kindclothingUnbind) {
		super.save(kindclothingUnbind);
	}
	
	@Transactional(readOnly = false)
	public void delete(KindclothingUnbind kindclothingUnbind) {
		super.delete(kindclothingUnbind);
	}

	/**
	 * 样衣解绑
	 * @return
	 */
	@Transactional(readOnly = false)
	public AjaxJsonResponse kindclothingUnbind(KindclothingBindParam param){

		List<String> list = param.getEpcList();
		/**
		 * 记录解绑信息
		 */
		KindclothingUnbind kindclothingUnbind = new KindclothingUnbind();
		kindclothingUnbind.setNum(list.size());
		kindclothingUnbind.setUnbindDate(new Date());
		kindclothingUnbind.setUnbindUser(kindclothingUnbind.getCurrentUser());
		kindclothingUnbind.setCode(orderCodeService.getOrderCode(OrderCodeKeys.K_UNBIND_KEY));  //单号
		this.save(kindclothingUnbind);

		for(String epc:list) {
			param.setEpc(epc);
			KindclothingStock kindclothingStock = kindclothingStockService.findKindclothingByEpc(param);
			KindclothingUnbindItem item = new KindclothingUnbindItem();
			if (kindclothingStock == null) {
				item.setEpc(epc);
			}else{
				item.setEpc(kindclothingStock.getEpc());
				item.setBarCode(kindclothingStock.getBarCode());
				item.setKindclothingUnbind(kindclothingUnbind);
				item.setName(kindclothingStock.getName());
				item.setSupplier(kindclothingStock.getSupplier());
			}
			kindclothingUnbindItemService.save(item);

			if(kindclothingStock != null) {
				/**
				 * 添加或者更新标签状态
				 */
				LabelInfo labelInfo = new LabelInfo();
				labelInfo.setNumber(epc);
				labelInfo.setStatus("4");
				labelInfo.setType("1");
				labelInfoService.saveOrUpdate(labelInfo);


				/**
				 * 更新样衣库存:样衣 1未绑定、清除epc,清除货架id、状态 1未上架
				 */
				kindclothingStockService.executeUpdateSql("update kindclothing_stock set epc='',bind_status=1,shelves_id='',shelves_status=1 where epc ='" + epc + "'");
				//更新上架时间
				kindclothingStockService.executeUpdateSql("update base_kindclothing set shelves_date = null where number = '"+ kindclothingStock.getBarCode() +"'");
			}
		}
		return new AjaxJsonResponse(true,"-1","解绑成功",kindclothingUnbind);
	}
}