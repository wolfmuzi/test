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
import com.iuling.mods.base.entity.Supplier;
import com.iuling.mods.base.param.ShelvesParam;
import com.iuling.mods.fabric.entity.FabricStock;
import com.iuling.mods.fabric.param.FabricBindParam;
import com.iuling.mods.fabric.service.FabricStockService;
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
 * 面料色卡库存信息Controller
 * @author 潘俞再
 * @version 2017-09-19
 */
@Controller
@RequestMapping(value = "${adminPath}/app/fabric/fabricStock")
public class AppFabricStockController extends BaseController {

	@Autowired
	private FabricStockService fabricStockService;


	/**
	 * 查找
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricStock:list")
	@RequestMapping(value = "checkFabric")
	public AjaxJsonResponse checkFabric(@RequestBody FabricBindParam fabricBindParam) {
		if (fabricBindParam.getEpc() == null) {
			return new AjaxJsonResponse(false, "1", "epc不能为空", null);
		}
		return fabricStockService.checkFabric(fabricBindParam);
	}


	/**
	 * 按epcList查找多个
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricStock:list")
	@RequestMapping(value = "checkFabricByEpcList")
	public AjaxJsonResponse checkFabricByEpcList(@RequestBody FabricBindParam fabricBindParam) {
		if (fabricBindParam.getEpcList() == null || fabricBindParam.getEpcList().size() == 0) {
			return new AjaxJsonResponse(false, "1", "epc不能为空", null);
		}
		return fabricStockService.checkFabricByEpcList(fabricBindParam);
	}

	/**
	 * 查找
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricStock:list")
	@RequestMapping(value = "checkFabricByBarCode")
	public AjaxJsonResponse checkFabricByBarCode(@RequestBody FabricBindParam fabricBindParam) {
		if (fabricBindParam.getBarCode() == null) {
			return new AjaxJsonResponse(false, "1", "条码不能为空", null);
		}
		return fabricStockService.checkFabricByBarCode(fabricBindParam);
	}


	/**
	 * 根据参数查找
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricStock:list")
	@RequestMapping(value = "selectByParam")
	public AjaxJsonResponse selectByParam(@RequestBody FabricStock fabricStock) {
		fabricStock.setBindStatus(2);
		List<FabricStock> suppliers = fabricStockService.findList(fabricStock);
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
	public AjaxJsonResponse submitByParam(@RequestBody FabricStock fabricStock) {
		fabricStock.setBindStatus(2);
		List<FabricStock> suppliers = fabricStockService.findListSubmit(fabricStock);
		Map<String,Object> map = new HashMap<>();
		map.put("list",suppliers);
		return new AjaxJsonResponse(true,"-1","操作成功",map);

	}


}