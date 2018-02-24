/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.app;

import com.iuling.comm.json.AjaxJsonResponse;
import com.iuling.core.web.BaseController;
import com.iuling.mods.base.entity.BaseCommonRole;
import com.iuling.mods.base.entity.KindClothing;
import com.iuling.mods.base.service.BaseCommonRoleService;
import com.iuling.mods.clothingmanageformj.param.KindclothingLendParam;
import com.iuling.mods.clothingmanageformj.service.KindclothingLendService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 样衣借用管理Controller
 * @author 彭成
 * @version 2017-09-22
 */
@Controller
@RequestMapping(value = "${adminPath}/app/kindclothing/kindclothingLend")
public class AppKindclothingLendController extends BaseController {

	@Autowired
	private KindclothingLendService kindclothingLendService;
	
	@Autowired
	private BaseCommonRoleService baseCommonRoleService;

	/**
	 * 根据epc找已上架，未借出的样衣
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingLend:list")
	@RequestMapping(value = "selectKindclothingList")
	public AjaxJsonResponse findBaseKindclothingList(@RequestBody KindclothingLendParam param) {
		if(param.getEpcList() == null || param.getEpcList().size() == 0  ){
			return new AjaxJsonResponse(false,"1","epc不能为空",null);
		}
		param.setIsLend(1);   //借出状态
		param.setShelvesStatus(2);  //上架状态

		List<KindClothing> list = kindclothingLendService.findListByParam(param);
		if(list.size() == 0 || list == null) {
			return new AjaxJsonResponse(false,"2","查询不到相关数据",null);
		}
		Map<String,Object> map = new HashMap<>();
		map.put("list",list);
		return new AjaxJsonResponse(true,"-1","操作成功",map);
	}

	/**
	 * 借出
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingLend:list")
	@RequestMapping(value = "lend")
	public AjaxJsonResponse lendKindclothing(@RequestBody KindclothingLendParam param) {
		if(param.getName() == null || param .getEpcList().size() == 0 ){
			return new AjaxJsonResponse(false,"1","参数不能为空",null);
		}
		BaseCommonRole baseCommonRole = baseCommonRoleService.selectByName(param);   //根据姓名获取借用人
		if(baseCommonRole == null) {
			return new AjaxJsonResponse(false,"5","查询不到相关数据",null);
		}
		return kindclothingLendService.lend(param);
	}

}
