/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.app;

import com.iuling.comm.json.AjaxJsonResponse;
import com.iuling.core.web.BaseController;
import com.iuling.mods.base.param.ShelvesParam;
import com.iuling.mods.fabric.service.FabricDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 销毁色卡记录Controller
 * @author 潘俞再
 * @version 2017-09-21
 */
@Controller
@RequestMapping(value = "${adminPath}/app/fabric/fabricDelete")
public class AppFabricDeleteController extends BaseController {

	@Autowired
	private FabricDeleteService fabricDeleteService;

	/**
	 * 销毁色卡信息
	 * @param
	 * @return
	 */
	@ResponseBody
//	@RequiresPermissions("base:shelves:list")
	@RequestMapping(value = "deleteFabric")
	public AjaxJsonResponse deleteFabric(@RequestBody ShelvesParam shelvesParam) {
		if (shelvesParam.getEpcList() == null || shelvesParam.getEpcList().size() == 0 ) {
			return new AjaxJsonResponse(false, "1", "标签不能为空", null);
		}
		return fabricDeleteService.deleteFabric(shelvesParam);
	}
}