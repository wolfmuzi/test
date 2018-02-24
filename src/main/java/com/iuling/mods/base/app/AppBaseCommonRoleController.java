/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.app;

import com.iuling.comm.json.AjaxJsonResponse;
import com.iuling.core.web.BaseController;
import com.iuling.mods.base.entity.BaseCommonRole;
import com.iuling.mods.base.service.BaseCommonRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 设计师质检员管理Controller
 * @author 彭成
 * @version 2017-09-22
 */
@Controller
@RequestMapping(value = "${adminPath}/app/base/baseCommonRole")
public class AppBaseCommonRoleController extends BaseController {

	@Autowired
	private BaseCommonRoleService baseCommonRoleService;

	/**
	 * 根据设计师、质检员查询人员
	 *
	 * @param baseCommonRole
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("base:baseCommonRole:list")
	@RequestMapping(value = "selectByParams")
	public AjaxJsonResponse findBaseCommonRoleList(@RequestBody BaseCommonRole baseCommonRole) {

		List<BaseCommonRole> list = baseCommonRoleService.findBaseCommonRoleList(baseCommonRole);
		if(list == null || list.size() == 0) {
			return new AjaxJsonResponse(false,"1","查询不到相关数据",null);
		}

		return new AjaxJsonResponse(true,"-1","操作成功",list);
	}

}