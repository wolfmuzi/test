/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.app;

import com.iuling.comm.json.AjaxJsonResponse;
import com.iuling.core.web.BaseController;
import com.iuling.mods.base.entity.Fabric;
import com.iuling.mods.fabric.param.FabricInventoryParam;
import com.iuling.mods.fabric.param.FabricShelvesParam;
import com.iuling.mods.fabric.service.FabricOrderService;
import com.iuling.mods.fabric.service.FabricStockService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 色卡调布Controller
 * @author 潘俞再
 * @version 2017-09-23
 */
@Controller
@RequestMapping(value = "${adminPath}/app/fabric/fabricOrder")
public class AppFabricOrderController extends BaseController {

	@Autowired
	private FabricOrderService fabricOrderService;

	@Autowired
	private FabricStockService fabricStockService;


	/**
	 * 根据epcList查找调布的信息
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricOrder:list")
	@RequestMapping(value = "checkBtEpcList")
	public AjaxJsonResponse checkBtEpcList(@RequestBody FabricShelvesParam fabricInventoryParam) {
		if(fabricInventoryParam.getEpcList() == null||fabricInventoryParam.getEpcList().size() == 0  ){
			return new AjaxJsonResponse(false,"1","epc不能为空",null);
		}
		List<Fabric> list = fabricStockService.findListByParam2(fabricInventoryParam);
		return new AjaxJsonResponse(true,"-1","操作成功",list);

	}


	/**
	 * 提交调布
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricOrder:add")
	@RequestMapping(value = "submit")
	public AjaxJsonResponse submit(@RequestBody FabricInventoryParam fabricInventoryParam) {
		if(fabricInventoryParam.getMappings() == null||fabricInventoryParam.getMappings().size() == 0 ||  fabricInventoryParam.getName() == null){
			return new AjaxJsonResponse(false,"1","参数不能为空",null);
		}
		return fabricOrderService.submit(fabricInventoryParam);
	}


}