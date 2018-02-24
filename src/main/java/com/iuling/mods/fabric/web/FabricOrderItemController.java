/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.iuling.mods.fabric.entity.FabricOrder;
import com.iuling.mods.fabric.service.FabricOrderService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.iuling.comm.utils.DateUtils;
import com.iuling.comm.config.Global;
import com.iuling.comm.json.AjaxJson;
import com.iuling.core.persistence.Page;
import com.iuling.core.web.BaseController;
import com.iuling.comm.utils.StringUtils;
import com.iuling.comm.utils.excel.ExportExcel;
import com.iuling.comm.utils.excel.ImportExcel;
import com.iuling.mods.fabric.entity.FabricOrderItem;
import com.iuling.mods.fabric.service.FabricOrderItemService;

/**
 * 调布订单详情Controller
 * @author 潘俞再
 * @version 2017-09-23
 */
@Controller
@RequestMapping(value = "${adminPath}/fabric/fabricOrderItem")
public class FabricOrderItemController extends BaseController {

	@Autowired
	private FabricOrderItemService fabricOrderItemService;


	@Autowired
	private FabricOrderService fabricOrderService;

	@ModelAttribute
	public FabricOrderItem get(@RequestParam(required=false) String id) {
		FabricOrderItem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fabricOrderItemService.get(id);
		}
		if (entity == null){
			entity = new FabricOrderItem();
		}
		return entity;
	}
	
	/**
	 * 调布详情信息列表页面
	 */
	@RequiresPermissions("fabric:fabricOrder:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/fabric/fabricOrderItemList";
	}
	
		/**
	 * 调布详情信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricOrder:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(FabricOrderItem fabricOrderItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FabricOrderItem> page = fabricOrderItemService.findPage(new Page<FabricOrderItem>(request, response), fabricOrderItem); 
		return getBootstrapData(page);
	}

	/**
	 * 详情页面跳转
	 */
	@RequiresPermissions("fabric:fabricOrder:list")
	@RequestMapping(value = {"toDetail/{code}/{id}", "GET"})
	public String toDetail(@PathVariable String id,@PathVariable String code, HttpServletRequest request) {

		request.setAttribute("id",id);
		request.setAttribute("code",code);

		return "mods/fabric/fabricOrderItemList";
	}




	/**
	 * 查看，增加，编辑调布详情信息表单页面
	 */
	@RequiresPermissions(value={"fabric:fabricOrder:view","fabric:fabricOrder:add","fabric:fabricOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(FabricOrderItem fabricOrderItem, Model model) {
		model.addAttribute("fabricOrderItem", fabricOrderItem);
		return "mods/fabric/fabricOrderItemForm";
	}

	/**
	 * 保存调布详情信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"fabric:fabricOrder:add","fabric:fabricOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(FabricOrderItem fabricOrderItem, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, fabricOrderItem)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		fabricOrderItemService.save(fabricOrderItem);//新建或者编辑保存
		/**
		 * 查询数据修改订单状态
		 */
		FabricOrder fabricOrder = fabricOrderService.get(fabricOrderItem.getFabricOrder().getId());
		if(fabricOrder != null){
			if(fabricOrderItem.getGstatus() == 2){
				fabricOrder.setStatus(2);//部分通过
				//判断是否全部过版
				FabricOrderItem item = new FabricOrderItem();
				item.setGstatus(1);
				item.setFabricOrder(fabricOrder);
				item.setId(fabricOrderItem.getId());//不包括自己  因为事务没提交上面没更新到
				List<FabricOrderItem> items = fabricOrderItemService.findList(item);
				if(items.size() == 0){
					fabricOrder.setStatus(3);//全部通过
				}
				fabricOrderService.save(fabricOrder);
			}else if(fabricOrderItem.getGstatus() == 1){
				//判断是否全部过版
				FabricOrderItem item = new FabricOrderItem();
				item.setGstatus(2);
				item.setFabricOrder(fabricOrder);
				item.setId(fabricOrderItem.getId());//不包括自己  因为事务没提交上面没更新到
				List<FabricOrderItem> items = fabricOrderItemService.findList(item);
				if(items.size() == 0){
					fabricOrder.setStatus(1);//全部通过
				}else{
					fabricOrder.setStatus(2);
				}
				fabricOrderService.save(fabricOrder);
			}
		}

		j.setSuccess(true);
		j.setMsg("保存调布详情信息成功");
		return j;
	}
	
	/**
	 * 删除调布详情信息
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricOrder:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(FabricOrderItem fabricOrderItem, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		fabricOrderItemService.delete(fabricOrderItem);
		j.setMsg("删除调布详情信息成功");
		return j;
	}
	
	/**
	 * 批量删除调布详情信息
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricOrder:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fabricOrderItemService.delete(fabricOrderItemService.get(id));
		}
		j.setMsg("删除调布详情信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricOrder:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(FabricOrderItem fabricOrderItem, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "调布详情信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<FabricOrderItem> page = fabricOrderItemService.findPage(new Page<FabricOrderItem>(request, response, -1), fabricOrderItem);
    		new ExportExcel("调布详情信息", FabricOrderItem.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出调布详情信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fabric:fabricOrder:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<FabricOrderItem> list = ei.getDataList(FabricOrderItem.class);
			for (FabricOrderItem fabricOrderItem : list){
				try{
					fabricOrderItemService.save(fabricOrderItem);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条调布详情信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条调布详情信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入调布详情信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fabric/fabricOrderItem/?repage";
    }
	
	/**
	 * 下载导入调布详情信息数据模板
	 */
	@RequiresPermissions("fabric:fabricOrder:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "调布详情信息数据导入模板.xlsx";
    		List<FabricOrderItem> list = Lists.newArrayList(); 
    		new ExportExcel("调布详情信息数据", FabricOrderItem.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fabric/fabricOrderItem/?repage";
    }

}