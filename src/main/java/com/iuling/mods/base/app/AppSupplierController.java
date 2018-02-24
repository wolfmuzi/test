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
import com.iuling.mods.base.entity.Colors;
import com.iuling.mods.base.entity.Supplier;
import com.iuling.mods.base.service.SupplierService;
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
 * 供应商管理Controller
 * @author 宋小雄
 * @version 2017-09-20
 */
@Controller
@RequestMapping(value = "${adminPath}/app/base/supplier")
public class AppSupplierController extends BaseController {

	@Autowired
	private SupplierService supplierService;

	/**
	 * 根据名称查找供应商
	 *
	 * @param supplier
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("base:supplier:list")
	@RequestMapping(value = "selectByName")
	public AjaxJsonResponse selectByName(@RequestBody Supplier supplier) {

		List<Supplier> suppliers = supplierService.findList(supplier);
		Map<String,Object> map = new HashMap<>();
		map.put("list",suppliers);
		return new AjaxJsonResponse(true,"-1","操作成功",map);
	}
}