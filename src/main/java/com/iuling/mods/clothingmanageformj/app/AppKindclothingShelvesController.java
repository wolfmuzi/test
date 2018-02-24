/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.app;

import com.iuling.comm.json.AjaxJsonResponse;
import com.iuling.core.web.BaseController;
import com.iuling.mods.base.entity.KindClothing;
import com.iuling.mods.clothingmanageformj.param.KindclothingBindParam;
import com.iuling.mods.clothingmanageformj.param.KindclothingShelvesParam;
import com.iuling.mods.clothingmanageformj.service.KindclothingShelvesService;
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
 * 样衣上架列表Controller
 * @author 彭成
 * @version 2017-09-20
 */
@Controller
@RequestMapping(value = "${adminPath}/app/kindclothing/kindclothingShelves")
public class AppKindclothingShelvesController extends BaseController {

	@Autowired
	private KindclothingShelvesService kindclothingShelvesService;
	@Autowired
	private KindclothingStockService kindclothingStockService;

	/**
	 * 查找
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingShelves:list")
	@RequestMapping(value = "checkKindclothing")
	public AjaxJsonResponse findKindclothing(@RequestBody KindclothingBindParam param) {
		if (param.getEpc() == null || param.getEpc() == "") {
			return new AjaxJsonResponse(false, "1", "epc不能为空", null);
		}
		return kindclothingStockService.checkKindclothing(param);
	}

	/**
	 * 根据epc获取库存已经绑定但未上架的信息
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingShelves:list")
	@RequestMapping(value = "selectByEpcList")
	public AjaxJsonResponse selectByEpcList(@RequestBody KindclothingShelvesParam param) {
		if(param.getEpcList() == null||param.getEpcList().size() == 0  ){
			return new AjaxJsonResponse(false,"1","epc不能为空",null);
		}
		param.setBindStatus(2);   //绑定状态
//		param.setShelvesStatus(1);  //上架状态
		List<KindClothing> list= kindclothingShelvesService.findListByParam(param);
		Map<String,Object> map = new HashMap<>();
		map.put("list",list);
		return new AjaxJsonResponse(true,"-1","操作成功",map);
	}

	/**
	 * 提交上架
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingShelves:add")
	@RequestMapping(value = "submit")
	public AjaxJsonResponse submit(@RequestBody KindclothingShelvesParam param) {
		if(param.getEpcList() == null || param .getEpcList().size() == 0 || param.getShelvesId() == null ){
			return new AjaxJsonResponse(false,"1","参数不能为空",null);
		}

		return kindclothingShelvesService.shelves(param);
	}
}