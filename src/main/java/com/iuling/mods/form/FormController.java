/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.form;

import com.iuling.core.persistence.Page;
import com.iuling.core.web.BaseController;
import com.iuling.mods.base.entity.BaseCommonRole;
import com.iuling.mods.base.entity.Supplier;
import com.iuling.mods.fabric.entity.FabricOrderItem;
import com.iuling.mods.fabric.service.FabricOrderItemService;
import com.iuling.mods.clothingmanageformj.entity.KindclothingLendItem;
import com.iuling.mods.clothingmanageformj.service.KindclothingLendItemService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 报表统计Controller
 * @author 潘俞再
 * @version 2017-09-25
 */
@Controller
@RequestMapping(value = "${adminPath}/form")
public class FormController extends BaseController {
	@Autowired
	private FabricOrderItemService fabricOrderItemService;
	@Autowired
	private KindclothingLendItemService kindclothingLendItemService;
	/**
	 * 调布 统计跳转
	 */
	@RequiresPermissions("form:fabricOrder")
	@RequestMapping(value = {"fabricOrder", ""})
	public String fabricOrder() {
		return "mods/form/fabricOrderList";
	}

	/**
	 * 调布 统计
	 */
	@ResponseBody
	@RequiresPermissions("form:fabricOrder:list")
	@RequestMapping(value = "/fabricOrder/data")
	public Map<String, Object> data(FabricOrderItem fabricOrderItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FabricOrderItem> page = new Page(request, response);

		fabricOrderItem.setPage(page);
		List<FabricOrderItem> list = fabricOrderItemService.findByParam(fabricOrderItem);
		page.setList(list);
		FabricOrderItem temp = new FabricOrderItem();
		temp.setEpc(fabricOrderItem.getEpc());
		temp.setFabricOrder(fabricOrderItem.getFabricOrder());
		List<FabricOrderItem> count1 = fabricOrderItemService.findByParam(temp);

		page.setCount(count1.size());
		return getBootstrapData(page);
	}


	/**
	 * 样衣借用 统计跳转
	 */
	@RequiresPermissions("form:kindclothingLend")
	@RequestMapping(value = {"kindclothingLend", ""})
	public String kindclothingLend() {
		return "mods/form/kindclothingLend";
	}

	/**
	 * 样衣借用 统计
	 */
	@ResponseBody
	@RequiresPermissions("form:fabricOrder:list")
	@RequestMapping(value = "/kindclothingLend/data")
	public Map<String, Object> kindclothingLendData(KindclothingLendItem kindclothingLendItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<KindclothingLendItem> page = new Page(request, response);

		kindclothingLendItem.setPage(page);
		List<KindclothingLendItem> list = kindclothingLendItemService.findByParam(kindclothingLendItem);
		page.setList(list);

		KindclothingLendItem temp = new KindclothingLendItem();

		temp.setBaseCommonRole(kindclothingLendItem.getBaseCommonRole());
		List<KindclothingLendItem> count1 = kindclothingLendItemService.findByParam(temp);
		page.setCount(count1.size());
		return getBootstrapData(page);
	}






	/**
	 * 供应商考核跳转
	 */
	@RequiresPermissions("form:supplier")
	@RequestMapping(value = {"supplier", ""})
	public String supplier() {
		return "mods/form/supplier";
	}

	/**
	 * 供应商考核
	 */
	@ResponseBody
	@RequiresPermissions("form:supplier:list")
	@RequestMapping(value = "/supplier/data")
	public Map<String, Object> supplierData(Supplier supplier, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Supplier> page = new Page(request, response);

		if(supplier.getId() == null || "".equals(supplier.getId())){
			return null;
		}

		supplier.setPage(page);
		page.setPageSize(page.getPageSize()/12);
		List<Supplier> list = fabricOrderItemService.supplierForm(supplier);
		page.setList(list);

		Supplier entity = new Supplier();
		entity.setId(supplier.getId());
		List<Supplier> count = fabricOrderItemService.supplierForm(entity);

		page.setCount(count.size());
		return getBootstrapData(page);
	}


	/**
	 * 设计师 考核跳转
	 */
	@RequiresPermissions("form:commonRole")
	@RequestMapping(value = {"commonRole", ""})
	public String commonRole() {
		return "mods/form/commonRole";
	}

	/**
	 * 设计师考核
	 */
	@ResponseBody
	@RequiresPermissions("form:commonRole:list")
	@RequestMapping(value = "/commonRole/data")
	public Map<String, Object> commonRoleData(BaseCommonRole commonRoleVo,HttpServletRequest request, HttpServletResponse response, Model model) {


		if(commonRoleVo.getId() == null || "".equals(commonRoleVo.getId())){
			return null;
		}
		Page<BaseCommonRole> page = new Page(request, response);

		commonRoleVo.setPage(page);
		page.setPageSize(page.getPageSize()/12);
		List<BaseCommonRole> list = fabricOrderItemService.commonRoleForm(commonRoleVo);
		page.setList(list);

		BaseCommonRole entity = new BaseCommonRole();
		entity.setId(commonRoleVo.getId());
		List<BaseCommonRole> count = fabricOrderItemService.commonRoleForm(entity);
		page.setCount(count.size());
		return getBootstrapData(page);
	}

}