/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.service;

import com.iuling.comm.config.OrderCodeKeys;
import com.iuling.comm.json.AjaxJsonResponse;
import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.base.entity.BaseCommonRole;
import com.iuling.mods.base.service.BaseCommonRoleService;
import com.iuling.mods.base.service.OrderCodeService;
import com.iuling.mods.clothingmanageformj.entity.KindclothingLendItem;
import com.iuling.mods.clothingmanageformj.entity.KindclothingReturn;
import com.iuling.mods.clothingmanageformj.entity.KindclothingReturnItem;
import com.iuling.mods.clothingmanageformj.mapper.KindclothingReturnMapper;
import com.iuling.mods.clothingmanageformj.param.KindclothingLendParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 样衣归还管理Service
 * @author 彭成
 * @version 2017-09-23
 */
@Service
@Transactional(readOnly = true)
public class KindclothingReturnService extends CrudService<KindclothingReturnMapper, KindclothingReturn> {

	@Autowired
	private KindclothingReturnMapper kindclothingReturnMapper;
	@Autowired
	private BaseCommonRoleService baseCommonRoleService;
	@Autowired
	private OrderCodeService orderCodeService;
	@Autowired
	private KindclothingStockService kindclothingStockService;
	@Autowired
	private KindclothingReturnItemService kindclothingReturnItemService;
	@Autowired
	private KindclothingLendService kindclothingLendService;
	@Autowired
	private KindclothingLendItemService kindclothingLendItemService;

	public KindclothingReturn get(String id) {
		return super.get(id);
	}
	
	public List<KindclothingReturn> findList(KindclothingReturn kindclothingReturn) {
		return super.findList(kindclothingReturn);
	}
	
	public Page<KindclothingReturn> findPage(Page<KindclothingReturn> page, KindclothingReturn kindclothingReturn) {
		return super.findPage(page, kindclothingReturn);
	}
	
	@Transactional(readOnly = false)
	public void save(KindclothingReturn kindclothingReturn) {
		super.save(kindclothingReturn);
	}
	
	@Transactional(readOnly = false)
	public void delete(KindclothingReturn kindclothingReturn) {
		super.delete(kindclothingReturn);
	}

	/**
	 * 根据epc获得借用的详情数据
	 */
	List<KindclothingLendItem> getByEpcDatas(KindclothingLendParam param){
		return kindclothingReturnMapper.selectByEpcDatas(param);
	}

	/**
	 * 根据条形码获得归还人信息
	 */
	public List <KindclothingLendItem> findByBarCodeCommonRole(KindclothingLendParam param){
		return kindclothingReturnMapper.findByBarCodeCommonRole(param);
	}



