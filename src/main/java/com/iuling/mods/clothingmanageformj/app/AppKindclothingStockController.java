/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.app;

import com.iuling.comm.json.AjaxJsonResponse;
import com.iuling.core.web.BaseController;
import com.iuling.mods.fabric.param.FabricBindParam;
import com.iuling.mods.clothingmanageformj.entity.KindclothingStock;
import com.iuling.mods.clothingmanageformj.param.KindclothingBindParam;
import com.iuling.mods.clothingmanageformj.service.KindclothingStockService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 样衣库存Controller
 * @author 彭成
 * @version 2017-09-22
 */
@Controller
@RequestMapping(value = "${adminPath}/app/kindclothing/kindclothingStock")
public class AppKindclothingStockController extends BaseController {

	@Autowired
	private KindclothingStockService kindclothingStockService;

	/**
	 * 根据样衣条码获取样衣信息
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingStock:list")
	@RequestMapping(value = "byByBarCode")
	public AjaxJsonResponse findKindclothingByBarCode(@RequestBody KindclothingBindParam param) {
		if (param.getBarCode() == null || param.getBarCode() == "") {
			return new AjaxJsonResponse(false, "1", "样衣条码不能为空", null);
		}
		KindclothingStock kindclothingStock = kindclothingStockService.findKindclothingByBarCode(param);
		if(kindclothingStock == null){
			return new AjaxJsonResponse(false, "2", "查询不到信息", null);
		}
		return new AjaxJsonResponse(true, "-1", "查询成功", kindclothingStock);
	}


	/**
	 * 根据epc获取样衣信息
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingStock:list")
	@RequestMapping(value = "byEpcData")
	public AjaxJsonResponse findKindclothing(@RequestBody KindclothingBindParam param) {
		if (param.getEpc() == null || param.getEpc() == "") {
			return new AjaxJsonResponse(false, "1", "epc不能为空", null);
		}
		KindclothingStock kindclothingStock = kindclothingStockService.findKindclothingByEpc(param);
		if(kindclothingStock == null){
			return new AjaxJsonResponse(false, "2", "查询不到信息", null);
		}
		return new AjaxJsonResponse(true, "-1", "查询成功", kindclothingStock);
	}



	/**
	 * 按epcList查找多个
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingStock:list")
	@RequestMapping(value = "byEpcListData")
	public AjaxJsonResponse byEpcListData(@RequestBody FabricBindParam fabricBindParam) {
		if (fabricBindParam.getEpcList() == null || fabricBindParam.getEpcList().size() == 0) {
			return new AjaxJsonResponse(false, "1", "epc不能为空", null);
		}
		return kindclothingStockService.byEpcListData(fabricBindParam);
	}



	/**
	 * 根据参数查找
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricStock:list")
	@RequestMapping(value = "selectByParam")
	public AjaxJsonResponse selectByParam(@RequestBody KindclothingStock kindclothingStock) {
		kindclothingStock.setBindStatus(2);
		List<KindclothingStock> suppliers = kindclothingStockService.findList(kindclothingStock);
		Map<String,Object> map = new HashMap<>();
		map.put("list",suppliers);
		return new AjaxJsonResponse(true,"-1","操作成功",map);

	}


	/**
	 * 根据参数查找提交
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricStock:list")
	@RequestMapping(value = "submitByParam")
	public AjaxJsonResponse submitByParam(@RequestBody KindclothingStock kindclothingStock) {
		kindclothingStock.setBindStatus(2);
		List<KindclothingStock> suppliers = kindclothingStockService.findListSubmit(kindclothingStock);
		Map<String,Object> map = new HashMap<>();
		map.put("list",suppliers);
		return new AjaxJsonResponse(true,"-1","操作成功",map);

	}

}