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
import com.iuling.mods.base.param.ShelvesParam;
import com.iuling.mods.fabric.entity.FabricUnbind;
import com.iuling.mods.fabric.service.FabricUnbindService;
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
 * 解绑色卡记录Controller
 * @author 潘俞再
 * @version 2017-09-21
 */
@Controller
@RequestMapping(value = "${adminPath}/app/fabric/fabricUnbind")
public class AppFabricUnbindController extends BaseController {

	@Autowired
	private FabricUnbindService fabricUnbindService;
	/**
	 * 解绑色卡信息
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("base:shelves:list")
	@RequestMapping(value = "unbindFabric")
	public AjaxJsonResponse unbindFabric(@RequestBody ShelvesParam shelvesParam) {
		if ( shelvesParam.getEpcList() == null || shelvesParam.getEpcList().size() == 0) {
			return new AjaxJsonResponse(false, "1", "标签不能为空", null);
		}
		return fabricUnbindService.unbindFabric(shelvesParam);
	}

}