/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.app;

import com.iuling.comm.json.AjaxJsonResponse;
import com.iuling.core.web.BaseController;
import com.iuling.mods.base.entity.Fabric;
import com.iuling.mods.fabric.param.FabricShelvesParam;
import com.iuling.mods.fabric.service.FabricShelvesService;
import com.iuling.mods.fabric.service.FabricStockService;
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
 * app上架信息Controller
 * @author 潘俞再
 * @version 2017-09-20
 */
@Controller
@RequestMapping(value = "${adminPath}/app/fabric/fabricShelves")
public class AppFabricShelvesController extends BaseController {

	@Autowired
	private FabricShelvesService fabricShelvesService;

	@Autowired
	private FabricStockService fabricStockService;


	/**
	 * 根据epc获取库存已经绑定但未上架的信息
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricShelves:list")
	@RequestMapping(value = "selectByEpcList")
	public AjaxJsonResponse selectByEpcList(@RequestBody FabricShelvesParam fabricShelvesParam) {
		if(fabricShelvesParam.getEpcList() == null||fabricShelvesParam.getEpcList().size() == 0  ){
			return new AjaxJsonResponse(false,"1","epc不能为空",null);
		}
		if(fabricShelvesParam.getShelvesId() == null || "".equals(fabricShelvesParam.getShelvesId())){
			return new AjaxJsonResponse(false,"1","货架ID不能为空",null);
		}
		fabricShelvesParam.setBindStatus(2);
		//fabricShelvesParam.setShelvesStatus(1);
		List<Fabric> list= fabricStockService.findListByParam(fabricShelvesParam);
		Map<String,Object> map = new HashMap<>();
		map.put("list",list);
		return new AjaxJsonResponse(true,"-1","操作成功",map);
	}



	/**
	 * 提交上架
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricShelves:add")
	@RequestMapping(value = "submit")
	public AjaxJsonResponse submit(@RequestBody FabricShelvesParam fabricShelvesParam) {
		if(fabricShelvesParam.getEpcList() == null||fabricShelvesParam.getEpcList().size() == 0 ||fabricShelvesParam.getShelvesId() == null ){
			return new AjaxJsonResponse(false,"1","参数不能为空",null);
		}

		return fabricShelvesService.shelves(fabricShelvesParam);
	}

}