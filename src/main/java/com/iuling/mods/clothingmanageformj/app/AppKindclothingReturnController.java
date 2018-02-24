/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.app;

import com.iuling.comm.json.AjaxJsonResponse;
import com.iuling.core.web.BaseController;
import com.iuling.mods.base.entity.BaseCommonRole;
import com.iuling.mods.base.service.BaseCommonRoleService;
import com.iuling.mods.clothingmanageformj.entity.KindclothingLendItem;
import com.iuling.mods.clothingmanageformj.param.KindclothingLendParam;
import com.iuling.mods.clothingmanageformj.service.KindclothingReturnService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * 样衣归还管理Controller
 * @author 彭成
 * @version 2017-09-23
 */
@Controller
@RequestMapping(value = "${adminPath}/app/kindclothing/kindclothingReturn")
public class AppKindclothingReturnController extends BaseController {

	@Autowired
	private KindclothingReturnService kindclothingReturnService;
	@Autowired
	private BaseCommonRoleService baseCommonRoleService;
	/**
	 * 根据用户获得未归还的信息
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingReturn:list")
	@RequestMapping(value = "findByUserIdList")
	public AjaxJsonResponse lendKindclothing(@RequestBody KindclothingLendParam param) {
		param.setLendIsReturn(1);  //是否归还
		if(param.getName() == null) {
			return new AjaxJsonResponse(false,"2","参数不能为空",null);
		}
		BaseCommonRole baseCommonRole = baseCommonRoleService.selectByName(param);   //根据姓名获取借用人
		if(baseCommonRole == null) {
			return new AjaxJsonResponse(false,"5","查询不到相关数据",null);
		}
		return kindclothingReturnService.findByParam(param);
	}

	/**
	 * 根据条形码获得归还人信息
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingReturn:list")
	@RequestMapping(value = "findByBarCode")
	public AjaxJsonResponse lendKindclothingByBarCode(@RequestBody KindclothingLendParam param) {
		param.setLendIsReturn(1);  //是否归还
		if(param.getBarCode1() == null || param.getBarCode1() == "") {
			return new AjaxJsonResponse(false,"2","参数不能为空",null);
		}
		List<KindclothingLendItem> list = kindclothingReturnService.findByBarCodeCommonRole(param);
		if(list.size() == 0 || list == null){
			return new AjaxJsonResponse(false,"3","样衣未借出",null);
		}
		HashMap<String, Object> maps = new HashMap<>();
		maps.put("datas", list);

		return new AjaxJsonResponse(true,"-1","查询成功",maps);
	}

	/**
	 * 样衣归还
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingReturn:list")
	@RequestMapping(value = "returns")
	public AjaxJsonResponse returns(@RequestBody KindclothingLendParam param) {

		param.setLendIsReturn(1);  //是否归还
		if(param.getName() == null || param.getName() == "" || param .getEpcList().size() == 0 ){
			return new AjaxJsonResponse(false,"1","参数不能为空",null);
		}
		BaseCommonRole baseCommonRole = baseCommonRoleService.selectByName(param);   //根据姓名获取借用人
		if(baseCommonRole == null) {
			return new AjaxJsonResponse(false,"5","查询不到相关数据",null);
		}
		return kindclothingReturnService.kindclothingReturnByEpc(param);
	}

}