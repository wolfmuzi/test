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
import com.iuling.mods.base.entity.BaseCommonRole;
import com.iuling.mods.base.entity.Colors;
import com.iuling.mods.base.service.ColorsService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.text.resources.ro.CollationData_ro;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 颜色信息管理Controller
 * @author 潘俞再
 * @version 2017-09-20
 */
@Controller
@RequestMapping(value = "${adminPath}/app/base/colors")
public class AppColorsController extends BaseController {

	@Autowired
	private ColorsService colorsService;

	/**
	 * 根据名称查找颜色
	 *
	 * @param color
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("base:colors:list")
	@RequestMapping(value = "selectByName")
	public AjaxJsonResponse selectByName(@RequestBody Colors color) {

		List<Colors> colors = colorsService.findList(color);
		Map<String,Object> map = new HashMap<>();
		map.put("list",colors);
		return new AjaxJsonResponse(true,"-1","操作成功",map);
	}


}