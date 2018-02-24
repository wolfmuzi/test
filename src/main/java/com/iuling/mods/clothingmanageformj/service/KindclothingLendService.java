/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.service;

import com.iuling.comm.config.OrderCodeKeys;
import com.iuling.comm.json.AjaxJsonResponse;
import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.base.entity.BaseCommonRole;
import com.iuling.mods.base.entity.KindClothing;
import com.iuling.mods.base.entity.LabelInfo;
import com.iuling.mods.base.service.BaseCommonRoleService;
import com.iuling.mods.base.service.LabelInfoService;
import com.iuling.mods.base.service.OrderCodeService;
import com.iuling.mods.clothingmanageformj.entity.KindclothingLend;
import com.iuling.mods.clothingmanageformj.entity.KindclothingLendItem;
import com.iuling.mods.clothingmanageformj.mapper.KindclothingLendMapper;
import com.iuling.mods.clothingmanageformj.param.KindclothingLendParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 样衣借用管理Service
 * @author 彭成
 * @version 2017-09-22
 */
@Service
@Transactional(readOnly = true)
public class KindclothingLendService extends CrudService<KindclothingLendMapper, KindclothingLend> {

	@Autowired
	private KindclothingLendMapper kindclothingLendMapper;

	@Autowired
	private LabelInfoService labelInfoService;





	@Autowired
	private OrderCodeService orderCodeService;

	@Autowired
	private KindclothingLendItemService kindclothingLendItemService;

	@Autowired
	private BaseCommonRoleService baseCommonRoleService;

	@Autowired
	private KindclothingStockService kindclothingStockService;

	public KindclothingLend get(String id) {
		return super.get(id);
	}
	
	public List<KindclothingLend> findList(KindclothingLend kindclothingLend) {
		return super.findList(kindclothingLend);
	}
	
	public Page<KindclothingLend> findPage(Page<KindclothingLend> page, KindclothingLend kindclothingLend) {

		return super.findPage(page, kindclothingLend);
	}
	
	@Transactional(readOnly = false)
	public void save(KindclothingLend kindclothingLend) {
		super.save(kindclothingLend);
	}
	
	@Transactional(readOnly = false)
	public void delete(KindclothingLend kindclothingLend) {
		super.delete(kindclothingLend);
	}

	//根据是否借出、上架状态获取样衣信息
	public List<KindClothing> findListByParam (KindclothingLendParam param) {
		return kindclothingLendMapper.findListByParam(param);
	}

	//借出
	@Transactional(readOnly = false)
	public AjaxJsonResponse lend (KindclothingLendParam param) {
		param.setIsLend(1);   //借出状态
		param.setShelvesStatus(2);  //上架状态
		List<KindClothing> list= this.findListByParam(param);
		if(list == null || list.size() == 0 ){
			return new AjaxJsonResponse(false,"4","查询不到样衣信息",null);
		}

		/**
		 * 生成单号
		 */
//		BaseCommonRole baseCommonRole = baseCommonRoleService.get(param.getLendUserId());   //借用人
		BaseCommonRole baseCommonRole = baseCommonRoleService.selectByName(param);   //根据姓名获取借用人
		KindclothingLendItem item = new KindclothingLendItem();  //以前借用详情entity
		item.setBaseCommonRole(baseCommonRole);
		item.setLendIsReturn(1);
		int total = kindclothingLendItemService.sumByBaseCommonRoleId(item);  //统计借用人的借用总数
		int current = list.size();  //本次借用
		total = total + current;  //计算借用总数

		Calendar c = Calendar.getInstance();  //获得7天后的当前时间
		Date today = c.getTime();  //当前时间
		c.add(Calendar.DAY_OF_YEAR, 7);
		Date lendDate = c.getTime();

		KindclothingLend kindclothingLend = new KindclothingLend();  //样衣借出entity
		kindclothingLend.setBaseCommonRole(baseCommonRole);
		kindclothingLend.setLendNo(orderCodeService.getOrderCode(OrderCodeKeys.JY_KINDCLOTHING_KEY));
		kindclothingLend.setLendCurrentNum(current);
		kindclothingLend.setLendTotal(total);
		kindclothingLend.setLendDate(today);
		kindclothingLend.setExpectedReturnDate(lendDate);
		kindclothingLend.setLendReturnStatus(1);
		this.save(kindclothingLend); //保存借用信息
		/**
		 * 记录借用详情
		 */
		for(KindClothing kindclothing:list){
			KindclothingLendItem kindclothingLendItem = new KindclothingLendItem();
			kindclothingLendItem.setBaseCommonRole(baseCommonRole);
			kindclothingLendItem.setLendNo(kindclothingLend.getLendNo());
			kindclothingLendItem.setName(kindclothing.getName());
			kindclothingLendItem.setBarCode(kindclothing.getEpc());  //样衣标签
			kindclothingLendItem.setStyle(kindclothing.getStyle());
			kindclothingLendItem.setEditionnumber(kindclothing.getEditionNumber());
			kindclothingLendItem.setSupplier(kindclothing.getSupplier());
			kindclothingLendItem.setLendIsReturn(1);
			kindclothingLendItemService.save(kindclothingLendItem);


			/**
			 * 添加或者更新标签状态
			 */
			LabelInfo labelInfo = new LabelInfo();
			labelInfo.setNumber(kindclothing.getEpc());
			labelInfo.setStatus("3");
			labelInfo.setType("1");
			labelInfoService.saveOrUpdate(labelInfo);


			/**
			 * 更新样衣库存借出状态 1(在库) 2(借出)
			 */
			kindclothingStockService.executeUpdateSql("update kindclothing_stock set is_lend = 2 where bar_code = '"+kindclothing.getNumber()+"'");
		}

		/**
		 * 返回前台借用数据
		 */
		kindclothingLend.setLendName(baseCommonRole.getName());

		return new AjaxJsonResponse(true,"-1","借出成功 ",kindclothingLend);
	}
}