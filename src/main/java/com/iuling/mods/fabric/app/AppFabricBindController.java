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
import com.iuling.mods.base.service.FabricService;
import com.iuling.mods.fabric.entity.FabricBind;
import com.iuling.mods.fabric.entity.FabricStock;
import com.iuling.mods.fabric.param.FabricBindParam;
import com.iuling.mods.fabric.service.FabricBindService;
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
import java.util.List;
import java.util.Map;

/**
 * app面料色卡绑定记录Controller
 * @author 潘俞再
 * @version 2017-09-20
 */
@Controller
@RequestMapping(value = "${adminPath}/app/fabric/fabricBind")
public class AppFabricBindController extends BaseController {

	@Autowired
	private FabricBindService fabricBindService;

	@Autowired
	private FabricStockService fabricStockService;

	@Autowired
	private FabricService fabricService;

	/**
	 * 绑定标签
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricBind:add")
	@RequestMapping(value = "bind")
	public AjaxJsonResponse bind(@RequestBody FabricBindParam fabricBindParam) {
		if(fabricBindParam.getBarCode() == null || fabricBindParam.getEpc() == null){
			return new AjaxJsonResponse(false,"1","参数不能为空",null);
		}
		return fabricStockService.bind(fabricBindParam);
	}

	/**
	 * 判断条码是否已经绑定
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricBind:add")
	@RequestMapping(value = "barCodeIsBind")
	public AjaxJsonResponse barCodeIsBind(@RequestBody FabricBindParam fabricBindParam) {
		if(fabricBindParam.getBarCode() == null){
			return new AjaxJsonResponse(false,"3","条码不能为空",null);
		}
		FabricStock stock = new FabricStock();
		stock.setBarCode(fabricBindParam.getBarCode());
		stock.setBindStatus(2);
		List<FabricStock> stockLIstanbul= fabricStockService.findList(stock);
		if(stockLIstanbul == null || stockLIstanbul.size()== 0){
			return new AjaxJsonResponse(true,"1","该条码未绑定",null);
		}else{
			return new AjaxJsonResponse(true,"2","该条码已绑定，是否覆盖",null);
		}
	}



	/**
	 * 判断标签是否已经绑定
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricBind:add")
	@RequestMapping(value = "epcIsBind")
	public AjaxJsonResponse epcIsBind(@RequestBody FabricBindParam fabricBindParam) {
		if(fabricBindParam.getEpc() == null){
			return new AjaxJsonResponse(false,"3","标签不能为空",null);
		}
		FabricStock stock = new FabricStock();
		stock.setEpc(fabricBindParam.getEpc());
		stock.setBindStatus(2);
		List<FabricStock> stockLIstanbul= fabricStockService.findList(stock);
		if(stockLIstanbul == null || stockLIstanbul.size()== 0){
			return new AjaxJsonResponse(true,"1","该标签未绑定",null);
		}else{
			return new AjaxJsonResponse(true,"2","该标签已绑定",null);
		}
	}

	/**
	 * 根据条码获取面料信息
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricBind:view")
	@RequestMapping(value = "selectByBarCodde")
	public AjaxJsonResponse selectByBarCodde(@RequestBody FabricBindParam fabricBindParam) {
		if(fabricBindParam.getBarCode() == null){
			return new AjaxJsonResponse(false,"1","条码不能为空",null);
		}
		Fabric fabric = new Fabric();
		fabric.setBarcode(fabricBindParam.getBarCode());
		List<Fabric> stockLIstanbul= fabricService.findList(fabric);
		fabric = (stockLIstanbul.size() == 0)?null:stockLIstanbul.get(0);
		return new AjaxJsonResponse(true,"-1","操作成功",fabric);
	}






















}