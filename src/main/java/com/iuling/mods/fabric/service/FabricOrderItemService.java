/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.iuling.comm.utils.DateUtils;
import com.iuling.mods.base.entity.BaseCommonRole;
import com.iuling.mods.base.entity.Fabric;
import com.iuling.mods.base.entity.Supplier;
import com.iuling.mods.base.mapper.BaseCommonRoleMapper;
import com.iuling.mods.base.mapper.SupplierMapper;
import com.iuling.mods.base.param.CommonRoleVo;
import com.iuling.mods.base.param.SupplierVo;
import com.iuling.mods.base.service.FabricService;
import com.iuling.mods.base.service.SupplierService;
import com.iuling.mods.fabric.entity.FabricOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iuling.core.persistence.Page;
import com.iuling.core.service.CrudService;
import com.iuling.mods.fabric.entity.FabricOrderItem;
import com.iuling.mods.fabric.mapper.FabricOrderItemMapper;

/**
 * 调布订单详情Service
 * @author 潘俞再
 * @version 2017-09-23
 */
@Service
@Transactional(readOnly = true)
public class FabricOrderItemService extends CrudService<FabricOrderItemMapper, FabricOrderItem> {

	@Autowired
	private SupplierMapper supplierMapper;
	@Autowired
	private FabricService fabricService;

	@Autowired
	private BaseCommonRoleMapper baseCommonRoleMapper;

	public FabricOrderItem get(String id) {
		return super.get(id);
	}
	
	public List<FabricOrderItem> findList(FabricOrderItem fabricOrderItem) {
		return super.findList(fabricOrderItem);
	}
	
	public Page<FabricOrderItem> findPage(Page<FabricOrderItem> page, FabricOrderItem fabricOrderItem) {
		return super.findPage(page, fabricOrderItem);
	}
	
	@Transactional(readOnly = false)
	public void save(FabricOrderItem fabricOrderItem) {
		super.save(fabricOrderItem);
	}
	
	@Transactional(readOnly = false)
	public void delete(FabricOrderItem fabricOrderItem) {
		super.delete(fabricOrderItem);
	}

    public List<FabricOrderItem> findByParam(FabricOrderItem fabricOrderItem) {
		return mapper.findByParam(fabricOrderItem);
    }

