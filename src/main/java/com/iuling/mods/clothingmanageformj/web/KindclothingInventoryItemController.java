/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.web;

import com.google.common.collect.Lists;
import com.iuling.comm.config.Global;
import com.iuling.comm.json.AjaxJson;
import com.iuling.comm.utils.DateUtils;
import com.iuling.comm.utils.StringUtils;
import com.iuling.comm.utils.excel.ExportExcel;
import com.iuling.comm.utils.excel.ImportExcel;
import com.iuling.core.persistence.Page;
import com.iuling.core.web.BaseController;
import com.iuling.mods.clothingmanageformj.entity.KindclothingInventoryItem;
import com.iuling.mods.clothingmanageformj.service.KindclothingInventoryItemService;
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
 * 样衣盘点详情Controller
 * @author 潘俞再
 * @version 2017-09-26
 */
@Controller
@RequestMapping(value = "${adminPath}/kindclothing/kindclothingInventoryItem")
public class KindclothingInventoryItemController extends BaseController {

	@Autowired
	private KindclothingInventoryItemService kindclothingInventoryItemService;
	
	@ModelAttribute
	public KindclothingInventoryItem get(@RequestParam(required=false) String id) {
		KindclothingInventoryItem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = kindclothingInventoryItemService.get(id);
		}
		if (entity == null){
			entity = new KindclothingInventoryItem();
		}
		return entity;
	}

	/**
	 * 样衣详情页面跳转
	 */
	@RequiresPermissions("kindclothing:kindclothingInventoryItem:list")
	@RequestMapping(value = {"toDetail/{code}/{type}", "GET"})
	public String toDetail(@PathVariable String code,@PathVariable Integer type,HttpServletRequest request) {

		request.setAttribute("code",code);
		request.setAttribute("type",type);
		return "mods/kindclothing/kindclothingInventoryItemList";
	}

	/**
	 * 盘点详情列表页面
	 */
	@RequiresPermissions("kindclothing:kindclothingInventoryItem:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/kindclothing/kindclothingInventoryItemList";
	}
	
		/**
	 * 盘点详情列表数据
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingInventoryItem:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(KindclothingInventoryItem kindclothingInventoryItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<KindclothingInventoryItem> page = kindclothingInventoryItemService.findPage(new Page<KindclothingInventoryItem>(request, response), kindclothingInventoryItem); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑盘点详情表单页面
	 */
	@RequiresPermissions(value={"kindclothing:kindclothingInventoryItem:view","kindclothing:kindclothingInventoryItem:add","kindclothing:kindclothingInventoryItem:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(KindclothingInventoryItem kindclothingInventoryItem, Model model) {
		model.addAttribute("kindclothingInventoryItem", kindclothingInventoryItem);
		return "mods/kindclothing/kindclothingInventoryItemForm";
	}

	/**
	 * 保存盘点详情
	 */
	@ResponseBody
	@RequiresPermissions(value={"kindclothing:kindclothingInventoryItem:add","kindclothing:kindclothingInventoryItem:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(KindclothingInventoryItem kindclothingInventoryItem, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, kindclothingInventoryItem)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		kindclothingInventoryItemService.save(kindclothingInventoryItem);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存盘点详情成功");
		return j;
	}
	
	/**
	 * 删除盘点详情
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingInventoryItem:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(KindclothingInventoryItem kindclothingInventoryItem, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		kindclothingInventoryItemService.delete(kindclothingInventoryItem);
		j.setMsg("删除盘点详情成功");
		return j;
	}
	
	/**
	 * 批量删除盘点详情
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingInventoryItem:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			kindclothingInventoryItemService.delete(kindclothingInventoryItemService.get(id));
		}
		j.setMsg("删除盘点详情成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingInventoryItem:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(KindclothingInventoryItem kindclothingInventoryItem, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "盘点详情"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<KindclothingInventoryItem> page = kindclothingInventoryItemService.findPage(new Page<KindclothingInventoryItem>(request, response, -1), kindclothingInventoryItem);
    		new ExportExcel("盘点详情", KindclothingInventoryItem.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出盘点详情记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("kindclothing:kindclothingInventoryItem:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<KindclothingInventoryItem> list = ei.getDataList(KindclothingInventoryItem.class);
			for (KindclothingInventoryItem kindclothingInventoryItem : list){
				try{
					kindclothingInventoryItemService.save(kindclothingInventoryItem);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条盘点详情记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条盘点详情记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入盘点详情失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/kindclothing/kindclothingInventoryItem/?repage";
    }
	
	/**
	 * 下载导入盘点详情数据模板
	 */
	@RequiresPermissions("kindclothing:kindclothingInventoryItem:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "盘点详情数据导入模板.xlsx";
    		List<KindclothingInventoryItem> list = Lists.newArrayList(); 
    		new ExportExcel("盘点详情数据", KindclothingInventoryItem.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/kindclothing/kindclothingInventoryItem/?repage";
    }

}