	/**
	 * 根据用户获得未归还样衣信息
	 */
	public AjaxJsonResponse findByParam(KindclothingLendParam param) {
		BaseCommonRole baseCommonRole = baseCommonRoleService.selectByName(param);   //根据姓名获取借用人
		String id = baseCommonRole.getId();  //获得用户id
		param.setLendUserId(id);
		List<KindclothingLendItem> list = kindclothingReturnMapper.findListByParam(param);
		int nums = list.size();
		if(list == null || nums == 0 ){
			return new AjaxJsonResponse(false,"1","您未借出样衣",null);
		}

		Date expectedReturnDate = null; //预计借用时间
		Date today = new Date();
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(today);

		for(KindclothingLendItem kindclothingLendItem : list) {
			Long diff = null; //两个时间的差
			Long days = null; //两个时间的差换算为天
			int day = 0;  //将天数换算为正数
			String deadline = null; //归还期限
			boolean flag = false;

			expectedReturnDate = kindclothingLendItem.getExpectedReturnDate();
			c2.setTime(expectedReturnDate);
			flag = c2.after(c1);     //判断是否同一天
			diff = expectedReturnDate.getTime() - today.getTime();
			days = diff / (1000 * 60 * 60 * 24);
			day = Math.abs(days.intValue());

			if (days >= 0) {
				if (flag) {
					day += 1;
				}/*else if(days == 0){
					day++;
				}*/
				deadline = day + "天";
			} else {
				deadline = "逾期" + day + "天";
			}
//			deadline = days >= 0? day+"天":"逾期"+day+"天";
			kindclothingLendItem.setDeadline(deadline);
			kindclothingLendItem.setLendTotal(nums);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("list",list);
		map.put("user",baseCommonRole);
		return new AjaxJsonResponse(true,"-1","操作成功",map);
	}

	/**
	 * 归还
	 */
	@Transactional(readOnly = false)
	public AjaxJsonResponse kindclothingReturnByEpc(KindclothingLendParam param) {

		/**
		 * 生成归还单号
		 */
//		BaseCommonRole baseCommonRole = baseCommonRoleService.get(param.getLendUserId());   //借用人
		BaseCommonRole baseCommonRole = baseCommonRoleService.selectByName(param);   //根据姓名获取借用人
		param.setLendUserId(baseCommonRole.getId());
		List<KindclothingLendItem> kindclothingLendItemList = kindclothingReturnMapper.findListByParam(param); //借用人借用总样衣
		List<KindclothingLendItem> list = this.getByEpcDatas(param);  //借用人归还样衣
		int returnNum = list.size();   //归还数量
		if(returnNum == 0) {
			return new AjaxJsonResponse(false,"2","样衣已归还",null);
		}
		int nums = kindclothingLendItemList.size(); //借用总样衣
		int surplusNum = nums - returnNum; //剩余未归还总数

		KindclothingReturn kindclothingReturn = new KindclothingReturn();
		kindclothingReturn.setBaseCommonRole(baseCommonRole);
		String roleType = baseCommonRole.getType()==1? "设计师":"质检员";
		kindclothingReturn.setRoleType(roleType);
		kindclothingReturn.setReturnNo(orderCodeService.getOrderCode(OrderCodeKeys.GH_KINDCLOTHING_KEY));
		kindclothingReturn.setSurplusNum(surplusNum);
		kindclothingReturn.setReturnNum(returnNum);
		kindclothingReturn.setReturnDate(new Date());
		this.save(kindclothingReturn);
		/**
		 * 记录归还详情
		 */
		int lendNum = 0;  //借用数量
		int totalLendNum = 0;  //借用总数
		for(KindclothingLendItem item : list) {
			KindclothingReturnItem kindclothingReturnItem = new KindclothingReturnItem();
			kindclothingReturnItem.setBaseCommonRole(baseCommonRole);
			kindclothingReturnItem.setReturnNo(kindclothingReturn.getReturnNo());
			kindclothingReturnItem.setLendNo(item.getLendNo());     //借用单号
			kindclothingReturnItem.setName(item.getName());
			kindclothingReturnItem.setEpc(item.getBarCode());  //样衣标签
			kindclothingReturnItem.setStyle(item.getStyle());
			kindclothingReturnItem.setEditionnumber(item.getEditionnumber());
			kindclothingReturnItem.setSupplier(item.getSupplier());
			kindclothingReturnItemService.save(kindclothingReturnItem);
			/**
			 * 更新样衣库存状态  1(在库) 2(借出)
			 */
			kindclothingStockService.executeUpdateSql("update kindclothing_stock set is_lend = 1 where epc = '"+item.getBarCode()+"'");
			/**
			 * 借用详情表:是否归还状态 1(未归还) 2(已归还)
			 */
			kindclothingLendItemService.executeUpdateSql("update kindclothing_lend_item set lend_isreturn = 2 where bar_code = '"+item.getBarCode()+"'");
			/**
			 * 更新样衣借用表:归还状态 1(未归还) 2(部分归还) 3(全部归还)
			 */
			item.setLendIsReturn(1);
			lendNum = kindclothingLendItemService.findByLendNo(item);
			totalLendNum = kindclothingLendItemService.getTotalByLendNo(item);
			if(lendNum == 0) { //全部归还
				kindclothingLendService.executeUpdateSql("update kindclothing_lend set lend_return_status = 3 where lend_no = '"+item.getLendNo()+"'");
			} else if(lendNum < totalLendNum) { //部分归还
				kindclothingLendService.executeUpdateSql("update kindclothing_lend set lend_return_status =2 where lend_no = '"+item.getLendNo()+"'");
			} else if(lendNum == totalLendNum) {  //未归还
				kindclothingLendService.executeUpdateSql("update kindclothing_lend set lend_return_status = 1 where lend_no = '"+item.getLendNo()+"'");
			}
		}

		kindclothingReturn.setBaseCommonRoleName(baseCommonRole.getName());  //返回的归还人名称
		/**
		 * 返回前端数据
		 */
		List<KindclothingLendItem> surplusList = kindclothingReturnMapper.findListByParam(param);//将未归还的epc返回前端
		Map<String, Object> map = new HashMap<>();
		map.put("list",surplusList);
		map.put("returnData",kindclothingReturn);

		return new AjaxJsonResponse(true,"-1","归还成功",map);
	}
}