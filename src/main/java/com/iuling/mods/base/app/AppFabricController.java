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
import com.iuling.mods.base.entity.Fabric;
import com.iuling.mods.base.param.ShelvesParam;
import com.iuling.mods.base.service.FabricService;
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
 * 面料色卡管理Controller
 * @author 宋小雄
 * @version 2017-09-21
 */
@Controller
@RequestMapping(value = "${adminPath}/app/base/fabric")
public class AppFabricController extends BaseController {

	@Autowired
	private FabricService fabricService;

	/**
	 * 根据条码获取基础色卡信息
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("base:shelves:list")
	@RequestMapping(value = "selectByEpc")
	public AjaxJsonResponse selectByBarCode(@RequestBody ShelvesParam shelvesParam) {
		if(shelvesParam.getEpc() == null ){
			return new AjaxJsonResponse(false,"1","标签不能为空",null);
		}
		Fabric fabricParam = new Fabric();
		fabricParam.setEpc(shelvesParam.getEpc());
		List<Fabric> list = fabricService.findListJoin(fabricParam);
		Fabric fabric = new Fabric();
		fabric = (list.size()>0)? list.get(0):null;
		if(fabric == null){
			return new AjaxJsonResponse(false,"1","查询不到相关信息,请重新扫描标签",null);
		}
		return new AjaxJsonResponse(true,"-1","操作成功",fabric)	;
	}

}