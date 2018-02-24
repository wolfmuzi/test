/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.app;

import com.iuling.comm.json.AjaxJsonResponse;
import com.iuling.core.web.BaseController;
import com.iuling.mods.base.entity.KindClothing;
import com.iuling.mods.base.service.KindClothingService;
import com.iuling.mods.clothingmanageformj.entity.KindclothingStock;
import com.iuling.mods.clothingmanageformj.param.KindclothingBindParam;
import com.iuling.mods.clothingmanageformj.service.KindclothingBindService;
import com.iuling.mods.clothingmanageformj.service.KindclothingStockService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 样衣绑定信息Controller
 * @author 彭成
 * @version 2017-09-20
 */
@Controller
@RequestMapping(value = "${adminPath}/app/kindclothing/kindclothingBind")
public class AppKindclothingBindController extends BaseController {

	@Autowired
	private KindclothingBindService kindclothingBindService;

	@Autowired
	private KindClothingService kindclothingService;  //样衣基础信息业务

	@Autowired
	private KindclothingStockService kindclothingStockService;

	/**
	 * 绑定标签
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingBind:add")
	@RequestMapping(value = "bind")
	public AjaxJsonResponse bind(@RequestBody KindclothingBindParam param) {
		if(param.getBarCode() == null || param.getEpc() == null){
			return new AjaxJsonResponse(false,"3","参数不能为空",null);
		}
		return kindclothingStockService.bind(param);
	}

	/**
	 * 根据条码获取样衣信息
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingBind:view")
	@RequestMapping(value = "selectByBarCode")
	public AjaxJsonResponse selectByBarCode(@RequestBody KindclothingBindParam param) {
		if(param.getBarCode() == null){
			return new AjaxJsonResponse(false,"3","条码不能为空",null);
		}
		KindClothing kindClothing = new KindClothing();
		kindClothing.setNumber(param.getBarCode());

		List<KindClothing> stockLIstanbul= kindclothingService.findList(kindClothing);
		kindClothing = (stockLIstanbul.size() == 0)?null:stockLIstanbul.get(0);
		return new AjaxJsonResponse(true,"-1","操作成功",kindClothing);
	}

	/**
	 * 判断条码是否已经绑定
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingBind:add")
	@RequestMapping(value = "barCodeIsBind")
	public AjaxJsonResponse barCodeIsBind(@RequestBody KindclothingBindParam param) {
		if(param.getBarCode() == null){
			return new AjaxJsonResponse(false,"3","条码不能为空",null);
		}
		KindclothingStock stock = new KindclothingStock();
		stock.setBarCode(param.getBarCode());
		stock.setBindStatus(2);

		List<KindclothingStock> stockLIstanbul= kindclothingStockService.findList(stock);
		if(stockLIstanbul == null || stockLIstanbul.size()== 0){
			return new AjaxJsonResponse(true,"1","该条码未绑定",null);
		}else{
			return new AjaxJsonResponse(true,"2","该条码已绑定，请扫描新的条码",null);
		}
	}

	/**
	 * 判断标签是否已经绑定
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingBind:add")
	@RequestMapping(value = "epcIsBind")
	public AjaxJsonResponse epcIsBind(@RequestBody KindclothingBindParam param) {
		if(param.getEpc() == null){
			return new AjaxJsonResponse(false,"3","标签不能为空",null);
		}
		KindclothingStock stock = new KindclothingStock();
		stock.setEpc(param.getEpc());

		List<KindclothingStock> stockLIstanbul= kindclothingStockService.findList(stock);
		if(stockLIstanbul == null || stockLIstanbul.size()== 0){
			return new AjaxJsonResponse(true,"1","该标签未绑定",null);
		}else{
			return new AjaxJsonResponse(true,"2","该标签已绑定",null);
		}
	}


}