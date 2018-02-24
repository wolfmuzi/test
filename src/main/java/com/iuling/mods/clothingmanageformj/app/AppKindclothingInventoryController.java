/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.app;

import com.iuling.comm.json.AjaxJsonResponse;
import com.iuling.core.web.BaseController;
import com.iuling.mods.fabric.param.FabricInventoryParam;
import com.iuling.mods.clothingmanageformj.entity.KindclothingInventory;
import com.iuling.mods.clothingmanageformj.service.KindclothingInventoryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 样衣盘点Controller
 * @author 潘俞再
 * @version 2017-09-22
 */
@Controller
@RequestMapping(value = "${adminPath}/app/kindclothing/kindclothingInventory")
public class AppKindclothingInventoryController extends BaseController {

	@Autowired
	private KindclothingInventoryService kindclothingInventoryService;

	/**
	 * 根据epcList进行盘点
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingInventory:list")
	@RequestMapping(value = "kindclothingInventory")
	public AjaxJsonResponse selectByEpcList(@RequestBody FabricInventoryParam fabricInventoryParam) {
		if(fabricInventoryParam.getEpcList() == null||fabricInventoryParam.getEpcList().size() == 0  ){
			return new AjaxJsonResponse(false,"1","epc不能为空",null);
		}
		return kindclothingInventoryService.inventory(fabricInventoryParam);
	}

	/**
	 * 提交盘点结果  把记录del_改为0
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingInventory:add")
	@RequestMapping(value = "kindclothingInventorySubmit")
	public AjaxJsonResponse fabricInventorySubmit(@RequestBody FabricInventoryParam fabricInventoryParam) {
		if(fabricInventoryParam.getId() == null ){
			return new AjaxJsonResponse(false,"1","id不能为空",null);
		}
        KindclothingInventory kindclothingInventory = kindclothingInventoryService.get(fabricInventoryParam.getId());
		if(kindclothingInventory == null){
			return new AjaxJsonResponse(false,"1","查询不到相关信息,id不正确",null);
		}
        kindclothingInventory.setDelFlag("0");
        kindclothingInventoryService.save(kindclothingInventory);
		return new AjaxJsonResponse(true,"-1","操作成功",kindclothingInventory);
	}
}