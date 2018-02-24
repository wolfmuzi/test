/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.app;

import com.google.common.collect.Lists;
import com.iuling.comm.config.Global;
import com.iuling.comm.json.AjaxJson;
import com.iuling.comm.json.AjaxJsonResponse;
import com.iuling.comm.utils.DateUtils;
import com.iuling.comm.utils.StringUtils;
import com.iuling.comm.utils.excel.ExportExcel;
import com.iuling.comm.utils.excel.ImportExcel;
import com.iuling.core.persistence.Page;
import com.iuling.core.web.BaseController;
import com.iuling.mods.base.entity.Fabric;
import com.iuling.mods.fabric.entity.FabricInventory;
import com.iuling.mods.fabric.param.FabricInventoryParam;
import com.iuling.mods.fabric.param.FabricShelvesParam;
import com.iuling.mods.fabric.service.FabricInventoryService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 色卡盘点Controller
 * @author 潘俞再
 * @version 2017-09-22
 */
@Controller
@RequestMapping(value = "${adminPath}/app/fabric/fabricInventory")
public class AppFabricInventoryController extends BaseController {

	@Autowired
	private FabricInventoryService fabricInventoryService;

	/**
	 * 根据epcList进行盘点
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricInventory:list")
	@RequestMapping(value = "fabricInventory")
	public AjaxJsonResponse selectByEpcList(@RequestBody FabricInventoryParam fabricInventoryParam) {
		if(fabricInventoryParam.getEpcList() == null||fabricInventoryParam.getEpcList().size() == 0  ){
			return new AjaxJsonResponse(false,"1","epc不能为空",null);
		}
		return fabricInventoryService.inventory(fabricInventoryParam);
	}

	/**
	 * 提交盘点结果  把记录del_改为0
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricInventory:add")
	@RequestMapping(value = "fabricInventorySubmit")
	public AjaxJsonResponse fabricInventorySubmit(@RequestBody FabricInventoryParam fabricInventoryParam) {
		if(fabricInventoryParam.getId() == null ){
			return new AjaxJsonResponse(false,"1","id不能为空",null);
		}
		FabricInventory fabricInventory = fabricInventoryService.get(fabricInventoryParam.getId());
		if(fabricInventory == null){
			return new AjaxJsonResponse(false,"1","查询不到相关信息,id不正确",null);
		}
		fabricInventory.setDelFlag("0");
		fabricInventoryService.save(fabricInventory);
		return new AjaxJsonResponse(true,"-1","操作成功",fabricInventory);
	}
}