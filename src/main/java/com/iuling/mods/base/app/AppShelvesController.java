/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.app;

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
import com.iuling.mods.base.entity.Shelves;
import com.iuling.mods.base.param.ShelvesParam;
import com.iuling.mods.base.service.ShelvesService;
import com.iuling.mods.fabric.entity.FabricStock;
import com.iuling.mods.fabric.param.FabricBindParam;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * app货架管理Controller
 * @author 潘俞再
 * @version 2017-09-20
 */
@Controller
@RequestMapping(value = "${adminPath}/app/base/shelves")
public class AppShelvesController extends BaseController {

	@Autowired
	private ShelvesService shelvesService;


	/**
	 * 根据条码获取货架信息
	 */
	@ResponseBody
	@RequiresPermissions("base:shelves:view")
	@RequestMapping(value = "selectByBarCode")
	public AjaxJsonResponse selectByBarCode(@RequestBody ShelvesParam shelvesParam) {
		if(shelvesParam.getBarCode() == null){
			return new AjaxJsonResponse(false,"1","条码不能为空",null);
		}
		Shelves shelves = new Shelves();
		shelves.setType(shelvesParam.getType());
		shelves.setCode(shelvesParam.getBarCode());
		shelves.setIsBind(2);
		List<Shelves> shelvesList = shelvesService.findList(shelves);
		if(shelvesList == null || shelvesList.size()== 0){
			return new AjaxJsonResponse(false,"1","查询不到货架信息",null);
		}

		return new AjaxJsonResponse(true,"-1","操作成功",shelvesList.get(0));
	}


	/**
	 * 根据货架名称获取多个货架
	 */
	@ResponseBody
	@RequiresPermissions("base:shelves:list")
	@RequestMapping(value = "selectByName")
	public AjaxJsonResponse selectByName(@RequestBody ShelvesParam shelvesParam) {

		Shelves shelves = new Shelves();
		shelves.setType(shelvesParam.getType());
		shelves.setIsBind(1);
		shelves.setName(shelvesParam.getName());
		List<Shelves> shelvesList = shelvesService.findList(shelves);
		if(shelvesList == null || shelvesList.size()== 0){
			return new AjaxJsonResponse(false,"1","查询不到货架信息",null);
		}
		Map<String,Object> map = new HashMap<>();
		map.put("list",shelvesList);
		return new AjaxJsonResponse(true,"-1","操作成功",map);
	}

	/**
	 * 提交货架绑定
	 */
	@ResponseBody
	@RequiresPermissions("base:shelves:add")
	@RequestMapping(value = "bind")
	public AjaxJsonResponse bind(@RequestBody ShelvesParam shelvesParam) {
		if(shelvesParam.getBarCode() == null || shelvesParam.getShelvesId() == null){
			return new AjaxJsonResponse(false,"1","参数不能为空",null);
		}

		Shelves s = new Shelves();
		s.setCode(shelvesParam.getBarCode());
		List<Shelves> shelvess= shelvesService.findList(s);
		if(shelvess != null && shelvess.size() > 0){
			return new AjaxJsonResponse(false,"2","该条码已经被绑定,请重新输入",null);
		}

		Shelves shelves = shelvesService.get(shelvesParam.getShelvesId());
		if(shelves == null){
			return new AjaxJsonResponse(false,"3","查询不到货架信息,货架id不正确",null);
		}
		shelves.setIsBind(2);
		shelves.setCode(shelvesParam.getBarCode());
		shelves.setId(shelvesParam.getShelvesId());
		shelvesService.save(shelves);

		return new AjaxJsonResponse(true,"-1","货架绑定成功",null);
	}






}