	/**
	 * 查询供应商信息图表
	 * @return
	 */
	public List<Supplier> supplierForm(Supplier param) {
		List<Supplier> list = new ArrayList<>();
		/**
		 * 查询所有供应商
		 */
		List<Supplier> suppliers = supplierMapper.findList(param);
		for(Supplier supplier:suppliers){

			for(int i = 1; i <= 12;i++) {
				Supplier supplierVo = new Supplier();
				supplierVo.setName(supplier.getName());
				supplierVo.setYue(i + "月");


				/**
				 * 更换率（每月更新率，总数60，需求量20，则更新率20/60）、
				 * 要货满足率（到货10，则要货满足率10/20）、下单成功率（成功下单3，则下单成功率3/10）。
				 总数 : 这个供应商所有的面料款数
				 需求量20  当月设计师调用这个供应商的面料款数
				 到货10   寄版时间做判断
				 下单3     下单时间
				 */
				SimpleDateFormat f = new SimpleDateFormat("yyyy-MM");
				Date date = null;
				try {
					date = f.parse(DateUtils.getYear()+"-0"+i);
				}catch (Exception e){
					e.printStackTrace();
				}

				//总数 : 这个供应商所有的面料款数
				Fabric fabric = new Fabric();
				fabric.setSupplier(supplier);
				List<Fabric> fabrics = fabricService.findList(fabric);
				int sum = fabrics.size();
				// 需求量  当月设计师调用这个供应商的面料款数
				FabricOrderItem fabricOrderItem = new FabricOrderItem();
				fabricOrderItem.setSupplier(supplier);

				fabricOrderItem.setCreateDate(date);
				List<FabricOrderItem> orders = mapper.findList(fabricOrderItem);
				int xuNum = orders.size();
				//到货   寄版时间做判断
				FabricOrderItem daoParam = new FabricOrderItem();
				daoParam.setSupplier(supplier);
				daoParam.setSendDate(date);
				List<FabricOrderItem> daoList = mapper.findListByDate(daoParam);
				int daoNum = daoList.size();

				//下单数   下单时间为判断
				FabricOrderItem orderParam = new FabricOrderItem();
				orderParam.setSupplier(supplier);
				orderParam.setOrderDate(date);
				List<FabricOrderItem> orderList = mapper.findListByDate(orderParam);
				int orderNum = orderList.size();


				//要货率  到货10，则要货满足率10/20
				int getNum = 0;
				if(xuNum != 0 && daoNum != 0){

					getNum =  new BigDecimal(daoNum  ).divide(new BigDecimal(xuNum),2,BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue();
					if(getNum > 100)getNum = 100;
				}
				supplierVo.setGetNum(getNum);

				//下单率  成功下单3，则下单成功率3/10
				int orderSum = 0;
				if(daoNum != 0 && orderNum != 0){
					orderSum = new BigDecimal(orderNum ).divide(new BigDecimal(daoNum),2,BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue();
					if(orderSum > 100)orderSum = 100;
				}
				supplierVo.setOrderNum(orderSum);


				//更新率  总数60，需求量20，则更新率20/60
				int updateNum = 0;
				if(sum != 0 && xuNum != 0) {
					updateNum = new BigDecimal(xuNum ).divide(new BigDecimal(sum),2,BigDecimal.ROUND_HALF_UP).setScale(2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue();
					if(updateNum > 100)updateNum = 100;

				}
				supplierVo.setUpdateNum(updateNum);
				list.add(supplierVo);
			}
		}
		return list;
	}


	/**
	 * 查询设计师信息图表
	 * @return
	 */
	public List<BaseCommonRole> commonRoleForm(BaseCommonRole param) {
		List<BaseCommonRole> list = new ArrayList<>();
		/**
		 * 查询所有设计师
		 */
		List<BaseCommonRole> baseCommonRoles = baseCommonRoleMapper.findList(param);
		for(BaseCommonRole baseCommonRole:baseCommonRoles){

			for(int i = 1; i <= 12;i++) {
				BaseCommonRole commonRoleVo = new BaseCommonRole();
				commonRoleVo.setName(baseCommonRole.getName());
				commonRoleVo.setYue(i + "月");
				FabricOrder temp = new FabricOrder();
				temp.setCommonRole(baseCommonRole);
				SimpleDateFormat f = new SimpleDateFormat("yyyy-MM");
				Date date = null;
				try {
					date = f.parse(DateUtils.getYear()+"-0"+i);
				}catch (Exception e){
					e.printStackTrace();
				}
				/**
				 *
				 *要货100，实际到货50，打版20，则打版率为20/50，下单成功10，则下单成功率为10\50
				 *要货100  当月这个设计师调了多少款
				 *实际到货50  回版时间
				 *打版20     发版时间
				 *下单成功10  下单时间
				 */
				//实际到货  回版时间
				FabricOrderItem daoParam = new FabricOrderItem();
				daoParam.setFabricOrder(temp);
				daoParam.setReturnDate(date);
				List<FabricOrderItem> daoList = mapper.findListByDate(daoParam);
				int daoNum = daoList.size();
				//打版  发版时间
				FabricOrderItem faParam = new FabricOrderItem();
				faParam.setFabricOrder(temp);
				faParam.setFdate(date);
				List<FabricOrderItem> faList = mapper.findListByDate(faParam);
				int faNum = faList.size();
				//下单成功  下单时间
				FabricOrderItem orderParam = new FabricOrderItem();
				orderParam.setFabricOrder(temp);
				orderParam.setOrderDate(date);
				List<FabricOrderItem> orderList = mapper.findListByDate(orderParam);
				int orderNum = orderList.size();
				/**
				 * 打版率  打版/实际到货
				 */
				int getNum = 0;
				if(faNum != 0 && daoNum != 0){
					getNum = new BigDecimal(faNum ).divide(new BigDecimal(daoNum),2,BigDecimal.ROUND_HALF_UP).setScale(2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue();
					if(getNum > 100)getNum = 100;
				}
				commonRoleVo.setGetNum(getNum);
				/**
				 * 下单率  下单成功/实际到货
				 */
				int xiaNum = 0;
				if(orderNum != 0 && daoNum != 0){
					xiaNum = new BigDecimal(orderNum).divide(new BigDecimal(daoNum),2,BigDecimal.ROUND_HALF_UP).setScale(2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue();
					if(xiaNum > 100)xiaNum = 100;
				}
				commonRoleVo.setOrderNum(xiaNum);
				list.add(commonRoleVo);
			}
		}
		return list;
	}
}

