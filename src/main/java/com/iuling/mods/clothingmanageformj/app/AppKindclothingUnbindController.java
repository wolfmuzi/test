/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.app;

import com.iuling.comm.json.AjaxJsonResponse;
import com.iuling.core.web.BaseController;
import com.iuling.mods.clothingmanageformj.param.KindclothingBindParam;
import com.iuling.mods.clothingmanageformj.service.KindclothingUnbindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 样衣解绑记录Controller
 * @author 彭成
 * @version 2017-09-26
 */
@Controller
@RequestMapping(value = "${adminPath}/app/kindclothing/kindclothingUnbind")
public class AppKindclothingUnbindController extends BaseController {

	@Autowired
	private KindclothingUnbindService kindclothingUnbindService;

	/**
	 * 样衣解绑
	 */
	@ResponseBody
	//@RequiresPermissions("kindclothing:kindclothingUnbind:list")
	@RequestMapping(value = "unbinds")
	public AjaxJsonResponse unbinds(@RequestBody KindclothingBindParam param) {

		if(param.getEpcList()==null || param.getEpcList().size() == 0){
			return new AjaxJsonResponse(false,"1","标签不能为空",null);
		}
		return kindclothingUnbindService.kindclothingUnbind(param);
	}

}